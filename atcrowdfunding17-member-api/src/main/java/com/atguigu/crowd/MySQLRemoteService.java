package com.atguigu.crowd;

import com.atguigu.crowd.ResultEntity;
import com.atguigu.crowd.entity.po.MemberPO;
import com.atguigu.crowd.entity.vo.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * @author shiyutao
 * @create 2022-01-23 22:27
 */
@FeignClient("atguigu-crowd-mysql")
public interface MySQLRemoteService {
   @RequestMapping("get/memberpo/by/login/acct/remote")
   public  ResultEntity<MemberPO> getMemberPOByLoginAcctRemote(@RequestParam("loginacct") String loginacct);
   @RequestMapping("save/member/remote")
   public ResultEntity<String> saveMember(@RequestBody MemberPO memberPO);
   @RequestMapping("save/project/vo/remote")
   ResultEntity<String> saveProjectVORemote(@RequestBody ProjectVO projectVO,@RequestParam("memberId") Integer memberId);
   @RequestMapping("get/portal/type/project/data/remote")
   public ResultEntity<List<PortalTypeVO>> getportalTypeProjectData();
   @RequestMapping("/get/project/detail/remote/{id}")
   public ResultEntity<DetailProjectVO> getDetailProjectVORemote(@PathVariable("id") Integer id);
   @RequestMapping("get/order/project/vo/remote")
   public ResultEntity<OrderProjectVO> getOrderProjectVORemote(@RequestParam("projectId") Integer projectId,@RequestParam("returnId") Integer returnId);
   @RequestMapping("get/address/vo/remote")
   public ResultEntity<List<AddressVO>> getAddressVORemote(@RequestParam("id") Integer id);
   @RequestMapping("save/address/remote")
    ResultEntity<String> saveAddressRemote(@RequestBody AddressVO addressVO);

   @RequestMapping("save/order/remote")
    ResultEntity<String> saveOrderRemote(@RequestBody OrderVO orderVO);
}
