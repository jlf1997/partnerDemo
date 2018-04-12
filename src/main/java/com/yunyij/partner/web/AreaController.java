package com.yunyij.partner.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.jlf1997.spring_boot_sdk.response.ResponseObject;
import com.yunyij.partner.model.Area;
import com.yunyij.partner.service.AreaService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;


@RestController
@RequestMapping("area")
public class AreaController {

	@Autowired
	private AreaService areaService;
	
	
	@ApiOperation(value = "获取当前下级城市", notes = "")
	@RequestMapping(value = "/getChildrenCitys", method = RequestMethod.GET)
	public ResponseObject<Area> getChildrenCitys(			
			@ApiParam(name = "id",value="城市id",required = false) @RequestParam(value = "id",required = false) Long id
			){
		Area area = null;
		if(id!=null) {
			area = areaService.findById(id);
		}		
		List<Area> list = areaService.getChildren(area);
		return new ResponseObject<Area>().success(list);
		
	}
}
