package com.atguigu.crowd.service.api;

import com.atguigu.crowd.entity.Role;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * @author shiyutao
 * @create 2022-01-12 20:14
 */
public interface RoleService {
    PageInfo<Role> getPageInfo(String keyWord, Integer pageNum, Integer pageSize);

    void saveRole(Role role);

    void updateRole(Role role);
    void removeRole(List<Integer> roleIdList);

    List<Role> getAssignedRole(Integer adminId);

    List<Role> getUnAssignedRole(Integer adminId);
}
