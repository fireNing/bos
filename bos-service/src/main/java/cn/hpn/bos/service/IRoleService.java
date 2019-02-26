package cn.hpn.bos.service;

import java.util.List;

import cn.hpn.bos.entity.Role;
import cn.hpn.bos.utils.PageBean;

public interface IRoleService {

	public void save(Role model, String functionId);

	public void findPageBean(PageBean pageBean);

	public List<Role> findAllRole();
	
}
