package com.atguigu.crowd.service.impl;

import com.atguigu.crowd.entity.Auth;
import com.atguigu.crowd.entity.AuthExample;
import com.atguigu.crowd.mapper.AuthMapper;
import com.atguigu.crowd.service.api.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author shiyutao
 * @create 2022-01-16 15:19
 */
@Service
public class AuthServiceImpl implements AuthService {
    @Autowired
    private AuthMapper authMapper;

    @Override
    public List<Auth> getAll() {
        return authMapper.selectByExample(new AuthExample());
    }

    @Override
    public List<Integer> getAuthIdByRoleId(Integer roleId) {
        return authMapper.selectAssignedAuthIdByRoleID(roleId);
    }



    @Override
    public void saveRoleAuthRelationship(Map<String, List<Integer>> map) {
        List<Integer> roleIdList = map.get("roleId");
        Integer roleId = roleIdList.get(0);
        authMapper.deleteOldRelationship(roleId);
        List<Integer> authIdList = map.get("authIdArray");
        if (authIdList!=null&&authIdList.size()!=0){

            authMapper.saveNewRelationship(roleId,authIdList);
        }

    }
}
