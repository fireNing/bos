package cn.hpn.bos.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.hpn.bos.dao.IUserDao;
import cn.hpn.bos.entity.Role;
import cn.hpn.bos.entity.User;
import cn.hpn.bos.service.IUserService;
import cn.hpn.bos.utils.MD5Utils;
import cn.hpn.bos.utils.PageBean;
@Service
@Transactional
public class UserServiceImpl implements IUserService {
	
	@Autowired
	private IUserDao userDao;

	public User login(User model) {
		String password = model.getPassword();
		String md5Password = MD5Utils.md5(password);
		model.setPassword(md5Password);
		return userDao.findUserByUsernameAndPassword(model);
	}

	public void updatePassword(String password, String id) {
		password = MD5Utils.md5(password);
		userDao.updateById("user.updatePassword",password,id);
	}

	/**
	 * 添加新的用户以及用户权限
	 */
	public void save(User model, String[] roleIds) {
		 model.setPassword(MD5Utils.md5(model.getPassword()));
		 userDao.save(model);
		 if(roleIds!=null && roleIds.length>0){
			 for (String roleId : roleIds) {
				 Role role = new Role(roleId);
				model.getRoles().add(role);
			}
		 }
	}

	/**
	 * 查询所有的用户信息
	 */
	public void findPageBean(PageBean pageBean) {
		 userDao.findPageBean(pageBean);
	}

}
