package cn.hpn.bos.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.hpn.bos.dao.IWorkordermanageDao;
import cn.hpn.bos.entity.Workordermanage;
import cn.hpn.bos.service.IWorkordermanageService;
@Service
@Transactional
public class WorkordermanageServiceImpl implements IWorkordermanageService {

	@Autowired
	private IWorkordermanageDao workordermanageDao;
	
	
	public void save(Workordermanage model){
		workordermanageDao.save(model);
	}

}
