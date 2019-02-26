package cn.hpn.bos.dao.base;

import java.io.Serializable;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;

import cn.hpn.bos.utils.PageBean;


public interface IBaseDao<T> {
	
	public void save(T t) ;
    public void delete(T t);
    public void update(T t);
    public T findById(Serializable id);
    public List<T> findByAll();
    public List<T> findNotDelete(DetachedCriteria dc);
    public void updateById(String queryName,Object...objects);
    public void findPageBean(PageBean pageBean);
}