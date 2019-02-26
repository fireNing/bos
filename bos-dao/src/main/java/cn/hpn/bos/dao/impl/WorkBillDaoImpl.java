package cn.hpn.bos.dao.impl;

import java.io.Serializable;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.springframework.stereotype.Repository;

import cn.hpn.bos.dao.IWorkBillDao;
import cn.hpn.bos.dao.base.impl.BaseDaoImpl;
import cn.hpn.bos.entity.Workbill;
import cn.hpn.bos.utils.PageBean;
@Repository
public class WorkBillDaoImpl extends BaseDaoImpl<Workbill> implements IWorkBillDao {

	
	/**
	 * 查询所有的新单
	 */
	public List<Workbill> findNewWorkbills() {
		String hql = "from Workbill where type = '新单' ";
		return (List<Workbill>) this.getHibernateTemplate().find(hql);
	}



}
