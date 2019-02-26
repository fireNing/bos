package cn.hpn.bos.service.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.hpn.bos.dao.IRoleDao;
import cn.hpn.bos.entity.Function;
import cn.hpn.bos.entity.Role;
import cn.hpn.bos.service.IRoleService;
import cn.hpn.bos.utils.PageBean;
@Service
@Transactional
public class RoleServiceImpl implements IRoleService {

	@Autowired
	private IRoleDao roleDao;
	
	
	//角色添加关联functionId
	public void save(Role model, String functionId) {
		roleDao.save(model);
		if(functionId !=null && StringUtils.isNotBlank(functionId)){
			String[] ids = functionId.split(",");
			for (String id : ids) {
				Function function = new Function(id);
				model.getFunctions().add(function);
			}
		}
	}
	
	/**
	 * 查询角色列表
	 */
	public void findPageBean(PageBean pageBean) {
		 roleDao.findPageBean(pageBean);
	}

	/**
	 * 查询所有的角色
	 */
	public List<Role> findAllRole() {
		List<Role> list = roleDao.findByAll();
		return list;
	}

}
