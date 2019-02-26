package cn.hpn.bos.service;

import cn.hpn.bos.entity.Decidedzone;
import cn.hpn.bos.utils.PageBean;

public interface IDecidedzoneService {

	public void save(Decidedzone model, String[] subareaId);

	public void findPageBean(PageBean pageBean);

}
