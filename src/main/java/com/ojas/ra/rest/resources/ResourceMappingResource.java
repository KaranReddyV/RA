package com.ojas.ra.rest.resources;

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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

import com.ojas.ra.domain.ResourceMapping;
import com.ojas.ra.exception.RAException;
import com.ojas.ra.mapper.ResourceMappingMapper;
import com.ojas.ra.service.RequirementService;
import com.ojas.ra.service.ResourceMappingService;
import com.ojas.ra.util.MongoAdvancedQuery;
import com.ojas.ra.util.MongoEqualQuery;
import com.ojas.ra.util.MongoOrderByEnum;
import com.ojas.ra.util.MongoSortVO;

@Component
@Path("/resourceMapping")
public class ResourceMappingResource {

	@Autowired
	ResourceMappingService resourceMappingService;
	@Autowired
	RequirementService requirementService;

	@Path("/createResourceMapping")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public boolean createResourceMapping(@RequestBody ResourceMappingMapper resourceMappingMapper) {
		try {
			ResourceMapping resourceMapping = new ResourceMapping();

			resourceMapping.setCompanyId(new ObjectId(resourceMappingMapper.getCompanyId()));
			resourceMapping.setRequirementId(new ObjectId(resourceMappingMapper.getRequirementId()));
			resourceMapping.setVendorId(new ObjectId(resourceMappingMapper.getVendorId()));
			resourceMapping.setResourceId(new ObjectId(resourceMappingMapper.getResourceId()));
			return resourceMappingService.createResourceMapping(resourceMapping);

		} catch (RAException e) {
			throw new RAException(e.getMessage());
		}
	}

	@Path("/deleteResourceMapping/{id}")
	@DELETE
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public boolean deleteRequirement(@Context ServletContext context, @PathParam("id") ObjectId id) {
		try {
			return resourceMappingService.deleteResourceMapping(id);
		} catch (RAException e) {
			throw new RAException(e.getMessage());
		}
	}

	@PUT
	@Path("/updateResourceMapping/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public boolean updateResourceMapping(@RequestBody ResourceMappingMapper resourceMappingMapper,
			@PathParam("id") ObjectId id) {
		try {
			ResourceMapping resourceMapping = new ResourceMapping();
			resourceMapping.set_id(id);
			resourceMapping.setCompanyId(new ObjectId(resourceMappingMapper.getCompanyId()));
			resourceMapping.setRequirementId(new ObjectId(resourceMappingMapper.getRequirementId()));
			resourceMapping.setResourceId(new ObjectId(resourceMappingMapper.getResourceId()));
			resourceMapping.setVendorId(new ObjectId(resourceMappingMapper.getVendorId()));
			return resourceMappingService.updateResourceMapping(resourceMapping);
		} catch (RAException e) {
			throw new RAException(e.getMessage());
		}
	}

	@Path("/findAllByCondition/{nameOftheProperty}/{valueOftheProperty}/{pageNo}/{pageSize}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public List<ResourceMappingMapper> findAllByCondition(@Context ServletContext context,
			@PathParam("nameOftheProperty") String nameOftheProperty,
			@PathParam("valueOftheProperty") String valueOftheProperty, @PathParam("pageNo") int pageNo,
			@PathParam("pageSize") int pageSize) {
		try {
			MongoEqualQuery equal = new MongoEqualQuery();

			equal.setEqualto(new ObjectId(valueOftheProperty));
			Map<String, MongoAdvancedQuery> condition = new HashMap<String, MongoAdvancedQuery>();
			condition.put(nameOftheProperty, equal);
			MongoSortVO sort = new MongoSortVO();
			sort.setPrimaryKey("_id");
			sort.setPrimaryOrderBy(MongoOrderByEnum.DESC);
			List<ResourceMapping> resourceMappinglist = resourceMappingService.advancedFind(condition, sort, pageNo,
					pageSize);
			return convertDomainToMapperList(resourceMappinglist);
		} catch (RAException e) {
			throw new RAException(e.getMessage());
		}
	}

	private List<ResourceMappingMapper> convertDomainToMapperList(List<ResourceMapping> resourceMappings) {
		try {
			List<ResourceMappingMapper> list = new ArrayList<ResourceMappingMapper>();
			for (ResourceMapping resourceMapping : resourceMappings) {
				ResourceMappingMapper resourceMappingMapper = new ResourceMappingMapper();

				resourceMappingMapper.set_id(resourceMapping.get_id().toString());
				resourceMappingMapper.setCompanyId(resourceMapping.getCompanyId().toString());
				resourceMappingMapper.setRequirementId(resourceMapping.getRequirementId().toString());
				resourceMappingMapper.setResourceId(resourceMapping.getResourceId().toString());
				resourceMappingMapper.setVendorId(resourceMapping.getVendorId().toString());

				list.add(resourceMappingMapper);
			}
			return list;
		} catch (RAException e) {
			throw new RAException(e.getMessage());
		}
	}
}
