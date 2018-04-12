package com.yunyij.partner.vo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.github.jlf1997.spring_boot_sdk.vo_translation.ModeAndViewTranslation;
import com.github.jlf1997.spring_boot_sdk.vo_translation.Translate;
import com.yunyij.partner.model.Area;
import com.yunyij.partner.model.Partner;
import com.yunyij.partner.service.AreaService;


public class PartnerModeAndViewTranslation extends ModeAndViewTranslation<Partner,PartnerVO>{

	
	
	public PartnerModeAndViewTranslation( AreaService areaService) {
		this.setTranslate(new Translate<Partner,PartnerVO>() {

			@Override
			public void modelToView(Partner m, PartnerVO vo) {
				vo.setLoginPwd(null);
				if(m!=null) {
					Area[] areas = areaService.getCitys(m.getCityCode());
					vo.setProvince(areas[0]);
					vo.setCity(areas[1]);
					vo.setCounty(areas[2]);
				}
			}

			@Override
			public void viewToModel(PartnerVO arg0, Partner arg1) {
				// TODO Auto-generated method stub
				
			}

			
			
		});
	}
	
	
	
}
