package cn.lili.common.aop.limiter;

import cn.lili.common.aop.limiter.annotation.LimitPoint;
import com.google.common.collect.ImmutableList;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.lang.reflect.Method;

/**
 * 流量拦截
 * @author Chopper
 */
@Aspect
@Configuration
@Slf4j
public class LimitInterceptor {
    private RedisTemplate<String, Serializable> redisTemplate;

    private DefaultRedisScript<Number> limitScript;

    @Autowired
    public void setRedisTemplate(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Autowired
    public void setLimitScript(DefaultRedisScript<Number> limitScript) {
        this.limitScript = limitScript;
    }

    @Around("execution(public * *(..)) && @annotation(cn.lili.common.aop.limiter.annotation.LimitPoint)")
    public Object interceptor(ProceedingJoinPoint pjp) {
        MethodSignature signature = (MethodSignature) pjp.getSignature();
        Method method = signature.getMethod();
        LimitPoint limitPointAnnotation = method.getAnnotation(LimitPoint.class);
        LimitType limitType = limitPointAnnotation.limitType();
        String name = limitPointAnnotation.name();
        String key;
        int limitPeriod = limitPointAnnotation.period();
        int limitCount = limitPointAnnotation.limit();
        switch (limitType) {
            case IP:
                key = limitPointAnnotation.key() + getIpAddress();
                break;
            case CUSTOMER:
                key = limitPointAnnotation.key();
                break;
            default:
                key = StringUtils.upperCase(method.getName());
        }
        ImmutableList<String> keys = ImmutableList.of(StringUtils.join(limitPointAnnotation.prefix(), key));
        try {
            Number count = redisTemplate.execute(limitScript, keys, limitCount, limitPeriod);
            log.info("Access try count is {} for name={} and key = {}", count, name, key);
            if (count != null && count.intValue() <= limitCount) {
                return pjp.proceed();
            } else {
                throw new RuntimeException("访问过于频繁，请稍后再试");
            }
        } catch (Throwable e) {
            if (e instanceof RuntimeException) {
                throw new RuntimeException(e.getLocalizedMessage());
            }
            throw new RuntimeException("服务器异常，请稍后再试");
        }
    }


    private static final String UNKNOWN = "unknown";

    public String getIpAddress() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }
}