package cn.hpn.bos.dao;

import cn.hpn.bos.entity.User;
import cn.hpn.bos.dao.base.IBaseDao;

public interface IUserDao extends IBaseDao<User> {

	public User findUserByUsernameAndPassword(User user);

	public User findUserByUsername(String username);
}
