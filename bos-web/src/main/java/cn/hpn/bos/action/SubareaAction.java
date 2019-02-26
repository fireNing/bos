package cn.hpn.bos.action;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletOutputStream;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import cn.hpn.bos.action.base.BaseAction;
import cn.hpn.bos.entity.Region;
import cn.hpn.bos.entity.Subarea;
import cn.hpn.bos.service.ISubareaService;
import cn.hpn.bos.utils.FileUtils;
@Controller
@Scope("prototype")
public class SubareaAction extends BaseAction<Subarea> {
	
	@Autowired
	private ISubareaService subareaService;
	
	public String addSubarea(){
		subareaService.save(model);
		return "toSubarea";
	}
		
	public String subareaList(){
		DetachedCriteria dc = pageBean.getDc();
		String addresskey = model.getAddresskey();
		if(StringUtils.isNotBlank(addresskey)){
			dc.add(Restrictions.like("addresskey", "%"+addresskey+"%"));
		}
		Region region = model.getRegion();
		if(region!=null){
			String province = region.getProvince();
			String city = region.getCity();
			String district = region.getDistrict();
			dc.createAlias("region", "r");
			if(StringUtils.isNotBlank(province)){
				dc.add(Restrictions.like("r.province", "%"+province+"%"));
			}
			if(StringUtils.isNotBlank(city)){
				dc.add(Restrictions.like("r.city", "%"+city+"%"));
			}
			if(StringUtils.isNotBlank(district)){
				dc.add(Restrictions.like("r.district", "%"+district+"%"));
			}
		}
		//查询所有的分区信息;
		subareaService.findSubareaList(pageBean);
		String[] excludes = {"decidedzone","subareas","currentPage","dc","pageSize"};
		this.java2Json(pageBean, excludes);
		return NONE;
	}
	/**
	 * 数据导出
	 * @return
	 * @throws Exception 
	 */
	public String exportXls() throws Exception{
		//查询所有数据;
		List<Subarea> subareaList = subareaService.findByAll();
		//创建一个excel表格;
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet dataSheet = workbook.createSheet("数据分区");
		//创建第一行;
		HSSFRow headRow = dataSheet.createRow(0);
		//创建第一列;
		headRow.createCell(0).setCellValue("分区编号");
		headRow.createCell(1).setCellValue("开始编号");
		headRow.createCell(2).setCellValue("结束编号");
		headRow.createCell(3).setCellValue("位置信息");
		headRow.createCell(4).setCellValue("省市区");
		//动态将数据填到excel表格中;
		for (Subarea subarea: subareaList) {
			HSSFRow dataRow = dataSheet.createRow(dataSheet.getLastRowNum()+1);
			dataRow.createCell(0).setCellValue(subarea.getId());
			dataRow.createCell(1).setCellValue(subarea.getStartnum());
			dataRow.createCell(2).setCellValue(subarea.getEndnum());
			dataRow.createCell(3).setCellValue(subarea.getPosition());
			dataRow.createCell(4).setCellValue(subarea.getRegion().getName());
		}
		//第三步：使用输出流进行文件下载（一个流、两个头）
		String filename = "分区数据.xls";
		String contextType = ServletActionContext.getServletContext().getMimeType(filename);
		ServletOutputStream out = ServletActionContext.getResponse().getOutputStream();
		ServletActionContext.getResponse().setContentType(contextType);
		//解决中文文件名乱码;
		String agent = ServletActionContext.getRequest().getHeader("User-Agent");
		filename = FileUtils.encodeDownloadFilename(filename, agent);
		ServletActionContext.getResponse().setHeader("content-disposition", "attachment;filename="+filename);
		workbook.write(out);
		return NONE;
	}
	/**
	 * 查询未关联定区的分区数据
	 */
	public String getSubareaAjax(){
		List<Subarea> subareaList = subareaService.findNotAssociation();
		String[] excludes = {"currentPage","dc","pageSize","decidedzone","region"};
		this.java2Json(subareaList, excludes);
		return NONE;
	}
	
	/**
	 * 通过decidedzoneId查询相关的分区信息;
	 */
	private String decidedzoneId;
	
	public String getDecidedzoneId() {
		return decidedzoneId;
	}
	public void setDecidedzoneId(String decidedzoneId) {
		this.decidedzoneId = decidedzoneId;
	}
	public String findListByDecidedzoneId(){
		List<Subarea> list = subareaService.findListByDecidedzoneId(decidedzoneId);
		String[] excludes = {"decidedzone","subareas"};
		this.java2Json(list, excludes);
		return NONE;
	}
	
	/**
	 * 显示区域分区图
	 */
	public String showSubareaGroupPie(){
		List<Object> list = subareaService.showSubareaGruop();
		this.java2Json(list, null);
		return NONE;
	}
}
