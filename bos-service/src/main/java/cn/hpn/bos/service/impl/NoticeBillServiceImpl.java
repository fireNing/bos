package cn.hpn.bos.service.impl;

import java.sql.Timestamp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.opensymphony.xwork2.ActionContext;

import cn.hpn.bos.crm.ICustomerService;
import cn.hpn.bos.dao.IDecidedzoneDao;
import cn.hpn.bos.dao.INoticeBillDao;
import cn.hpn.bos.dao.IWorkBillDao;
import cn.hpn.bos.entity.Decidedzone;
import cn.hpn.bos.entity.Noticebill;
import cn.hpn.bos.entity.Staff;
import cn.hpn.bos.entity.User;
import cn.hpn.bos.entity.Workbill;
import cn.hpn.bos.service.INoticeBillService;
@Service
@Transactional
public class NoticeBillServiceImpl implements INoticeBillService {

	@Autowired
	private INoticeBillDao noticeBillDao;
	
	@Autowired
	private IDecidedzoneDao decidedzoneDao;
	
	@Autowired
	private ICustomerService customerService;
	
	@Autowired
	private IWorkBillDao workbillDao;
	
	public void save(Noticebill model) {
		//获取当前用户;
		User user = (User) ActionContext.getContext().getSession().get("user");
		//外键关联;
		model.setUser(user);
		noticeBillDao.save(model);
		//更具取件地址获取定区id;
		String pickaddress = model.getPickaddress();
		String decidedzoneId = customerService.findDecidedzoneIdByaddress(pickaddress);
		//根据定区Id查找相关定区的取派员;
		Decidedzone decidedzone = decidedzoneDao.findById(decidedzoneId);
		if(decidedzone!=null){
			//进去自动分单程序;
			Staff staff = decidedzone.getStaff();
			model.setStaff(staff);
			model.setOrdertype(Noticebill.ORDERTYPE_AUTO);//自动分单；
			Workbill workbill = new Workbill();
			workbill.setAttachbilltimes(0);//追单次数
			workbill.setBuildtime(new Timestamp(System.currentTimeMillis()));//建单时间
			workbill.setNoticebill(model);//业务通知单号
			workbill.setPickstate(workbill.PICKSTATE_NO);//未取件
			workbill.setRemark(model.getRemark());//备注
			workbill.setStaff(staff);//指定取派员;
			workbill.setType(workbill.TYPE_1);//类型：新单
			//保存工作单;
			workbillDao.save(workbill);
		}else{
			//进入人工分单;
			model.setOrdertype(Noticebill.ORDERTYPE_MAN);//人工分单
		}
	}

}
