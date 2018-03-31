
package com.ojas.ra.rest.resources;

import java.io.IOException;
import java.io.InputStream;
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

import org.apache.commons.io.IOUtils;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

import com.mongodb.BasicDBObject;
import com.ojas.ra.domain.Requirement;
import com.ojas.ra.exception.RAException;
import com.ojas.ra.mapper.RequirementMapper;
import com.ojas.ra.service.RequirementService;
import com.ojas.ra.util.MongoAdvancedQuery;
import com.ojas.ra.util.MongoEqualQuery;
import com.ojas.ra.util.MongoOrderByEnum;
import com.ojas.ra.util.MongoSortVO;

@Component
@Path("/requirement")
public class RequirementResource {

	@Autowired
	private RequirementService requirementService;

	@POST
	@Path("/createRequirement")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public boolean createRequirement(@RequestBody RequirementMapper requirementMapper) throws IOException {

		Requirement requirement = new Requirement();
		BeanUtils.copyProperties(requirementMapper, requirement);
		requirement.setRegistrationId(new ObjectId(requirementMapper.getRegistrationId()));
		requirement.setStatus("Active");
		return requirementService.saveRequirement(requirement);
	}

	@Path("/bulkUpload/{id}")
	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public boolean bulkUpload(@FormDataParam("uploadFile") InputStream uploadFile, @PathParam("id") ObjectId id)
			throws IOException {
		boolean b = false;
		try {
			Requirement requirement = new Requirement();
			requirement.setRegistrationId(id);

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
				requirement.setUploadFile(doc);
				b = requirementService.createRequirement(requirement);
			}

		} catch (RAException e) {
			throw new RAException(e.getMessage());
		}
		return b;
	}

	@Path("/saveRequirement/{id}")
	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	public boolean saveRequirement(@RequestBody RequirementMapper requiementMapper, @PathParam("id") ObjectId id) {
		try {
			Requirement requirement = new Requirement();
			BeanUtils.copyProperties(requiementMapper, requirement);
			requirement.set_id(id);
			requirement.setRegistrationId(new ObjectId(requiementMapper.getRegistrationId()));
			return requirementService.saveRequirement(requirement);
		} catch (RAException e) {
			throw new RAException(e.getMessage());
		}
	}

	@Path("/deleteRequirement/{id}")
	@DELETE
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public boolean deleteRequirement(@Context ServletContext context, @PathParam("id") ObjectId id) {
		try {
			return requirementService.deleteRequirement(id);
		} catch (RAException e) {
			throw new RAException(e.getMessage());
		}
	}

	@Path("/listRequirements/{pageNo}/{pageSize}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public List<RequirementMapper> findAllByCondition(@Context ServletContext context, @PathParam("pageNo") int pageNo,
			@PathParam("pageSize") int pageSize) {
		try {
			MongoSortVO sort = new MongoSortVO();
			sort.setPrimaryKey("_id");
			sort.setPrimaryOrderBy(MongoOrderByEnum.DESC);
			List<Requirement> list = requirementService.getAllObjects(sort, pageNo, pageSize);
			return convertDomainToMapperList(list);
		} catch (RAException e) {
			throw new RAException(e.getMessage());
		}
	}

	@Path("/findRequirementsByRegistrationId/{registrationId}/{pageNo}/{pageSize}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public List<RequirementMapper> findRequirementsByRegistrationId(@Context ServletContext context,
			@PathParam("registrationId") String objectId, @PathParam("pageNo") int pageNo,
			@PathParam("pageSize") int pageSize) {
		try {
			MongoEqualQuery equal = new MongoEqualQuery();

			equal.setEqualto(new ObjectId(objectId));
			Map<String, MongoAdvancedQuery> requirementMappingcondition = new HashMap<String, MongoAdvancedQuery>();
			requirementMappingcondition.put("registrationId", equal);

			MongoSortVO sort = new MongoSortVO();
			sort.setPrimaryKey("registrationId");
			sort.setPrimaryOrderBy(MongoOrderByEnum.DESC);

			List<Requirement> requirementlist = requirementService.advancedFindByCondition(requirementMappingcondition,
					sort, pageNo, pageSize);

			return convertDomainToMapperList(requirementlist);
		} catch (RAException e) {
			throw new RAException(e.getMessage());
		}
	}

	@Path("/removeByPrimaryId/{id}")
	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public boolean removeByPrimaryId(@Context ServletContext context, @PathParam("id") String id) {
		try {
			return requirementService.removeByPrimaryId(id);
		} catch (RAException e) {
			throw new RAException(e.getMessage());
		}
	}

	@Path("/findOneByCondition/{nameOftheProperty}/{valueOftheProperty}}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public RequirementMapper findOneByCondition(@Context ServletContext context,
			@PathParam("nameOftheProperty") String nameOftheProperty,
			@PathParam("valueOftheProperty") String valueOftheProperty) {
		try {
			Map<String, Object> condition = new HashMap<String, Object>();
			condition.put(nameOftheProperty, valueOftheProperty);
			Requirement requirement = requirementService.findOneByCondition(condition);
			return convertDomainToMappar(requirement);
		} catch (RAException e) {
			throw new RAException(e.getMessage());
		}
	}

	@Path("/findOneByTextSearch/{text}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Document findOneByTextSearch(@Context ServletContext context, @PathParam("text") String text)
			throws JsonGenerationException, JsonMappingException, IOException {
		try {

			return requirementService.findOneByTextSearch(text, false, false);

		} catch (RAException e) {
			throw new RAException(e.getMessage());
		}
	}

	@Path("/findOneByPrimaryId/{value}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public RequirementMapper findOneByPrimaryId(@Context ServletContext context, @PathParam("value") ObjectId value)
			throws JsonGenerationException, JsonMappingException, IOException {
		try {
			Map<String, Object> condition = new HashMap<String, Object>();
			condition.put("_id", value);

			Requirement req = requirementService.findOneByCondition(condition);

			return convertDomainToMappar(req);
		} catch (RAException e) {
			throw new RAException(e.getMessage());
		}
	}

	@Path("/findOneAllByCondition/{nameOftheProperty}/{valueOftheProperty}/{pageNo}/{pageSize}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public List<RequirementMapper> findOneAllByCondition(@Context ServletContext context,
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
			List<Requirement> requirementlist = requirementService.advancedFindByCondition(requirementMappingcondition,
					sort, pageNo, pageSize);

			return convertDomainToMapperList(requirementlist);
		} catch (RAException e) {
			throw new RAException(e.getMessage());
		}
	}

	@PUT
	@Path("/inactiveOrActive/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public boolean inactiveOrActive(@RequestBody RequirementMapper requirementMapper, @PathParam("id") ObjectId id) {
		try {
			Requirement requirement = new Requirement();
			BeanUtils.copyProperties(requirementMapper, requirement);
			requirement.set_id(id);
			Map<String, Object> condition = new HashMap<String, Object>();
			condition.put("_id", id);
			Map<String, Object> target = new HashMap<String, Object>();
			target.put("status", requirement.getStatus());

			return requirementService.updateMapByCondition(condition, target);
		} catch (RAException e) {
			throw new RAException(e.getMessage());
		}
	}

	@Path("/listRequirements")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public List<RequirementMapper> findAllByCondition(@Context ServletContext context) {
		try {
			MongoSortVO sort = new MongoSortVO();
			sort.setPrimaryKey("_id");
			sort.setPrimaryOrderBy(MongoOrderByEnum.DESC);
			List<Requirement> list = requirementService.getAllObjects(sort);
			return convertDomainToMapperList(list);
		} catch (RAException e) {
			throw new RAException(e.getMessage());
		}
	}

	private List<RequirementMapper> convertDomainToMapperList(List<Requirement> requirements) {
		try {
			List<RequirementMapper> list = new ArrayList<RequirementMapper>();
			for (Requirement requirement : requirements) {
				RequirementMapper requirementMapper = new RequirementMapper();
				BeanUtils.copyProperties(requirement, requirementMapper);
				requirementMapper.set_id(requirement.get_id().toString());
				if (null != requirement.getRegistrationId()) {
					requirementMapper.setRegistrationId(requirement.getRegistrationId().toString());
				}
				list.add(requirementMapper);
			}
			return list;
		} catch (RAException e) {
			throw new RAException(e.getMessage());
		}
	}

	private RequirementMapper convertDomainToMappar(Requirement requirements) {
		try {
			RequirementMapper requirementMapper = new RequirementMapper();
			BeanUtils.copyProperties(requirements, requirementMapper);
			requirementMapper.set_id(requirements.get_id().toString());
			if (null != requirements.getRegistrationId()) {
				requirementMapper.setRegistrationId(requirements.getRegistrationId().toString());
			}
			return requirementMapper;
		} catch (RAException e) {
			throw new RAException(e.getMessage());
		}
	}
}
