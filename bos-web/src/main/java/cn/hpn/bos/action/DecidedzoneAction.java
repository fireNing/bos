package cn.hpn.bos.action;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import cn.hpn.bos.action.base.BaseAction;
import cn.hpn.bos.crm.Customer;
import cn.hpn.bos.crm.ICustomerService;
import cn.hpn.bos.entity.Decidedzone;
import cn.hpn.bos.service.IDecidedzoneService;
@Controller
@Scope("prototype")
public class DecidedzoneAction extends BaseAction<Decidedzone> {

	@Autowired
	private IDecidedzoneService decidedzoneService;
	
	private String[] subareaId;
	public void setSubareaId(String[] subareaId) {
		this.subareaId = subareaId;
	}
	/**
	 * 保存未关联分区到定区
	 * @return
	 */
	public String addDecidedzone(){
		decidedzoneService.save(model,subareaId);
		return "toDecidedzone";
	}
	
	/**
	 * 定区分页显示
	 */
	public String decidedzoneList(){
		decidedzoneService.findPageBean(pageBean);
		String[] excludes = {"currentPage","pageSize","dc","subareas","decidedzones",};
		this.java2Json(pageBean, excludes);
		return NONE;
	}
	
	public String noAssociationCustomer(){
		List<Customer> list = poxy.findNotAssociation();
		this.java2Json(list, null);
		return NONE;
	}
	private String decidedzoneId;
	public void setDecidezoneId(String decidedzoneId) {
		this.decidedzoneId = decidedzoneId;
	}
	
	public String getDecidedzoneId() {
		return decidedzoneId;
	}
	public void setDecidedzoneId(String decidedzoneId) {
		this.decidedzoneId = decidedzoneId;
	}
	public String AssociationCustomer(){
		List<Customer> list = poxy.findHasAssociation(decidedzoneId);
		java2Json(list, null);
		return NONE;
	}
	
	private List<Integer> customerIds;
	public List<Integer> getCustomerIds() {
		return customerIds;
	}
	public void setCustomerIds(List<Integer> customerIds) {
		this.customerIds = customerIds;
	}
	public String updateAssociationCustomer(){
		poxy.decidedzoneAssociationCustomer(model.getId(), customerIds);
		return "toDecidedzone";
	}
	
}
