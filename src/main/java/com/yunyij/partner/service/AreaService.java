package com.yunyij.partner.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.github.jlf1997.spring_boot_sdk.oper.SpringDateJpaOper;
import com.yunyij.partner.model.Area;
import com.yunyij.partner.repository.AreaRepository;

@Service("areaService")
public class AreaService {

	@Autowired
	private AreaRepository areaRepository;
	
	
	
	/**
	 * 根据id查询
	 * @param id
	 * @return
	 */
	public Area findById(Long id) {
		return areaRepository.findOne(id);
	}
	
	/**
	 * 根据城市编码查询直接下级所有城市
	 * @param code
	 * @return
	 */
	public List<Area> getChildren(Area area){
		
		
		List<Area> list = areaRepository.findAll(new Specification<Area>() {

			@Override
			public Predicate toPredicate(Root<Area> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate>  predicates = new ArrayList<>();
				SpringDateJpaOper<Area> springDateJpaOper = new SpringDateJpaOper<>(root,query,cb);
				if(area!=null) {
					springDateJpaOper.eq(predicates, "pid", area.getId());
				}else {
					springDateJpaOper.eq(predicates, "level", 1);
				}
				query.where(predicates.toArray(new Predicate[predicates.size()]));
				
				return query.getRestriction();
			}
			
		});
		return list;
		
	}
	
	
	
	
	/**
	 * 根据code 获取省 市 县
	 * @param code
	 * @return
	 */
	public Area[] getCitys(Long code) {
		Area[] areas = new Area[3];
		if(code==null) {
			return areas;
		}
		Area area = findById(code);
		if(area==null) {
			return areas;
		}
		if(area.getLevel()==1) {
			areas[0] = area;
		}
		if(area.getLevel()==2) {
			areas[1] = area;
			areas[0] =  findById(area.getPid());
		}
		if(area.getLevel()==3) {
			areas[2] = area;
			areas[1] =  findById(area.getPid());
			areas[0] =  findById(areas[1].getPid());
		}
		return areas;
		
	}
	
	
	
}
