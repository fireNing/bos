package cn.hpn.bos.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.hpn.bos.dao.IDecidedzoneDao;
import cn.hpn.bos.dao.ISubareaDao;
import cn.hpn.bos.entity.Decidedzone;
import cn.hpn.bos.entity.Subarea;
import cn.hpn.bos.service.IDecidedzoneService;
import cn.hpn.bos.utils.PageBean;
@Service
@Transactional
public class DecidedzoneServiceImpl implements IDecidedzoneService {

	@Autowired
	private IDecidedzoneDao decidedzoneDao;
	@Autowired
	private ISubareaDao subareaDao;
	/**
	 * 保存关联分区到定区
	 */
	public void save(Decidedzone model, String[] subareaId) {
		decidedzoneDao.save(model);
		//由多的一方对外键进行维护；
		for (String id : subareaId) {
			Subarea subarea = subareaDao.findById(id);
			subarea.setDecidedzone(model);
			
		}
	}
	public void findPageBean(PageBean pageBean) {
		decidedzoneDao.findPageBean(pageBean);
	}

}
