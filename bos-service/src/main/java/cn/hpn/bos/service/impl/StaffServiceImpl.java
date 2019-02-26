package cn.hpn.bos.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.hpn.bos.dao.IStaffDao;
import cn.hpn.bos.entity.Staff;
import cn.hpn.bos.service.IStaffService;
import cn.hpn.bos.utils.PageBean;

@Service
@Transactional
public class StaffServiceImpl implements IStaffService {

	@Autowired
	private IStaffDao staffDao;
	
	public void save(Staff model) {
		staffDao.save(model);
	}

	public void findStaffList(PageBean pageBean) {
		staffDao.findPageBean(pageBean);
	}

	public void deleteBatch(String ids) {
		if(StringUtils.isNotBlank(ids)){
			String[] split = ids.split(",");
			for (String id : split) {
				staffDao.updateById("staff.deleteBatch",id);
			}
		}
		
	}

	public Staff findById(String id) {
		Staff staff = staffDao.findById(id);
		return staff;
	}
	
	public void update(Staff staff) {
		staffDao.update(staff);
	}
	/**
	 * 为定区查找为删除的取派员
	 */
	public List<Staff> findNotDeleteStaff() {
		DetachedCriteria dc = DetachedCriteria.forClass(Staff.class);
		dc.add(Restrictions.eq("deltag", "0"));
		return staffDao.findNotDelete(dc);
	}

}
