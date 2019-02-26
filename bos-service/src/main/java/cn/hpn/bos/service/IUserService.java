package cn.hpn.bos.service;

import java.util.List;

import cn.hpn.bos.entity.User;
import cn.hpn.bos.utils.PageBean;

public interface IUserService {

	public User login(User model);

	public void updatePassword(String password, String id);

	public void save(User model, String[] roleIds);

	public void findPageBean(PageBean pageBean);

}
