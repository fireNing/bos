package cn.hpn.bos.dao.base.impl;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;

import cn.hpn.bos.dao.base.IBaseDao;
import cn.hpn.bos.utils.PageBean;

public class BaseDaoImpl<T> extends HibernateDaoSupport implements IBaseDao<T> {

	private Class<T> clazz;
	@Resource
	public void setMySessionFactory(SessionFactory sessionFactory){
		super.setSessionFactory(sessionFactory);
	}
	
	public BaseDaoImpl(){
		ParameterizedType tyClazz = (ParameterizedType) this.getClass().getGenericSuperclass();
		clazz = (Class<T>) tyClazz.getActualTypeArguments()[0];
	}

	public void save(T t) {
		this.getHibernateTemplate().save(t);
	}


	public void delete(T t) {
		this.getHibernateTemplate().delete(t);
	}


	public void update(T t) {
		this.getHibernateTemplate().update(t);
	}


	public T findById(Serializable id) {
		try{
			return this.getHibernateTemplate().get(clazz, id);
		}catch(Exception e){
			return null;
		}
	}


	public List<T> findByAll() {
		String hql ="from "+clazz.getSimpleName();
		return (List<T>) this.getHibernateTemplate().find(hql);
	}

	public void updateById(String queryName, Object... objects) {
		Session session = this.getSessionFactory().getCurrentSession();
		Query query = session.getNamedQuery(queryName);
		int i = 0;
		for (Object object : objects) {
			query.setParameter(i++, object);
		}
		query.executeUpdate();
	}

	public void findPageBean(PageBean pageBean) {
		//总数据数量;
		DetachedCriteria dc = pageBean.getDc();
		//查询总数据数量
		dc.setProjection(Projections.rowCount());
		List<Long> countList = (List<Long>) this.getHibernateTemplate().findByCriteria(dc);
		Integer count = countList.get(0).intValue();
		pageBean.setTotal(count);
		//清除设置函数;
		dc.setProjection(null);
		//指定hibernate封装对象方式
		dc.setResultTransformer(DetachedCriteria.ROOT_ENTITY);
		Integer firstResult = (pageBean.getCurrentPage()-1)*pageBean.getPageSize();
		Integer maxResults = pageBean.getPageSize();
		List rows = this.getHibernateTemplate().findByCriteria(dc, firstResult, maxResults);
		pageBean.setRows(rows);
		
	}

	public List<T> findNotDelete(DetachedCriteria dc) {
		return (List<T>) this.getHibernateTemplate().findByCriteria(dc);
	}

}
