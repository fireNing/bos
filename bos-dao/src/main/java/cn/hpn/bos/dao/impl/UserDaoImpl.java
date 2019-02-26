package cn.hpn.bos.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import cn.hpn.bos.dao.IUserDao;
import cn.hpn.bos.entity.User;
import cn.hpn.bos.dao.base.impl.BaseDaoImpl;

@Repository
public class UserDaoImpl extends BaseDaoImpl<User> implements IUserDao {

	public User findUserByUsernameAndPassword(User user) {
		String username = user.getUsername();
		String password = user.getPassword();
		String hql = "FROM User u WHERE u.username = ? AND u.password = ?";
		List<User> list = (List<User>) this.getHibernateTemplate().find(hql, username,password);
		if(list != null && list.size() > 0){
			return list.get(0);
		}
		return null;
	}
	/**
	 * 使用shiro框架对登录用户进行认证
	 */
	public User findUserByUsername(String username) {
		String hql  = "from User u where u.username = ? ";
		List<User> list = (List<User>) this.getHibernateTemplate().find(hql, username);
		if(list!=null && list.size()>0){
			return list.get(0);
		}
		return null;
	}

}
