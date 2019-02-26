package cn.hpn.bos.action;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import cn.hpn.bos.action.base.BaseAction;
import cn.hpn.bos.entity.Function;
import cn.hpn.bos.service.IFunctionService;
@Controller
@Scope("prototype")
public class FunctionAction extends BaseAction<Function> {

	@Autowired
	private IFunctionService functionService;
	/**
	 * 查找初始化数据
	 * @return
	 */
	public String initialineFatherData(){
		List<Function> list = functionService.findFatherData();
		String[] excludes = {"parentFunction","roles"};
		this.java2Json(list, excludes);
		return NONE;
	}
	//添加新的系统数据；
	public String addFunctionData(){
		if(model.getCode()==null && StringUtils.isBlank(model.getCode())){
			return "MethodError";
		}
		Function parentFunction = model.getParentFunction();
		if(parentFunction !=null && parentFunction.getId().equals("") ){
			model.setParentFunction(null);
		}
		functionService.save(model);
		return "toFunction";
	}
	//系统数据分页；
	public String functionList(){
		String page = model.getPage();
		pageBean.setCurrentPage(Integer.parseInt(page));
		functionService.findPageBean(pageBean);
		String[] excludes = {"parentFunction","roles","children","currentPage","dc","pageSize"};
		this.java2Json(pageBean, excludes);
		return NONE;
	}
	/**
	 * 根据用户权限动态加载菜单
	 */
	public String findMenuByuserFunction(){
		List<Function> list = functionService.findMenuByuserFunction();
		String[] excludes = {"parentFunction","roles","children"};
		this.java2Json(list, excludes);
		return NONE;
	}
}
