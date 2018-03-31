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

import com.ojas.ra.domain.PrimarySkills;
import com.ojas.ra.exception.RAException;
import com.ojas.ra.mapper.PrimarySkillsMapper;
import com.ojas.ra.service.PrimarySkillsService;
import com.ojas.ra.util.MongoOrderByEnum;
import com.ojas.ra.util.MongoSortVO;

@Component
@Path("/primarySkills")
public class PrimarySkillsResource {
	@Autowired
	private PrimarySkillsService primarySkillsService;

	@Path("/createPrimarySkills")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public boolean create(@RequestBody PrimarySkillsMapper primarySkillsMapper) {
		int count = 0;
		try {

			MongoSortVO sort = new MongoSortVO();
			sort.setPrimaryKey("_id");
			sort.setPrimaryOrderBy(MongoOrderByEnum.DESC);

			for (PrimarySkills primSkills : primarySkillsService.getAll(sort)) {
				if (primSkills.getPrimarySkills().equalsIgnoreCase(primarySkillsMapper.getPrimarySkills()) == false) {

					count++;
				}

			}
			if (count == primarySkillsService.getAll(sort).size()) {

				PrimarySkills primarySkills = new PrimarySkills();

				BeanUtils.copyProperties(primarySkillsMapper, primarySkills);

				return primarySkillsService.create(primarySkills);
			} else {
				return false;
			}

		} catch (RAException e) {
			throw new RAException(e.getMessage());
		}

	}

	@Path("/updatePrimarySkills/{id}")
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public boolean update(@RequestBody PrimarySkillsMapper primarySkillsMapper, @PathParam("id") ObjectId id) {

		try {
			PrimarySkills primarySkills = new PrimarySkills();
			BeanUtils.copyProperties(primarySkillsMapper, primarySkills);
			primarySkills.set_id(id);
			return primarySkillsService.update(primarySkills);
		} catch (RAException e) {
			throw new RAException(e.getMessage());
		}
	}

	@Path("/listPrimarySkills/{pageNo}/{pageSize}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public List<PrimarySkillsMapper> getAll(@Context ServletContext context, @PathParam("pageNo") int pageNo,
			@PathParam("pageSize") int pageSize) {

		try {
			MongoSortVO sort = new MongoSortVO();
			sort.setPrimaryKey("_id");
			sort.setPrimaryOrderBy(MongoOrderByEnum.DESC);
			List<PrimarySkills> list = primarySkillsService.getAll(sort, pageNo, pageSize);
			return convertDomainToMapperList(list);
		} catch (RAException e) {
			throw new RAException(e.getMessage());
		}
	}

	public List<PrimarySkillsMapper> convertDomainToMapperList(List<PrimarySkills> primarySkills) {
		try {
			List<PrimarySkillsMapper> list = new ArrayList<PrimarySkillsMapper>();
			for (PrimarySkills primarySkillss : primarySkills) {
				PrimarySkillsMapper primarySkillsMapper = new PrimarySkillsMapper();
				BeanUtils.copyProperties(primarySkillss, primarySkillsMapper);
				primarySkillsMapper.set_id(primarySkillss.get_id().toString());

				list.add(primarySkillsMapper);
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
	public PrimarySkillsMapper findOneByPrimaryId(@Context ServletContext context, @PathParam("value") ObjectId value)
			throws JsonGenerationException, JsonMappingException, IOException {
		try {
			Map<String, Object> condition = new HashMap<String, Object>();
			condition.put("_id", value);

			PrimarySkills primarySkills = primarySkillsService.getOneByCondition(condition);

			return convertDomainToMappar(primarySkills);
		} catch (RAException e) {
			throw new RAException(e.getMessage());
		}
	}

	private PrimarySkillsMapper convertDomainToMappar(PrimarySkills primarySkills) {
		try {
			PrimarySkillsMapper primarySkillsMapper = new PrimarySkillsMapper();
			BeanUtils.copyProperties(primarySkills, primarySkillsMapper);
			primarySkillsMapper.set_id(primarySkills.get_id().toString());

			return primarySkillsMapper;
		} catch (RAException e) {
			throw new RAException(e.getMessage());
		}
	}

}
