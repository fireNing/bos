package cn.hpn.bos.action;

import java.io.IOException;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import cn.hpn.bos.action.base.BaseAction;
import cn.hpn.bos.entity.Staff;
import cn.hpn.bos.service.IStaffService;
import cn.hpn.bos.utils.PageBean;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
@Controller
@Scope("prototype")
public class StaffAction extends BaseAction<Staff> {
	
	@Autowired
	private IStaffService staffService;
	
	public String addStaff(){
		
		staffService.save(model);
		
		return "toStaff";
	}
	public String staffList() throws IOException{
		staffService.findStaffList(pageBean);
		String[] excludes = {"currentPage","pageSize","dc","decidedzones"};
		java2Json(pageBean, excludes);
		return NONE;
	}
	
	private String ids;
	@RequiresPermissions("staff-delete")//当前用户实现方法是需要有staff-delete权限
	public String deleteBatch(){
		staffService.deleteBatch(ids);
		return "toStaff";
	}
	
	public String editStaff(){
		Staff staff = staffService.findById(model.getId());
		staff.setName(model.getName());
		staff.setTelephone(model.getTelephone());
		staff.setHaspda(model.getHaspda());
		staff.setStandard(model.getStandard());
		staff.setStation(model.getStation());
		staffService.update(staff);
		return "toStaff";
	}
	
	public String getStaffId(){
		List<Staff> staffList = staffService.findNotDeleteStaff();
		String[] excludes = {"decidedzones"};
		this.java2Json(staffList, excludes);
		return NONE;
	}
	
	public String getIds() {
		return ids;
	}
	public void setIds(String ids) {
		this.ids = ids;
	}
	
	


}
