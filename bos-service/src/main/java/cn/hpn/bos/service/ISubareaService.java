package cn.hpn.bos.service;

import java.util.List;

import cn.hpn.bos.entity.Subarea;
import cn.hpn.bos.utils.PageBean;

public interface ISubareaService {

	public void save(Subarea model);

	public void findSubareaList(PageBean pageBean);

	public List<Subarea> findByAll();

	public List<Subarea> findNotAssociation();

	public List<Subarea> findListByDecidedzoneId(String decidedzoneId);

	public List<Object> showSubareaGruop();

}
