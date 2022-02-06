package com.atguigu.crowd.handler;

import com.atguigu.crowd.CorwdConstant;
import com.atguigu.crowd.MySQLRemoteService;
import com.atguigu.crowd.ResultEntity;
import com.atguigu.crowd.entity.vo.AddressVO;
import com.atguigu.crowd.entity.vo.MemberLoginVO;
import com.atguigu.crowd.entity.vo.OrderProjectVO;
import com.sun.jndi.cosnaming.IiopUrl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @author shiyutao
 * @create 2022-02-03 20:16
 */
@Controller
public class OrderHandler {
    @Autowired
    private MySQLRemoteService mySQLRemoteService;
    @RequestMapping("confirm/return/info/{projectId}/{returnId}")
    public String showReturnConfimInfo(HttpSession session, @PathVariable("projectId") Integer projectId, @PathVariable("returnId") Integer returnId){
        ResultEntity<OrderProjectVO> resultEntity =mySQLRemoteService.getOrderProjectVORemote(projectId,returnId);
        if (ResultEntity.SUCCESS.equals(resultEntity.getResult())){
            OrderProjectVO data = resultEntity.getData();
            session.setAttribute("orderProjectVO",data);
        }

        return "confirm-return";
    }

    @RequestMapping("confirm/order/{returnCount}")
    public String showConfirmOrderInfo(@PathVariable("returnCount")Integer returnCount,HttpSession session){
        OrderProjectVO orderProjectVO =(OrderProjectVO) session.getAttribute("orderProjectVO");
        orderProjectVO.setReturnCount(returnCount);
        session.setAttribute("orderProjectVO",orderProjectVO);

        MemberLoginVO attribute =(MemberLoginVO) session.getAttribute(CorwdConstant.ATTR_NAME_LOGIN_MEMBER);
        Integer id = attribute.getId();
        ResultEntity<List<AddressVO>> resultEntity=mySQLRemoteService.getAddressVORemote(id);
        System.out.println(resultEntity.getData());
        if(ResultEntity.SUCCESS.equals(resultEntity.getResult())) {
            List<AddressVO> list = resultEntity.getData();
            session.setAttribute("addressVOList", list);
        }
        return "confirm-order";
    }
    @RequestMapping("save/address")
    public String saveAddress(AddressVO addressVO,HttpSession session){
        ResultEntity<String> resultEntity= mySQLRemoteService.saveAddressRemote(addressVO);
            OrderProjectVO orderProjectVO =(OrderProjectVO) session.getAttribute("orderProjectVO");
            Integer returnCount = orderProjectVO.getReturnCount();

        return "redirect:http://localhost/order/confirm/order/"+ returnCount;










    }

}
