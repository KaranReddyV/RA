package com.ojas.ra.rest.resources;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
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

import com.ojas.ra.domain.Plans;
import com.ojas.ra.exception.RAException;
import com.ojas.ra.mapper.PlansMapper;
import com.ojas.ra.service.PlansService;
import com.ojas.ra.util.MongoAdvancedQuery;
import com.ojas.ra.util.MongoEqualQuery;
import com.ojas.ra.util.MongoOrderByEnum;
import com.ojas.ra.util.MongoSortVO;

@Component
@Path("/plans")
public class PlansResource {

	@Autowired
	private PlansService plansService;

	@Path("/createPlans")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public boolean createPlans(@RequestBody PlansMapper plansMapper) {

		try {
			Plans plans = new Plans();
			BeanUtils.copyProperties(plansMapper, plans);

			return plansService.createPlans(plans);
		} catch (RAException e) {
			throw new RAException(e.getMessage());
		}
	}

	@Path("/updatePlans/{id}")
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public boolean updatePlans(@RequestBody PlansMapper plansMapper, @PathParam("id") ObjectId id) {
		try {
			Plans plans = new Plans();
			BeanUtils.copyProperties(plansMapper, plans);
			plans.set_id(id);

			return plansService.updatePlans(plans);
		} catch (RAException e) {
			throw new RAException(e.getMessage());
		}
	}

	@DELETE
	@Path("/deletePlans/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public boolean deletePlansById(@Context ServletContext context, @PathParam("id") ObjectId id) {
		try {
			return plansService.deletePlansById(id);
		} catch (RAException e) {
			throw new RAException(e.getMessage());
		}
	}

	//

	@Path("/getplansByPrimaryId/{value}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public PlansMapper findPlansByPrimaryId(@Context ServletContext context, @PathParam("value") ObjectId value)
			throws JsonGenerationException, JsonMappingException, IOException {
		try {
			Map<String, Object> condition = new HashMap<String, Object>();
			condition.put("_id", value);
			Plans plans = plansService.getPlansByPrimaryId(condition);

			return convertDomainToMappar(plans);
		} catch (RAException e) {
			throw new RAException(e.getMessage());
		}
	}

	private PlansMapper convertDomainToMappar(Plans plans) {
		try {
			PlansMapper plansMapper = new PlansMapper();

			BeanUtils.copyProperties(plans, plansMapper);
			plansMapper.set_id(plans.get_id().toString());

			return plansMapper;
		} catch (RAException e) {
			throw new RAException(e.getMessage());
		}
	}

	@Path("/listPlans/{pageNo}/{pageSize}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public List<PlansMapper> getAllPlans(@Context ServletContext context, @PathParam("pageNo") int pageNo,
			@PathParam("pageSize") int pageSize) {
		try {
			MongoSortVO sort = new MongoSortVO();
			sort.setPrimaryKey("_id");
			sort.setPrimaryOrderBy(MongoOrderByEnum.DESC);
			List<Plans> list = plansService.getAllPlans(sort, pageNo, pageSize);
			return convertDomainToMapperList(list);
		} catch (RAException e) {
			throw new RAException(e.getMessage());
		}
	}

	@Path("/findAllByCondition/{nameOftheProperty}/{valueOftheProperty}/{pageNo}/{pageSize}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public List<PlansMapper> advanceFind(@Context ServletContext context,
			@PathParam("nameOftheProperty") String nameOftheProperty,
			@PathParam("valueOftheProperty") String valueOftheProperty, @PathParam("pageNo") int pageNo,
			@PathParam("pageSize") int pageSize) {
		try {
			MongoEqualQuery equal = new MongoEqualQuery();
            equal.setEqualto(valueOftheProperty);
			Map<String, MongoAdvancedQuery> condition = new HashMap<String, MongoAdvancedQuery>();
			condition.put(nameOftheProperty, equal);
			MongoSortVO sort = new MongoSortVO();
			sort.setPrimaryOrderBy(MongoOrderByEnum.DESC);
			List<Plans> planslist = plansService.advancedFind(condition, sort, pageNo, pageSize);

			return convertDomainToMapperList(planslist);
		} catch (RAException e) {
			throw new RAException(e.getMessage());
		}
	}

	private List<PlansMapper> convertDomainToMapperList(List<Plans> plans) {
		try {
			List<PlansMapper> list = new ArrayList<PlansMapper>();
			for (Plans planss : plans) {
				PlansMapper plansMapper = new PlansMapper();
				BeanUtils.copyProperties(planss, plansMapper);
				plansMapper.set_id(planss.get_id().toString());
				list.add(plansMapper);
			}
			return list;
		} catch (RAException e) {
			throw new RAException(e.getMessage());
		}
	}
}
