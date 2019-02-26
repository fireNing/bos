package cn.hpn.bos.dao.impl;

import java.io.Serializable;
import java.util.List;

import org.springframework.stereotype.Repository;

import cn.hpn.bos.dao.IRegionDao;
import cn.hpn.bos.dao.base.impl.BaseDaoImpl;
import cn.hpn.bos.entity.Region;
import cn.hpn.bos.utils.PageBean;
@Repository
public class RegionDaoImpl extends BaseDaoImpl<Region> implements IRegionDao {

	public void addBatch(List<Region> regionList) {
		for (Region region : regionList) {
			this.getHibernateTemplate().saveOrUpdate(region);
		}
	}

	public List<Region> findByQ(String q) {
		String hql= "from Region r where r.province like ? or r.city like ? or r.district like ? or r.shortcode like ? or r.citycode like ?";
		List<Region> list = (List<Region>) this.getHibernateTemplate().find(hql, "%"+q+"%","%"+q+"%","%"+q+"%","%"+q+"%","%"+q+"%");
		return list;
	}

}
