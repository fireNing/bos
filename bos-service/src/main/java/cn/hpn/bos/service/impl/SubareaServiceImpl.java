package cn.hpn.bos.service.impl;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.hpn.bos.dao.ISubareaDao;
import cn.hpn.bos.entity.Subarea;
import cn.hpn.bos.service.ISubareaService;
import cn.hpn.bos.utils.PageBean;
@Service
@Transactional
public class SubareaServiceImpl implements ISubareaService {

	@Autowired
	private ISubareaDao subareaDao;
	
	public void save(Subarea model) {
		subareaDao.save(model);;
	}

	public void findSubareaList(PageBean pageBean) {
		subareaDao.findPageBean(pageBean);
	}

	public List<Subarea> findByAll() {
		return subareaDao.findByAll();
	}

	@Override
	public List<Subarea> findNotAssociation() {
		DetachedCriteria dc = DetachedCriteria.forClass(Subarea.class);
		dc.add(Restrictions.isNull("decidedzone"));
		return subareaDao.findNotAssociation(dc);
	}

	@Override
	public List<Subarea> findListByDecidedzoneId(String decidedzoneId) {
		return subareaDao.findListByDecidedzoneId(decidedzoneId);
	}
	/**
	 * 显示区域分区图
	 */
	public List<Object> showSubareaGruop() {
		return subareaDao.findRegionAndNum();
	}


}
