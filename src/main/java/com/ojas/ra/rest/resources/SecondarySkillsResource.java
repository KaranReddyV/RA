package com.ojas.ra.rest.resources;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.bson.types.ObjectId;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

import com.ojas.ra.domain.SecondarySkills;
import com.ojas.ra.exception.RAException;
import com.ojas.ra.mapper.SecondarySkillsMapper;
import com.ojas.ra.service.SecondatySkillsService;
import com.ojas.ra.util.MongoOrderByEnum;
import com.ojas.ra.util.MongoSortVO;

@Component
@Path("/secondarySkills")
public class SecondarySkillsResource {
	@Autowired
	private SecondatySkillsService secondarySkillsService;

	@Path("/createSecondarySkills")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public boolean createJobType(@RequestBody SecondarySkillsMapper secondarySkillsMapper) {

		int count = 0;
		try {

			MongoSortVO sort = new MongoSortVO();
			sort.setPrimaryKey("_id");
			sort.setPrimaryOrderBy(MongoOrderByEnum.DESC);

			for (SecondarySkills secondarySkills : secondarySkillsService.getAll(sort)) {
				if (secondarySkills.getSecondarySkills().equalsIgnoreCase(secondarySkillsMapper.getSecondarySkills()) == false) {

					count++;
				}

			}
			if (count == secondarySkillsService.getAll(sort).size()) {

				SecondarySkills secondarySkills = new SecondarySkills();

				BeanUtils.copyProperties(secondarySkillsMapper, secondarySkills);

				return secondarySkillsService.create(secondarySkills);
			} else {
				return false;
			}

		} catch (RAException e) {
			throw new RAException(e.getMessage());
		}

	}
	@Path("/updateSecondarySkill/{id}")
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public boolean update(@RequestBody SecondarySkillsMapper secondarySkillsMapper, @PathParam("id") ObjectId id) {

		try {
			SecondarySkills secondarySkills = new SecondarySkills();
			BeanUtils.copyProperties(secondarySkillsMapper, secondarySkills);
			secondarySkills.set_id(id);
			return secondarySkillsService.update(secondarySkills);
		} catch (RAException e) {
			throw new RAException(e.getMessage());
		}
	}


	
	@Path("/listSecondarySkills/{pageNo}/{pageSize}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public List<SecondarySkillsMapper> getAll(@Context ServletContext context, @PathParam("pageNo") int pageNo,
			@PathParam("pageSize") int pageSize) {

		try {
			MongoSortVO sort = new MongoSortVO();
			sort.setPrimaryKey("_id");
			sort.setPrimaryOrderBy(MongoOrderByEnum.DESC);
			List<SecondarySkills> list = secondarySkillsService.getAll(sort, pageNo, pageSize);
			return convertDomainToMapperList(list);
		} catch (RAException e) {
			throw new RAException(e.getMessage());
		}
	}
	public List<SecondarySkillsMapper> convertDomainToMapperList(List<SecondarySkills> secondarySkills) {
		try {
			List<SecondarySkillsMapper> list = new ArrayList<SecondarySkillsMapper>();
			for (SecondarySkills secondarySkillss : secondarySkills) {
				SecondarySkillsMapper secondarySkillsMapper = new SecondarySkillsMapper();
				BeanUtils.copyProperties(secondarySkillss, secondarySkillsMapper);
				secondarySkillsMapper.set_id(secondarySkillss.get_id().toString());

				list.add(secondarySkillsMapper);
			}
			return list;
		} catch (RAException e) {
			throw new RAException(e.getMessage());
		}
	}

	@Path("/findOneByPrimaryId/{value}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public SecondarySkillsMapper findOneByPrimaryId(@Context ServletContext context, @PathParam("value") ObjectId value)
			throws JsonGenerationException, JsonMappingException, IOException {
		try {
			Map<String, Object> condition = new HashMap<String, Object>();
			condition.put("_id", value);

			SecondarySkills secondarySkills = secondarySkillsService.getOneByCondition(condition);

			return convertDomainToMappar(secondarySkills);
		} catch (RAException e) {
			throw new RAException(e.getMessage());
		}
	}
	private SecondarySkillsMapper convertDomainToMappar(SecondarySkills secondarySkills) {
		try {
			SecondarySkillsMapper secondarySkillsMapper = new SecondarySkillsMapper();
			BeanUtils.copyProperties(secondarySkills, secondarySkillsMapper);
			secondarySkillsMapper.set_id(secondarySkills.get_id().toString());

			return secondarySkillsMapper;
		} catch (RAException e) {
			throw new RAException(e.getMessage());
		}
	}
}
