package cn.hpn.bos.dao;

import java.util.List;

import cn.hpn.bos.dao.base.IBaseDao;
import cn.hpn.bos.entity.Function;

public interface IFunctionDao extends IBaseDao<Function> {

	public List<Function> findFunctionByuserId(String id);

	public List<Function> findMenu();

	public List<Function> findMenuByuserId(String id);

}
