package cn.hpn.bos.dao.impl;

import java.io.Serializable;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.springframework.stereotype.Repository;

import cn.hpn.bos.dao.IFunctionDao;
import cn.hpn.bos.dao.base.impl.BaseDaoImpl;
import cn.hpn.bos.entity.Function;
import cn.hpn.bos.utils.PageBean;
@Repository
public class FunctionDaoImpl extends BaseDaoImpl<Function> implements IFunctionDao {

	public List<Function> findByAll(){
		String hql  = "from Function where parentFunction is null";
		return (List<Function>) this.getHibernateTemplate().find(hql);
	}

	/**
	 * 根据用户的id查询相应的用户权限
	 */
	public List<Function> findFunctionByuserId(String userId) {
		String hql = "select distinct f from Function f left outer join f.roles r left outer join r.users u where u.id = ?";
		return (List<Function>) this.getHibernateTemplate().find(hql, userId);
	}

	/**
	 * 超级管理员查询所有的菜单
	 */
	public List<Function> findMenu() {
		String hql = "from Function f where generatemenu = '1' order by f.zindex desc ";
		return (List<Function>) this.getHibernateTemplate().find(hql);
	}

	/**
	 * 普通用户根据id查询相应的菜单
	 */
	public List<Function> findMenuByuserId(String userId) {
		String hql = "select distinct f from Function f left outer join "
				+ "f.roles r left outer join r.users u where u.id = ? and generatemenu = '1' "
				+ "order by f.zindex desc";
		return (List<Function>) this.getHibernateTemplate().find(hql, userId);
	}
	
	
}
