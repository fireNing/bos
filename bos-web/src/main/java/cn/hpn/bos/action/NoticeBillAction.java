package cn.hpn.bos.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import cn.hpn.bos.action.base.BaseAction;
import cn.hpn.bos.crm.Customer;
import cn.hpn.bos.crm.ICustomerService;
import cn.hpn.bos.entity.Noticebill;
import cn.hpn.bos.service.INoticeBillService;
@Controller
@Scope("prototype")
public class NoticeBillAction extends BaseAction<Noticebill> {
	
	@Autowired
	private ICustomerService customerService;
	@Autowired
	private INoticeBillService noticeBillService;
	
	public String findCustomerByTelephone(){
		Customer customer = customerService.findCustomerByTelephone(model.getTelephone());
		this.java2Json(customer, null);
		return NONE;
	}
	
	/**
	 * 保存新单，完成自动派单
	 */
	public String addNoticeBill(){
		noticeBillService.save(model);
		return "toNoticeBill";
	}
}
