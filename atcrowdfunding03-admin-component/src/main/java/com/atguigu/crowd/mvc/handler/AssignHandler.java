package com.atguigu.crowd.mvc.handler;

import com.atguigu.crowd.ResultEntity;
import com.atguigu.crowd.entity.Auth;
import com.atguigu.crowd.entity.Role;
import com.atguigu.crowd.service.api.AdminService;
import com.atguigu.crowd.service.api.AuthService;
import com.atguigu.crowd.service.api.RoleService;
import com.sun.tools.doclets.formats.html.SourceToHTMLConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.xml.bind.SchemaOutputResolver;
import java.lang.reflect.Array;
import java.rmi.MarshalledObject;
import java.util.List;
import java.util.Map;

/**
 * @author shiyutao
 * @create 2022-01-15 19:31
 */
@Controller
public class AssignHandler {
    @Autowired
    private AdminService adminService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private AuthService authService;
    @RequestMapping("assign/to/assign/role/page.html")
    public String toAssignRolePage(@RequestParam("adminId") Integer adminId , ModelMap modelMap){
        List<Role> assignedrolelist=roleService.getAssignedRole(adminId);
        List<Role> unassignedrolelist=roleService.getUnAssignedRole(adminId);
        modelMap.addAttribute("assignedrolelist",assignedrolelist);
        modelMap.addAttribute("unassignedrolelist",unassignedrolelist);
        return "assign-role";

    }
    @RequestMapping("assign/do/role/assign.html")
    public String saveRelationship (@RequestParam("adminId")Integer adminId,
                                    @RequestParam("pageNum")Integer pageNum,
                                    @RequestParam("keyWord")String keyWord,
                                    @RequestParam(value = "roleIdList",required = false)List<Integer> list){
        adminService.saveRelationship(adminId,list);
        return "redirect:/admin/get/page.html?pageNum="+pageNum+"&keyWord="+keyWord;

    }

    @ResponseBody
    @RequestMapping("assign/get/all/auth.json")
    public ResultEntity<List<Auth>> getallAuth(){
       List<Auth> list= authService.getAll();
        return ResultEntity.successwithdata(list);
    }
    @ResponseBody
    @RequestMapping("assign/get/assigned/auth/id/by/role/id.json")
    public ResultEntity<List<Integer>> getAuthIdByRoleId(@RequestParam("roleId") Integer roleId){
        List<Integer> list= authService.getAuthIdByRoleId(roleId);
        return ResultEntity.successwithdata(list);
    }

    @ResponseBody
    @RequestMapping("assign/do/role/assign/auth.json")
    public ResultEntity<String> saveRoleAuthRelationship(@RequestBody Map<String,List<Integer>> map){
        authService.saveRoleAuthRelationship(map);
        return ResultEntity.successwithoutdata();






   }


}
