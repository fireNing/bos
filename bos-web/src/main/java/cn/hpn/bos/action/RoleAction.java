package cn.hpn.bos.action;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import cn.hpn.bos.action.base.BaseAction;
import cn.hpn.bos.entity.Role;
import cn.hpn.bos.service.IRoleService;
@Controller
@Scope("prototype")
public class RoleAction extends BaseAction<Role> {

	@Autowired
	private IRoleService roleService;
	
	private String functionId;
	public void setFunctionId(String functionId) {
		this.functionId = functionId;
	}


	/**
	 * 添加角色
	 */
	public String addRole(){
		roleService.save(model,functionId);
		return "toRole";
	}
	/**
	 * 查询角色列表
	 */
	public String roleList(){
		roleService.findPageBean(pageBean);
		String[] excludes = {"functions","users","currentPage","dc","pageSize"};
		this.java2Json(pageBean, excludes);
		return NONE;
	}
	/**
	 * 查询说有的角色
	 */
	public String findAllRole(){
		List<Role> list = roleService.findAllRole();
		String[] excludes = {"functions","users"};
		this.java2Json(list, excludes);
		return NONE;
	}
}
