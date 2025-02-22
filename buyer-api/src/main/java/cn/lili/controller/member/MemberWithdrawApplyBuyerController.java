package cn.lili.controller.member;


import cn.lili.common.security.context.UserContext;
import cn.lili.common.enums.ResultUtil;
import cn.lili.common.vo.PageVO;
import cn.lili.common.vo.ResultMessage;
import cn.lili.modules.member.entity.dos.MemberWithdrawApply;
import cn.lili.modules.member.entity.vo.MemberWithdrawApplyQueryVO;
import cn.lili.modules.member.service.MemberWithdrawApplyService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * 买家端,余额提现记录接口
 *
 * @author pikachu
 * @date: 2020/11/16 10:07 下午
 */
@RestController
@Api(tags = "买家端,余额提现记录接口")
@RequestMapping("/buyer/member/withdrawApply")
@Transactional(rollbackFor = Exception.class)
public class MemberWithdrawApplyBuyerController {
    @Autowired
    private MemberWithdrawApplyService memberWithdrawApplyService;


    @ApiOperation(value = "分页获取提现记录")
    @GetMapping
    public ResultMessage<IPage<MemberWithdrawApply>> getByPage(PageVO page, MemberWithdrawApplyQueryVO memberWithdrawApplyQueryVO) {
        memberWithdrawApplyQueryVO.setMemberId(UserContext.getCurrentUser().getId());
        //构建查询 返回数据
        IPage<MemberWithdrawApply> memberWithdrawApplyIPage = memberWithdrawApplyService.getMemberWithdrawPage(page, memberWithdrawApplyQueryVO);
        return ResultUtil.data(memberWithdrawApplyIPage);
    }

}
