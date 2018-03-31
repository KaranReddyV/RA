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

import com.ojas.ra.domain.JobCategory;
import com.ojas.ra.exception.RAException;
import com.ojas.ra.mapper.JobCategoryMapper;
import com.ojas.ra.service.JobCategoryService;
import com.ojas.ra.util.MongoOrderByEnum;
import com.ojas.ra.util.MongoSortVO;

@Component
@Path("/jobCategory")
public class JobCategoryResource {

	@Autowired
	private JobCategoryService jobCategoryService;

	@Path("/createJobCategory")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public boolean createJobCategory(@RequestBody JobCategoryMapper jobCategoryMapper) {
		int count = 0;
		try {

			MongoSortVO sort = new MongoSortVO();
			sort.setPrimaryKey("_id");
			sort.setPrimaryOrderBy(MongoOrderByEnum.DESC);

			for (JobCategory jobCat: jobCategoryService.getAllJobCategory(sort)) {
				if (jobCat.getJobCategory().equalsIgnoreCase(jobCategoryMapper.getJobCategory()) == false) {

					count++;
				}

			}
			if (count == jobCategoryService.getAllJobCategory(sort).size()) {

				JobCategory jobCategory = new JobCategory();

				BeanUtils.copyProperties(jobCategoryMapper, jobCategory);

				return jobCategoryService.createJobCategory(jobCategory);
			} else {
				return false;
			}

		} catch (RAException e) {
			throw new RAException(e.getMessage());
		}

	}

	@Path("/updateJobCategory/{id}")
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public boolean updateJobCategory(@RequestBody JobCategoryMapper jobCategoryMapper, @PathParam("id") ObjectId id) {

		try {
			JobCategory jobCategory = new JobCategory();
			BeanUtils.copyProperties(jobCategoryMapper, jobCategory);
			jobCategory.set_id(id);
			return jobCategoryService.updateJobCategory(jobCategory);
		} catch (RAException e) {
			throw new RAException(e.getMessage());
		}
	}

	@Path("/listJobCategory/{pageNo}/{pageSize}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public List<JobCategoryMapper> getAllJobCategory(@Context ServletContext context, @PathParam("pageNo") int pageNo,
			@PathParam("pageSize") int pageSize) {

		try {
			MongoSortVO sort = new MongoSortVO();
			sort.setPrimaryKey("_id");
			sort.setPrimaryOrderBy(MongoOrderByEnum.DESC);
			List<JobCategory> list = jobCategoryService.getAllJobCategory(sort, pageNo, pageSize);
			return convertDomainToMapperList(list);
		} catch (RAException e) {
			throw new RAException(e.getMessage());
		}
	}

	public List<JobCategoryMapper> convertDomainToMapperList(List<JobCategory> jobCategories) {
		try {
			List<JobCategoryMapper> list = new ArrayList<JobCategoryMapper>();
			for (JobCategory jobCategory : jobCategories) {
				JobCategoryMapper jobCategoryMapper = new JobCategoryMapper();
				BeanUtils.copyProperties(jobCategory, jobCategoryMapper);
				jobCategoryMapper.set_id(jobCategory.get_id().toString());

				list.add(jobCategoryMapper);
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
	public JobCategoryMapper findOneByPrimaryId(@Context ServletContext context, @PathParam("value") ObjectId value)
			throws JsonGenerationException, JsonMappingException, IOException {
		try {
			Map<String, Object> condition = new HashMap<String, Object>();
			condition.put("_id", value);

			JobCategory jobCategory = jobCategoryService.getOneByCondition(condition);

			return convertDomainToMappar(jobCategory);
		} catch (RAException e) {
			throw new RAException(e.getMessage());
		}
	}

	private JobCategoryMapper convertDomainToMappar(JobCategory jobCategory) {
		try {
			JobCategoryMapper jobCategoryMapper = new JobCategoryMapper();
			BeanUtils.copyProperties(jobCategory, jobCategoryMapper);
			jobCategoryMapper.set_id(jobCategory.get_id().toString());

			return jobCategoryMapper;
		} catch (RAException e) {
			throw new RAException(e.getMessage());
		}
	}
	
}
