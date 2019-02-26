package cn.hpn.bos.dao;

import java.util.List;

import cn.hpn.bos.dao.base.IBaseDao;
import cn.hpn.bos.entity.Region;

public interface IRegionDao extends IBaseDao<Region> {

	public void addBatch(List<Region> regionList);

	public List findByQ(String q);

}
