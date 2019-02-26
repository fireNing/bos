package cn.hpn.bos.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.hpn.bos.dao.IRegionDao;
import cn.hpn.bos.entity.Region;
import cn.hpn.bos.service.IRegionService;
import cn.hpn.bos.utils.PageBean;
@Service
@Transactional
public class RegionServiceImpl implements IRegionService {
	@Autowired
	private IRegionDao regionDao;
	
	public void addBatch(List<Region> regionList) {
		regionDao.addBatch(regionList);
	}

	public void getPageBean(PageBean pageBean) {
		regionDao.findPageBean(pageBean);
	}

	public List findByAll() {
		return regionDao.findByAll();
	}

	public List findByQ(String q) {
		return regionDao.findByQ(q);
	}

}
