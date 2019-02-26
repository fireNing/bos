package cn.hpn.bos.service;

import java.util.List;

import cn.hpn.bos.entity.Staff;
import cn.hpn.bos.utils.PageBean;

public interface IStaffService {

	public void save(Staff model);

	public void findStaffList(PageBean pageBean);

	public void deleteBatch(String ids);

	public Staff findById(String id);

	public void update(Staff staff);

	public List<Staff> findNotDeleteStaff();

}
