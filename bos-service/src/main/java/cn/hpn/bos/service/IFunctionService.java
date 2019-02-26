package cn.hpn.bos.service;

import java.util.List;

import cn.hpn.bos.entity.Function;
import cn.hpn.bos.entity.User;
import cn.hpn.bos.utils.PageBean;

public interface IFunctionService {

	public List<Function> findFatherData();

	public void save(Function model);

	public void findPageBean(PageBean pageBean);

	public List<Function> findMenuByuserFunction();

}
