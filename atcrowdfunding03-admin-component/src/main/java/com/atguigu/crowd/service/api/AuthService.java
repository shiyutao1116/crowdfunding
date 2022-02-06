package com.atguigu.crowd.service.api;

import com.atguigu.crowd.entity.Auth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author shiyutao
 * @create 2022-01-16 14:45
 */
public interface AuthService {

    List<Auth> getAll();

    List<Integer> getAuthIdByRoleId(Integer roleId);

    void saveRoleAuthRelationship(Map<String, List<Integer>> map);



}
