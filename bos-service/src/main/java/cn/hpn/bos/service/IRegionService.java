package cn.hpn.bos.service;

import java.util.List;

import cn.hpn.bos.entity.Region;
import cn.hpn.bos.utils.PageBean;

public interface IRegionService {

	public void addBatch(List<Region> regionList);

	public void getPageBean(PageBean pageBean);

	public List findByAll();

	public List findByQ(String q);

}
