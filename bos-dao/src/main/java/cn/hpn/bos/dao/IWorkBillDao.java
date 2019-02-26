package cn.hpn.bos.dao;

import java.util.List;

import cn.hpn.bos.dao.base.IBaseDao;
import cn.hpn.bos.entity.Workbill;

public interface IWorkBillDao extends IBaseDao<Workbill>{

	public List<Workbill> findNewWorkbills();

}
