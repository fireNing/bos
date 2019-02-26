package cn.hpn.bos.dao;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;

import cn.hpn.bos.dao.base.IBaseDao;
import cn.hpn.bos.entity.Subarea;

public interface ISubareaDao extends IBaseDao<Subarea> {

	public List<Subarea> findNotAssociation(DetachedCriteria dc);

	public List<Subarea> findListByDecidedzoneId(String decidedzoneId);

	public List<Object> findRegionAndNum();

}
