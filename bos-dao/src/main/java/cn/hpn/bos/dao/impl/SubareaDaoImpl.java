package cn.hpn.bos.dao.impl;

import java.io.Serializable;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import cn.hpn.bos.dao.ISubareaDao;
import cn.hpn.bos.dao.base.impl.BaseDaoImpl;
import cn.hpn.bos.entity.Subarea;
import cn.hpn.bos.utils.PageBean;
@Repository
public class SubareaDaoImpl extends BaseDaoImpl<Subarea> implements ISubareaDao {
	/*
	 * 查询未关联定区的分区
	 */
	public List<Subarea> findNotAssociation(DetachedCriteria dc) {
		return (List<Subarea>) this.getHibernateTemplate().findByCriteria(dc);
	}

	@Override
	public List<Subarea> findListByDecidedzoneId(String decidedzoneId) {
		DetachedCriteria dc = DetachedCriteria.forClass(Subarea.class);
		//添加过滤条件
		dc.add(Restrictions.eq("decidedzone.id", decidedzoneId));
		List<Subarea> list = (List<Subarea>) this.getHibernateTemplate().findByCriteria(dc);
		return list;
	}

	/**
	 * 显示区域分区图
	 */
	public List<Object> findRegionAndNum() {
		String hql = "select r.province , Count(*) from Subarea s left outer join s.region r group by r.province";
		List<Object> list = (List<Object>) this.getHibernateTemplate().find(hql);
		return list;
	}

}
