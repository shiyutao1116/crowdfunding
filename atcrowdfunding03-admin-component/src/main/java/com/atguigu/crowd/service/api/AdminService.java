package com.atguigu.crowd.service.api;

import java.util.List;

import com.atguigu.crowd.entity.Admin;
import com.github.pagehelper.PageInfo;

public interface AdminService {
	
	void saveAdmin(Admin admin);

	List<Admin> getAll();

    Admin getAdminByLoginAcct(String login, String userPswd);

    PageInfo<Admin> getPageInfo(String keyWord,Integer pageNum,Integer pageSize);

    void remove(Integer adminId);

    Admin getAdminById(Integer adminId);

    void update(Admin admin);

    void saveRelationship(Integer adminId, List<Integer> list);
}
