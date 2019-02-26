package cn.hpn.bos.service.impl;

import java.util.List;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.opensymphony.xwork2.ActionContext;

import cn.hpn.bos.dao.IFunctionDao;
import cn.hpn.bos.entity.Function;
import cn.hpn.bos.entity.User;
import cn.hpn.bos.service.IFunctionService;
import cn.hpn.bos.utils.PageBean;
@Service
@Transactional
public class FunctionServiceImpl implements IFunctionService {

	@Autowired
	private IFunctionDao functionDao;
	
	/**
	 * 查询数据库中的初始化系统数据
	 */
	public List<Function> findFatherData() {
		List<Function> list = functionDao.findByAll();
		return list;
	}

	public void save(Function model) {
		functionDao.save(model);
	}
	//分页方法
	public void findPageBean(PageBean pageBean) {
		functionDao.findPageBean(pageBean);
	}

	/**
	 * 根据用户权限动态加载菜单
	 */
	public List<Function> findMenuByuserFunction() {
		User user = (User) ActionContext.getContext().getSession().get("user");
		List<Function> list = null;
		if(user.getUsername().equals("admin")){
			//超级管理员，加载所有的菜单;
			list = functionDao.findMenu();
		}else{
			//普通用户;
			list = functionDao.findMenuByuserId(user.getId());
		}
		return list;
	}

}
