package com.atguigu.crowd.service.api;

import com.atguigu.crowd.entity.vo.AddressVO;
import com.atguigu.crowd.entity.vo.OrderProjectVO;
import com.atguigu.crowd.entity.vo.OrderVO;

import java.util.List;

/**
 * @author shiyutao
 * @create 2022-02-03 22:00
 */
public interface OrderProviderService {

    OrderProjectVO getOrderProjectVO(Integer projectId, Integer returnId);

    List<AddressVO> getAddressVOList(Integer id);

    void saveAddress(AddressVO addressVO);

    void saveOrder(OrderVO orderVO);
}

