package com.atguigu.crowd.mvc.handler;

import com.atguigu.crowd.CrowdUtil;
import com.atguigu.crowd.service.api.AdminService;
import com.atguigu.crowd.entity.Admin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author shiyutao
 * @create 2022-01-07 21:06
 */
@Controller
public class Testhandler {
    @Autowired
    private AdminService adminService;
    @RequestMapping("/com.atguigu.crowd.test/ssm.html")
    public String testssm(Model model, HttpServletRequest request){
        System.out.println(CrowdUtil.judgeRequestType(request));
        List<Admin> all = adminService.getAll();
        model.addAttribute("list",all);
        System.out.println(10/0);
        /*String a=null;
        System.out.println(a.length());*/
        return "target";
    }
    @ResponseBody
    @PostMapping("send/array.html")
    public String test2(@RequestParam("array[]")List<Integer> list){
            for (Integer number:list){

                System.out.println(number);
            }

        return "success";
    }
}
