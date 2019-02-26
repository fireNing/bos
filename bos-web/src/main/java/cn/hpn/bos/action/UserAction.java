package cn.hpn.bos.action;

import java.io.IOException;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;

import cn.hpn.bos.action.base.BaseAction;
import cn.hpn.bos.entity.User;
import cn.hpn.bos.service.IUserService;
import cn.hpn.bos.utils.MD5Utils;

@Controller()
@Scope("prototype")
public class UserAction extends BaseAction<User> {

	private String checkcode;
	@Autowired
	private IUserService userService;

	public void setCheckcode(String checkcode) {
		this.checkcode = checkcode;
	}

	/**
	 * 用户登录功能;使用shiro框架进行认证
	 * 
	 * @return
	 */

	public String login() {
		String checkword = (String) ActionContext.getContext().getSession().get("key");
		if (StringUtils.isNotBlank(checkcode) && checkword.equals(checkcode)) {
			Subject subject = SecurityUtils.getSubject();// 获取当前用户对象(未认证)
			// 检验用户名密码信息;
			AuthenticationToken token = new UsernamePasswordToken(model.getUsername(),
					MD5Utils.md5(model.getPassword()));

			try {
				subject.login(token);
				// 获取通过认证的user对象
				User user = (User) subject.getPrincipal();
				// 登录成功;
				ActionContext.getContext().getSession().put("user", user);
				return "home";
			} catch (Exception e) {
				// 登录失败；
				e.printStackTrace();
				ActionContext.getContext().put("error", "帐号或密码错误!");
				return ERROR;
			}
			/*
			 * User user = userService.login(model); if(user!=null){ }else{ }
			 */
		} else {
			ActionContext.getContext().put("error", "验证码错误");
			return ERROR;
		}
	}

	public String logout() {
		ActionContext.getContext().getSession().put("user", null);
		return "toLogin";
	}

	/*	*//**
			 * 用户登录功能;
			 * 
			 * @return
			 */

	/*
	 * 
	 * public String login(){ String checkword = (String)
	 * ActionContext.getContext().getSession().get("key");
	 * if(StringUtils.isNotBlank(checkcode) && checkword.equals(checkcode)){
	 * User user = userService.login(model); if(user!=null){ //登录成功;
	 * ActionContext.getContext().getSession().put("user", user); return "home";
	 * }else{ ActionContext.getContext().put("error", "帐号或密码错误!"); return ERROR;
	 * } }else{ ActionContext.getContext().put("error", "验证码错误"); return ERROR;
	 * } } public String logout(){
	 * ActionContext.getContext().getSession().put("user", null); return
	 * "toLogin"; }
	 * 
	 */ /**
		 * 修改密码；
		 */
	public String editPassword() {
		String flag = "1";
		User user = (User) ActionContext.getContext().getSession().get("user");
		try {
			userService.updatePassword(model.getPassword(), user.getId());
		} catch (Exception e) {
			flag = "0";
			System.out.println(e.getMessage());
		}
		try {
			ServletActionContext.getResponse().setContentType("text/html;charset=utf-8");
			ServletActionContext.getResponse().getWriter().write(flag);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return NONE;
	}

	/**
	 * 添加一个用户以及用户权限
	 */
	private String[] roleIds;

	public void setRoleIds(String[] roleIds) {
		this.roleIds = roleIds;
	}

	public String addUser() {
		userService.save(model, roleIds);
		return "toUser";
	}

	// 用户列表显示;
	public String userList() {
		userService.findPageBean(pageBean);
		String[] excludes = { "noticebills", "roles" };
		this.java2Json(pageBean, excludes);
		return NONE;
	}
}
