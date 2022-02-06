package com.atguigu.crowd.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import com.atguigu.crowd.CorwdConstant;
import com.atguigu.crowd.CrowdUtil;
import com.atguigu.crowd.LoginFailedException;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.atguigu.crowd.entity.Admin;
import com.atguigu.crowd.entity.AdminExample;
import com.atguigu.crowd.mapper.AdminMapper;
import com.atguigu.crowd.service.api.AdminService;

@Service
public class AdminServiceImpl implements AdminService {
	
	@Autowired
	private AdminMapper adminMapper;

	@Override
	public void saveAdmin(Admin admin) {
		String userPswd=admin.getUserPswd();
		userPswd=CrowdUtil.Md5(userPswd);
		admin.setUserPswd(userPswd);
		Date date = new Date();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String format1 = format.format(date);
		admin.setCreateTime(format1);
		adminMapper.insert(admin);
	}

	@Override
	public List<Admin> getAll() {
		return adminMapper.selectByExample(new AdminExample());
	}

	@Override
	public Admin getAdminByLoginAcct(String login, String userPswd) {
		AdminExample adminExample = new AdminExample();
		AdminExample.Criteria criteria = adminExample.createCriteria();
		criteria.andLoginEqualTo(login);
		List<Admin> admins = adminMapper.selectByExample(adminExample);
		if (admins==null||admins.size()==0){
			throw new LoginFailedException(CorwdConstant.MESSAGE_LONGIN_FAILED);
			}
		if (admins.size()>1){
			throw new RuntimeException(CorwdConstant.MESSAGE_SYSTEM_ERROR_LOGIN_NOT_UNIQUE);
		}
		Admin admin = admins.get(0);
		if (admin==null) {
			throw new LoginFailedException(CorwdConstant.MESSAGE_LONGIN_FAILED);
		}
		String userPswdfromdb = admin.getUserPswd();
		String userPswdForm = CrowdUtil.Md5(userPswd);
		if (!Objects.equals(userPswdfromdb,userPswdForm)){
			throw new LoginFailedException(CorwdConstant.MESSAGE_LONGIN_FAILED);

		}
		return admin;
	}

	@Override
	public PageInfo<Admin> getPageInfo(String keyWord, Integer pageNum, Integer pageSize) {
		PageHelper.startPage(pageNum,pageSize);
		List<Admin> admins = adminMapper.selectAdminByKeyword(keyWord);
		PageInfo<Admin> adminPageInfo = new PageInfo<>(admins);
		return adminPageInfo;


	}

	@Override
	public void remove(Integer adminId) {
		adminMapper.deleteByPrimaryKey(adminId);
	}

	@Override
	public Admin getAdminById(Integer adminId) {
		return adminMapper.selectByPrimaryKey(adminId);
	}

	@Override
	public void update(Admin admin) {
		adminMapper.updateByPrimaryKeySelective(admin);
	}

	@Override
	public void saveRelationship(Integer adminId, List<Integer> list) {
		adminMapper.deleteOldRelationship(adminId);
		if (list!=null&&list.size()!=0){

			adminMapper.saveNewRelationship(adminId,list);
		}

	}


}
