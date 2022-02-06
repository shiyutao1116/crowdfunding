package com.atguigu.crowd.handler;

import com.atguigu.crowd.CorwdConstant;
import com.atguigu.crowd.ResultEntity;
import com.atguigu.crowd.entity.po.MemberPO;
import com.atguigu.crowd.entity.vo.PortalTypeVO;
import com.atguigu.crowd.service.api.MemberService;
import com.atguigu.crowd.service.api.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author shiyutao
 * @create 2022-01-23 22:42
 */
@RestController
public class MemberProviderHandler {
    @Autowired
    private MemberService memberService;

    @RequestMapping("save/member/remote")
    public ResultEntity<String> saveMember(@RequestBody MemberPO memberPO){
        try {
            memberService.saveMember(memberPO);
            return ResultEntity.successwithoutdata();
        }catch (Exception e){
            if (e instanceof DuplicateKeyException){
                return ResultEntity.failed(CorwdConstant.MESSAGE_LOGIN_ACCT_ALREADY_IN_USE);

            }
            return ResultEntity.failed(e.getMessage());

        }


    }


    @RequestMapping("get/memberpo/by/login/acct/remote")
    public ResultEntity<MemberPO> getMemberPOByLoginAcctRemote(@RequestParam("loginacct") String loginacct){
      try {
          MemberPO memberPO=memberService.getMemberPOByLoginAcct(loginacct);
          return ResultEntity.successwithdata(memberPO);
      }catch (Exception e){
          e.printStackTrace();
          return ResultEntity.failed(e.getMessage());
      }
}
}
