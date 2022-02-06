package com.atguigu.crowd.mvc.handler;

import com.atguigu.crowd.CorwdConstant;
import com.atguigu.crowd.ResultEntity;
import com.atguigu.crowd.entity.Admin;
import com.atguigu.crowd.entity.Role;
import com.atguigu.crowd.service.api.RoleService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @author shiyutao
 * @create 2022-01-12 20:15
 */
@Controller
public class RoleHandler {
    @Autowired
    private RoleService roleService;
    @ResponseBody
    @RequestMapping("role/get/page/info.json")
    public ResultEntity<PageInfo<Role>> getPageInfo(
            @RequestParam(value = "keyWord" ,defaultValue = "") String keyWord,
            @RequestParam(value = "pageNum",defaultValue = "1") Integer pageNum,
            @RequestParam(value = "pageSize",defaultValue = "5") Integer pageSize
    ){
        PageInfo<Role> pageInfo = roleService.getPageInfo(keyWord, pageNum, pageSize);
        return ResultEntity.successwithdata(pageInfo);

    }
    @ResponseBody
    @RequestMapping("role/save.json")
    public ResultEntity<String> saveRole(Role role){
       roleService.saveRole(role);
        return ResultEntity.successwithoutdata();

    }
    @ResponseBody
    @RequestMapping("role/update.json")
    public ResultEntity<String> updateRole(Role role){
        roleService.updateRole(role);
        return ResultEntity.successwithoutdata();

    }
    @ResponseBody
    @RequestMapping("role/remove/by/role/id/array.json")
    public ResultEntity<String> removeRole(@RequestBody List<Integer> roleIdList){
        roleService.removeRole(roleIdList);
        return ResultEntity.successwithoutdata();

    }
}
