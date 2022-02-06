package com.atguigu.crowd.handler;

import com.atguigu.crowd.ResultEntity;
import com.atguigu.crowd.entity.vo.AddressVO;
import com.atguigu.crowd.entity.vo.OrderProjectVO;
import com.atguigu.crowd.entity.vo.OrderVO;
import com.atguigu.crowd.service.api.OrderProviderService;
import com.atguigu.crowd.service.impl.OrderProviderServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author shiyutao
 * @create 2022-02-03 21:56
 */

@RestController
public class OrderProviderHandler {
    @Autowired
    private OrderProviderService orderProviderService;
    @RequestMapping("save/address/remote")
    ResultEntity<String> saveAddressRemote(@RequestBody AddressVO addressVO){
       ;
        try {
            orderProviderService.saveAddress(addressVO);
            return ResultEntity.successwithoutdata();
        } catch (Exception e) {
            return ResultEntity.failed(e.getMessage());
        }



    }


    @RequestMapping("get/address/vo/remote")
    public ResultEntity<List<AddressVO>> getAddressVORemote(@RequestParam("id")Integer id){

        try {
            List<AddressVO> addressVOList=orderProviderService.getAddressVOList(id);
            return ResultEntity.successwithdata(addressVOList);
        } catch (Exception e) {
            return ResultEntity.failed(e.getMessage());
        }

    }


    @RequestMapping("get/order/project/vo/remote")
    public ResultEntity<OrderProjectVO> getOrderProjectVORemote(@RequestParam("projectId") Integer projectId, @RequestParam("returnId") Integer returnId) {
        try {
            OrderProjectVO orderProjectVO = orderProviderService.getOrderProjectVO(projectId, returnId);
            return ResultEntity.successwithdata(orderProjectVO);
        } catch (Exception e) {
            return ResultEntity.failed(e.getMessage());
        }

    }
    @RequestMapping("save/order/remote")
    ResultEntity<String> saveOrderRemote(@RequestBody OrderVO orderVO){
        try {
            orderProviderService.saveOrder(orderVO);
            return ResultEntity.successwithoutdata();
        } catch (Exception e) {
            return ResultEntity.failed(e.getMessage());
        }


    }


}
