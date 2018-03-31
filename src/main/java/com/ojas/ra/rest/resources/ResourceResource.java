package com.ojas.ra.rest.resources;

import java.io.IOException;
import java.io.InputStream;
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

import org.apache.commons.io.IOUtils;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.bson.types.ObjectId;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

import com.mongodb.BasicDBObject;
import com.ojas.ra.domain.Resource;
import com.ojas.ra.exception.RAException;
import com.ojas.ra.mapper.ResourceMapper;
import com.ojas.ra.service.ResourceService;
import com.ojas.ra.util.MongoAdvancedQuery;
import com.ojas.ra.util.MongoEqualQuery;
import com.ojas.ra.util.MongoOrderByEnum;
import com.ojas.ra.util.MongoSortVO;

@Component
@Path("resource")
public class ResourceResource {

	@Autowired
	private ResourceService resourceService;

	@Path("/createResource")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public boolean createResource(@RequestBody ResourceMapper resourceMapper) {
		try {
			Resource resource = new Resource();
			BeanUtils.copyProperties(resourceMapper, resource);
			resource.setStatus("Active");
			resource.setRegistrationId(new ObjectId(resourceMapper.getRegistrationId()));
			return resourceService.createResource(resource);
		} catch (RAException e) {
			throw new RAException(e.getMessage());
		}
	}

	@Path("/uploadFile/{id}")
	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public boolean uploadFile(@FormDataParam("uploadFile") InputStream uploadFile, @PathParam("id") ObjectId id)
			throws IOException {
		try {
			Resource resource = new Resource();
			resource.setRegistrationId(id);
			resource.setUploadResume(IOUtils.toByteArray(uploadFile));
			return resourceService.createResource(resource);
		} catch (RAException e) {
			throw new RAException(e.getMessage());
		}
	}

	@Path("/bulkUpload/{id}")
	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public boolean bulkUpload(@FormDataParam("uploadFile") InputStream uploadFile, @PathParam("id") ObjectId id)
			throws IOException {
		boolean b = false;
		try {
			Resource resource = new Resource();
			resource.setRegistrationId(id);

			XSSFWorkbook workbook = new XSSFWorkbook(uploadFile);

			XSSFSheet sheet = workbook.getSheetAt(0);
			int col_value = sheet.getRow(0).getLastCellNum();
			int row_num = sheet.getLastRowNum();
			List<String> DBheader = new ArrayList<String>();
			List<String> Data = new ArrayList<String>();

			for (int z = 1; z <= row_num; z++) {
				DBheader.clear();
				Data.clear();
				for (int j = 0; j < col_value; j++) {
					if (sheet.getRow(0).getCell(j) != null || sheet.getRow(0) != null) {
						String cel_value = sheet.getRow(0).getCell(j).toString();
						DBheader.add(cel_value.trim());
					} else {
						break;

					}
				}
				for (int k = 0; k < col_value; k++) {
					String data = " ";
					if (sheet.getRow(z).getCell(k) != null) {
						data = sheet.getRow(z).getCell(k).toString();
					}
					Data.add(data.trim());
				}
				BasicDBObject doc = new BasicDBObject();
				int l = 0;
				for (String headers : DBheader) {
					if (l > Data.size()) {
						break;
					}
					doc.append(headers, Data.get(l));
					l++;
				}
				resource.setBulkUpload(doc);
				b = resourceService.createResource(resource);
			}

		} catch (RAException e) {
			throw new RAException(e.getMessage());
		}
		return b;
	}

	@Path("/saveResource/{id}")
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public boolean saveResource(@RequestBody ResourceMapper resourceMapper, @PathParam("id") ObjectId id) {
		try {
			Resource resource = new Resource();
			BeanUtils.copyProperties(resourceMapper, resource);
			resource.set_id(id);
			resource.setRegistrationId(new ObjectId(resourceMapper.getRegistrationId()));
			return resourceService.saveResource(resource);
		} catch (RAException e) {
			throw new RAException(e.getMessage());
		}
	}

	@Path("/findOneByPrimaryId/{value}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public ResourceMapper findOneByPrimaryId(@Context ServletContext context, @PathParam("value") ObjectId value)
			throws JsonGenerationException, JsonMappingException, IOException {
		try {
			Map<String, Object> condition = new HashMap<String, Object>();
			condition.put("_id", value);

			Resource resource = resourceService.findOneByCondition(condition);

			return convertDomainToMappar(resource);
		} catch (RAException e) {
			throw new RAException(e.getMessage());
		}
	}

	@Path("/findResourcesByRegistrationId/{registrationId}/{pageNo}/{pageSize}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public List<ResourceMapper> findResourcesByRegistrationId(@Context ServletContext context,
			@PathParam("registrationId") String objectId, @PathParam("pageNo") int pageNo,
			@PathParam("pageSize") int pageSize) {
		try {
			MongoEqualQuery equal = new MongoEqualQuery();

			equal.setEqualto(new ObjectId(objectId));
			Map<String, MongoAdvancedQuery> resourceMappingcondition = new HashMap<String, MongoAdvancedQuery>();
			resourceMappingcondition.put("registrationId", equal);

			MongoSortVO sort = new MongoSortVO();
			sort.setPrimaryKey("registrationId");
			sort.setPrimaryOrderBy(MongoOrderByEnum.DESC);

			List<Resource> resourcelist = resourceService.advancedFindByCondition(resourceMappingcondition, sort,
					pageNo, pageSize);

			return convertDomainToMapperList(resourcelist);
		} catch (RAException e) {
			throw new RAException(e.getMessage());
		}
	}

	@Path("/findOneByCondition/{nameOftheProperty}/{valueOftheProperty}}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public ResourceMapper findOneByCondition(@Context ServletContext context,
			@PathParam("nameOftheProperty") String nameOftheProperty,
			@PathParam("valueOftheProperty") String valueOftheProperty) {
		try {
			Map<String, Object> condition = new HashMap<String, Object>();
			condition.put(nameOftheProperty, valueOftheProperty);
			Resource resource = resourceService.findOneByCondition(condition);
			return convertDomainToMappar(resource);
		} catch (RAException e) {
			throw new RAException(e.getMessage());
		}
	}

	@Path("/listResources/{pageNo}/{pageSize}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public List<ResourceMapper> findAllByCondition(@Context ServletContext context, @PathParam("pageNo") int pageNo,
			@PathParam("pageSize") int pageSize) {
		try {
			MongoSortVO sort = new MongoSortVO();
			sort.setPrimaryKey("_id");
			sort.setPrimaryOrderBy(MongoOrderByEnum.DESC);
			List<Resource> list = resourceService.getAllObjects(sort, pageNo, pageSize);
			return convertDomainToMapperList(list);
		} catch (RAException e) {
			throw new RAException(e.getMessage());
		}
	}

	@Path("/findResourcesByExperience/{experience}/{pageNo}/{pageSize}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public List<ResourceMapper> findResourcesByExperience(@Context ServletContext context,
			@PathParam("experience") String totalExperience, @PathParam("pageNo") int pageNo,
			@PathParam("pageSize") int pageSize) {
		try {
			MongoEqualQuery equal = new MongoEqualQuery();

			equal.setEqualto(totalExperience);
			Map<String, MongoAdvancedQuery> resourceMappingcondition = new HashMap<String, MongoAdvancedQuery>();
			resourceMappingcondition.put(totalExperience, equal);

			MongoSortVO sort = new MongoSortVO();
			sort.setPrimaryKey("_id");
			sort.setPrimaryOrderBy(MongoOrderByEnum.DESC);

			List<Resource> resourcelist = resourceService.advancedFindByCondition(resourceMappingcondition, sort,
					pageNo, pageSize);

			return convertDomainToMapperList(resourcelist);
		} catch (RAException e) {
			throw new RAException(e.getMessage());
		}
	}

	@Path("/findOneAllByCondition/{nameOftheProperty}/{valueOftheProperty}/{pageNo}/{pageSize}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public List<ResourceMapper> findOneAllByCondition(@Context ServletContext context,
			@PathParam("nameOftheProperty") String nameOftheProperty,
			@PathParam("valueOftheProperty") String valueOftheProperty, @PathParam("pageNo") int pageNo,
			@PathParam("pageSize") int pageSize) {
		try {
			MongoEqualQuery equal = new MongoEqualQuery();

			equal.setEqualto(valueOftheProperty);
			Map<String, MongoAdvancedQuery> requirementMappingcondition = new HashMap<String, MongoAdvancedQuery>();
			requirementMappingcondition.put(nameOftheProperty, equal);
			MongoSortVO sort = new MongoSortVO();
			sort.setPrimaryKey("_id");
			sort.setPrimaryOrderBy(MongoOrderByEnum.DESC);
			List<Resource> resourcelist = resourceService.advancedFindByCondition(requirementMappingcondition, sort,
					pageNo, pageSize);

			return convertDomainToMapperList(resourcelist);
		} catch (RAException e) {
			throw new RAException(e.getMessage());
		}
	}

	@PUT
	@Path("/inactiveOrActive/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public boolean inactiveOrActive(@RequestBody ResourceMapper resourceMapper, @PathParam("id") ObjectId id) {
		try {
			Resource resource = new Resource();
			BeanUtils.copyProperties(resourceMapper, resource);
			resource.set_id(id);
			Map<String, Object> condition = new HashMap<String, Object>();
			condition.put("_id", id);
			Map<String, Object> target = new HashMap<String, Object>();
			target.put("status", resource.getStatus());

			return resourceService.updateMapByCondition(condition, target);
		} catch (RAException e) {
			throw new RAException(e.getMessage());
		}
	}

	private List<ResourceMapper> convertDomainToMapperList(List<Resource> resources) {
		try {
			List<ResourceMapper> list = new ArrayList<ResourceMapper>();
			for (Resource resource : resources) {
				ResourceMapper resourceMapper = new ResourceMapper();
				BeanUtils.copyProperties(resource, resourceMapper);
				resourceMapper.set_id(resource.get_id().toString());
				if (null != resource.getRegistrationId()) {
					resourceMapper.setRegistrationId(resource.getRegistrationId().toString());
				}
				list.add(resourceMapper);
			}
			return list;
		} catch (RAException e) {
			throw new RAException(e.getMessage());
		}
	}

	private ResourceMapper convertDomainToMappar(Resource resources) {
		try {
			ResourceMapper resourceMapper = new ResourceMapper();
			BeanUtils.copyProperties(resources, resourceMapper);
			resourceMapper.set_id(resources.get_id().toString());
			if (null != resources.getRegistrationId()) {
				resourceMapper.setRegistrationId(resources.getRegistrationId().toString());
			}
			return resourceMapper;
		} catch (RAException e) {
			throw new RAException(e.getMessage());
		}
	}

}
