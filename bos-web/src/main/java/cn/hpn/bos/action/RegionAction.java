package cn.hpn.bos.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import cn.hpn.bos.action.base.BaseAction;
import cn.hpn.bos.entity.Region;
import cn.hpn.bos.service.IRegionService;
import cn.hpn.bos.utils.PinYin4jUtils;
@Controller
@Scope("prototype")
public class RegionAction extends BaseAction<Region> {

	private File regionFile;
	@Autowired
	private IRegionService regionService;
	public void setRegionFile(File regionFile) {
		this.regionFile = regionFile;
	}
	
	public String addRegion() throws Exception{
		
		List<Region> regionList = new ArrayList<Region>();
		//使用POI解析Excel文件
		HSSFWorkbook workbook = new HSSFWorkbook(new FileInputStream(regionFile));
		//Excle表格中的sheet1
		HSSFSheet sheetAt = workbook.getSheetAt(0);
		for (Row row : sheetAt) {
			int num = row.getRowNum();
			if(num==0){
				continue;
			}else{
				String id = row.getCell(0).getStringCellValue();
				String province = row.getCell(1).getStringCellValue();
				String city = row.getCell(2).getStringCellValue();
				String district = row.getCell(3).getStringCellValue();
				String postcode = row.getCell(4).getStringCellValue();
				String shortProvince = province.substring(0, province.length()-1);
				String shortCity = city.substring(0, city.length()-1);
				String shortDistrict = district.substring(0, district.length()-1);
				String pcd = shortProvince+shortCity+shortDistrict;
				String[] info = PinYin4jUtils.getHeadByString(pcd,false);
				String shortcode = StringUtils.join(info);
				String citycode = PinYin4jUtils.hanziToPinyin(city, "");
				Region region = new Region(id, province, city, district, postcode, shortcode, citycode, null);
				regionList.add(region);
			}
		}
		regionService.addBatch(regionList);
		workbook.close();
		return "toRegion";
	}
	
	public String regionList() throws IOException{
		regionService.getPageBean(pageBean);
		//转json对象;
		String[] excludes = {"currentPage","pageSize","dc","subareas"};
		java2Json(pageBean, excludes);
		return NONE;
	}
	private String q;
	public void setQ(String q) {
		this.q = q;
	}
	public String shortRegion() throws IOException{
		List regionList = null;
		if(StringUtils.isNotBlank(q)){
			//前端传了查询数据过来；
			regionList = regionService.findByQ(q);
		}else{
			regionList = regionService.findByAll();
		}
		String[] excludes = {"subareas"};
		this.java2Json(regionList, excludes);
		return NONE;
	}
}
