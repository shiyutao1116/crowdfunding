package com.atguigu.crowd.mvc.handler;

import com.atguigu.crowd.CorwdConstant;
import com.atguigu.crowd.CrowdUtil;
import com.atguigu.crowd.LoginAcctAlreadyInUserException;
import com.atguigu.crowd.LoginAcctAlreadyInUserException2;
import com.atguigu.crowd.entity.Admin;
import com.atguigu.crowd.service.api.AdminService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

import static com.atguigu.crowd.CorwdConstant.ATTR_NAME_PAGE_INFO;

/**
 * @author shiyutao
 * @create 2022-01-10 12:23
 */
@Controller
public class AdminHandler {
    @Autowired
    private AdminService adminService;
    @RequestMapping("admin/do/logout.html")
    public String dologinout(
            HttpSession session){
        session.invalidate();
        return "redirect:/admin/to/login/page.html";
    }
    @RequestMapping("admin/do/login.html")
    public String dologin(
            @RequestParam("login")String login,
            @RequestParam("userPswd")String userPswd,
            HttpSession session){
            Admin admin=adminService.getAdminByLoginAcct(login,userPswd);
            session.setAttribute(CorwdConstant.ATTR_NAME_LOGIN_ADMIN,admin);
        return "redirect:/admin/to/main/page.html";
    }
    @RequestMapping("admin/get/page.html")
    public String getPageInfo(
            @RequestParam(value = "keyWord" ,defaultValue = "") String keyWord,
            @RequestParam(value = "pageNum",defaultValue = "1") Integer pageNum,
            @RequestParam(value = "pageSize",defaultValue = "5") Integer pageSize,
            ModelMap modelMap
    ){
        PageInfo<Admin> pageInfo = adminService.getPageInfo(keyWord, pageNum, pageSize);
        modelMap.addAttribute(CorwdConstant.ATTR_NAME_PAGE_INFO,pageInfo);

        return "admin-page";
    }

    @RequestMapping("admin/remove/{adminId}/{pageNum}/{keyWord}.html")
    public String remove(@PathVariable("adminId") Integer adminId,@PathVariable("pageNum") Integer pageNum,
                         @PathVariable("keyWord") String keyWord){
       adminService.remove(adminId);
        return "redirect:/admin/get/page.html?pageNum="+pageNum+"&keyWord="+keyWord;
    }
    @RequestMapping("admin/save.html")
    public String save(Admin admin){
        try {
            adminService.saveAdmin(admin);
        }catch (Exception e){
            e.printStackTrace();
            if (e instanceof DuplicateKeyException){
                throw  new LoginAcctAlreadyInUserException(CorwdConstant.MESSAGE_LOGIN_ACCT_ALREADY_IN_USE);
            }
        }

        return "redirect:/admin/get/page.html?pageNum="+Integer.MAX_VALUE;
    }
    @RequestMapping("admin/to/edit/page.html")
    public String update(@RequestParam("adminId") Integer adminId, ModelMap modelMap){
            Admin admin=adminService.getAdminById(adminId);
            modelMap.addAttribute("admin",admin);

        return "admin-edit";
    }
    @RequestMapping("admin/update.html")
    public String update(Admin admin,@RequestParam("pageNum")Integer pageNum,@RequestParam("keyWord")String keyWord){
        try {
            adminService.update(admin);
        }catch (Exception e){
            e.printStackTrace();
            if (e instanceof DuplicateKeyException){
                throw  new LoginAcctAlreadyInUserException2(CorwdConstant.MESSAGE_LOGIN_ACCT_ALREADY_IN_USE);
            }
        }
        return  "redirect:/admin/get/page.html?pageNum="+pageNum+"&keyWord="+keyWord;
    }












}
