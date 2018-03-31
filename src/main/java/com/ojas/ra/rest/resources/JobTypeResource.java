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

import com.ojas.ra.domain.JobType;
import com.ojas.ra.exception.RAException;
import com.ojas.ra.mapper.JobTypeMapper;
import com.ojas.ra.service.JobTypeSevice;
import com.ojas.ra.util.MongoOrderByEnum;
import com.ojas.ra.util.MongoSortVO;

@Component
@Path("/jobType")
public class JobTypeResource {

	@Autowired
	private JobTypeSevice jobTypeService;

	@Path("/createJobType")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public boolean createJobType(@RequestBody JobTypeMapper jobTypeMapper) {

		int count = 0;
		try {

			MongoSortVO sort = new MongoSortVO();
			sort.setPrimaryKey("_id");
			sort.setPrimaryOrderBy(MongoOrderByEnum.DESC);

			for (JobType jobType : jobTypeService.getAllJobType(sort)) {
				if (jobType.getJobType().equalsIgnoreCase(jobTypeMapper.getJobType()) == false) {

					count++;
				}

			}
			if (count == jobTypeService.getAllJobType(sort).size()) {

				JobType jobType = new JobType();

				BeanUtils.copyProperties(jobTypeMapper, jobType);

				return jobTypeService.createJobType(jobType);
			} else {
				return false;
			}

		} catch (RAException e) {
			throw new RAException(e.getMessage());
		}

	}

	@Path("/updateJobType/{id}")
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public boolean updateJobType(@RequestBody JobTypeMapper jobTypeMapper, @PathParam("id") ObjectId id) {

		try {
			JobType jobType = new JobType();
			BeanUtils.copyProperties(jobTypeMapper, jobType);
			jobType.set_id(id);
			return jobTypeService.updateJobType(jobType);
		} catch (RAException e) {
			throw new RAException(e.getMessage());
		}
	}

	@Path("/listJobType/{pageNo}/{pageSize}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public List<JobTypeMapper> getAllJobType(@Context ServletContext context, @PathParam("pageNo") int pageNo,
			@PathParam("pageSize") int pageSize) {

		try {
			MongoSortVO sort = new MongoSortVO();
			sort.setPrimaryKey("_id");
			sort.setPrimaryOrderBy(MongoOrderByEnum.DESC);
			List<JobType> list = jobTypeService.getAllJobType(sort, pageNo, pageSize);
			return convertDomainToMapperList(list);
		} catch (RAException e) {
			throw new RAException(e.getMessage());
		}
	}

	public List<JobTypeMapper> convertDomainToMapperList(List<JobType> jobTypes) {
		try {
			List<JobTypeMapper> list = new ArrayList<JobTypeMapper>();
			for (JobType jobType : jobTypes) {
				JobTypeMapper jobTypeMapper = new JobTypeMapper();
				BeanUtils.copyProperties(jobType, jobTypeMapper);
				jobTypeMapper.set_id(jobType.get_id().toString());

				list.add(jobTypeMapper);
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
	public JobTypeMapper findOneByPrimaryId(@Context ServletContext context, @PathParam("value") ObjectId value)
			throws JsonGenerationException, JsonMappingException, IOException {
		try {
			Map<String, Object> condition = new HashMap<String, Object>();
			condition.put("_id", value);

			JobType jobType = jobTypeService.getOneByCondition(condition);

			return convertDomainToMappar(jobType);
		} catch (RAException e) {
			throw new RAException(e.getMessage());
		}
	}

	private JobTypeMapper convertDomainToMappar(JobType jobType) {
		try {
			JobTypeMapper jobTypeMapper = new JobTypeMapper();
			BeanUtils.copyProperties(jobType, jobTypeMapper);
			jobTypeMapper.set_id(jobType.get_id().toString());

			return jobTypeMapper;
		} catch (RAException e) {
			throw new RAException(e.getMessage());
		}
	}
}
