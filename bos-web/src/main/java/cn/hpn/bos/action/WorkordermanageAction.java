package cn.hpn.bos.action;

import java.io.IOException;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import cn.hpn.bos.action.base.BaseAction;
import cn.hpn.bos.entity.Workordermanage;
import cn.hpn.bos.service.IWorkordermanageService;
@Controller
@Scope("prototype")
public class WorkordermanageAction extends BaseAction<Workordermanage> {

	@Autowired
	private IWorkordermanageService workordermanageService;
	
	public String addWorkordermanage() throws Exception{
		String flag = "1";
		try {
			workordermanageService.save(model);
		} catch (Exception e) {
			flag = "0";
		}
		ServletActionContext.getResponse().setContentType("text/html;charset=utf-8");
		ServletActionContext.getResponse().getWriter().write(flag);
		return NONE;
	}
	
}
