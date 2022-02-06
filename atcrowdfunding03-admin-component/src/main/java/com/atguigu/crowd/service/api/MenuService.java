package com.atguigu.crowd.service.api;

import com.atguigu.crowd.entity.Menu;

import java.util.List;

/**
 * @author shiyutao
 * @create 2022-01-14 13:11
 */
public interface MenuService {
    List<Menu> getAll();

    void saveMenu(Menu menu);

    void updateMenu(Menu menu);


    void deleteMenu(Integer id);
}
