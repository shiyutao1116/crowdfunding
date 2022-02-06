package com.atguigu.crowd.mvc.handler;

import com.alibaba.druid.sql.visitor.functions.If;
import com.atguigu.crowd.ResultEntity;
import com.atguigu.crowd.entity.Menu;
import com.atguigu.crowd.service.api.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;

/**
 * @author shiyutao
 * @create 2022-01-14 13:12
 */
@Controller
public class MenuHandler {
    @Autowired
    private MenuService menuService;
    @ResponseBody
    @RequestMapping("menu/get/whole/tree.json")
    public ResultEntity<Menu> getWholeTree() {
        List<Menu> menuList = menuService.getAll();
        Menu root = null;
        HashMap<Integer, Menu> map = new HashMap<>();
        for (Menu menu : menuList) {
            map.put(menu.getId(),menu);

        }
        for (Menu menu : menuList) {
            Integer pid = menu.getPid();
            if(pid == null) {
                root = menu;
                continue ;
            }
            Menu father = map.get(pid);
            father.getChildren().add(menu);
        }

        return ResultEntity.successwithdata(root);
    }
    @ResponseBody
    @RequestMapping("menu/save.json")
    public ResultEntity<String> savaMenu(Menu menu) {
        menuService.saveMenu(menu);
        return ResultEntity.successwithoutdata();
    }
    @ResponseBody
    @RequestMapping("menu/update.json")
    public ResultEntity<String> updateMenu(Menu menu) {
        menuService.updateMenu(menu);
        return ResultEntity.successwithoutdata();


    }
    @ResponseBody
    @RequestMapping("menu/remove.json")
    public ResultEntity<String> removeMenu(@RequestParam("id")Integer id) {
        menuService.deleteMenu(id);
        return ResultEntity.successwithoutdata();


    }


    }
