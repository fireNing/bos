package cn.hpn.bos.service;

import java.util.List;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.hibernate.criterion.DetachedCriteria;
import org.springframework.beans.factory.annotation.Autowired;

import cn.hpn.bos.dao.IFunctionDao;
import cn.hpn.bos.dao.IUserDao;
import cn.hpn.bos.entity.Function;
import cn.hpn.bos.entity.User;

public class ShiroRealm extends AuthorizingRealm {

	@Autowired
	private IUserDao userDao;
	@Autowired
	private IFunctionDao functionDao;
	
	//授权方法
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		//为用户授权；
		User user = (User) principals.getPrimaryPrincipal();
		//根据当前用户查询用户的权限;
		List<Function> list = null;
		if(user.getUsername().equals("admin")){
			//超级管理员拥有全部权限;
			DetachedCriteria dc = DetachedCriteria.forClass(Function.class);
			list = functionDao.findNotDelete(dc);
		}else{
			//普通用户查询数据获取相应的权限；
			list = functionDao.findFunctionByuserId(user.getId());
		}
		for (Function function : list) {
			info.addStringPermission(function.getCode());
		}
		
		return info;
	}
	//认证方法
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		//认证方式通过username查询数据库获取用户对象，将用户对象的密码和输入密码进行比较；
		UsernamePasswordToken mytoken = (UsernamePasswordToken) token;
		String username = mytoken.getUsername();
		User user = userDao.findUserByUsername(username);
		if(user==null){
			return null;
		}
		AuthenticationInfo info = new SimpleAuthenticationInfo(user, user.getPassword(), this.getName());
		//认证通过返回info信息；不通过抛出异常
		return info;
	}

}
