package cn.lili.modules.distribution.serviceimpl;

import cn.lili.modules.distribution.entity.dos.DistributionSelectedGoods;
import cn.lili.modules.distribution.mapper.DistributionSelectedGoodsMapper;
import cn.lili.modules.distribution.service.DistributionSelectedGoodsService;
import cn.lili.modules.distribution.service.DistributionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 分销选择商品接口实现
 *
 * @author pikachu
 * @date 2020-03-24 23:04:56
 */
@Service
@Transactional
public class DistributionSelectedGoodsServiceImpl extends ServiceImpl<DistributionSelectedGoodsMapper, DistributionSelectedGoods> implements DistributionSelectedGoodsService {

    //分销员
    @Autowired
    private DistributionService distributionService;
    @Override
    public boolean add(String distributionGoodsId) {
        //检查分销功能开关
        distributionService.checkDistributionSetting();

        String distributionId=distributionService.getDistribution().getId();
        DistributionSelectedGoods distributionSelectedGoods=new DistributionSelectedGoods(distributionId,distributionGoodsId);
        return this.save(distributionSelectedGoods);
    }
}
