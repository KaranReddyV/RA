package com.ojas.ra.rest.resources;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ojas.ra.domain.Skills;
import com.ojas.ra.exception.RAException;
import com.ojas.ra.mapper.SkillsMapper;
import com.ojas.ra.service.SkillsService;
import com.ojas.ra.util.MongoOrderByEnum;
import com.ojas.ra.util.MongoSortVO;

@Component
@Path("/skills")
public class SkillsResource {

	@Autowired
	private SkillsService skillsService;

	@Path("/listSkills/{pageNo}/{pageSize}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public List<SkillsMapper> findAllByCondition(@Context ServletContext context, @PathParam("pageNo") int pageNo,
			@PathParam("pageSize") int pageSize) {
		try {
			MongoSortVO sort = new MongoSortVO();
			sort.setPrimaryKey("_id");
			sort.setPrimaryOrderBy(MongoOrderByEnum.DESC);
			List<Skills> list = skillsService.getAllObjects(sort, pageNo, pageSize);
			return convertDomainToMapperList(list);
		} catch (RAException e) {
			throw new RAException(e.getMessage());
		}
	}

	@Path("/findOneByCondition/{nameOftheProperty}/{valueOftheProperty}}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public SkillsMapper findOneByCondition(@Context ServletContext context,
			@PathParam("nameOftheProperty") String nameOftheProperty,
			@PathParam("valueOftheProperty") String valueOftheProperty) {
		try {
			Map<String, Object> condition = new HashMap<String, Object>();
			condition.put(nameOftheProperty, valueOftheProperty);
			Skills skills = skillsService.findOneByCondition(condition);
			return convertDomainToMappar(skills);
		} catch (RAException e) {
			throw new RAException(e.getMessage());
		}
	}

	private List<SkillsMapper> convertDomainToMapperList(List<Skills> skills) {
		try {
			List<SkillsMapper> list = new ArrayList<SkillsMapper>();
			for (Skills requirement : skills) {
				SkillsMapper skillsMapper = new SkillsMapper();
				BeanUtils.copyProperties(requirement, skillsMapper);
				skillsMapper.set_id(requirement.get_id().toString());
				list.add(skillsMapper);
			}
			return list;
		} catch (RAException e) {
			throw new RAException(e.getMessage());
		}
	}

	private SkillsMapper convertDomainToMappar(Skills skills) {
		try {
			SkillsMapper skillsMapper = new SkillsMapper();
			BeanUtils.copyProperties(skills, skillsMapper);
			skillsMapper.set_id(skills.get_id().toString());

			return skillsMapper;
		} catch (RAException e) {
			throw new RAException(e.getMessage());
		}
	}
}