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

import com.ojas.ra.domain.Role;
import com.ojas.ra.exception.RAException;
import com.ojas.ra.mapper.RoleMapper;
import com.ojas.ra.service.RoleService;
import com.ojas.ra.util.MongoOrderByEnum;
import com.ojas.ra.util.MongoSortVO;

@Component
@Path("/role")
public class RoleResource {

	@Autowired
	private RoleService roleService;

	@POST
	@Path("/createRole")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public boolean createRole(@RequestBody Role role) {
		try {
			return roleService.createRole(role);
		} catch (RAException e) {
			throw new RAException(e.getMessage());
		}
	}

	@Path("/findRoleByName/{name}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public RoleMapper findOneByPrimaryId(@Context ServletContext context, @PathParam("name") String roleName)
			throws JsonGenerationException, JsonMappingException, IOException {
		try {
			Map<String, Object> condition = new HashMap<String, Object>();
			condition.put("_id", roleName);

			Role role = roleService.findOneByCondition(condition);

			return convertDomainToMappar(role);
		} catch (RAException e) {
			throw new RAException(e.getMessage());
		}
	}

	@Path("/findRolesByRoleMapping/{id}/{page}/{size}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public List<RoleMapper> findRolesByRoleMapping(@Context ServletContext context, @PathParam("id") String id,
			@PathParam("page") int page, @PathParam("size") int size)
			throws JsonGenerationException, JsonMappingException, IOException {
		try {
			Map<String, Object> condition = new HashMap<String, Object>();
			condition.put("user_id", new ObjectId(id));
			MongoSortVO sort = new MongoSortVO();
			sort.setPrimaryKey("_id");
			sort.setPrimaryOrderBy(MongoOrderByEnum.DESC);
			List<Role> role = roleService.getByMapObjects(sort, size, page, condition);

			return convertDomainToMapperList(role);
		} catch (RAException e) {
			throw new RAException(e.getMessage());
		}
	}

	private List<RoleMapper> convertDomainToMapperList(List<Role> roleMappers) {
		try {
			List<RoleMapper> list = new ArrayList<RoleMapper>();
			for (Role roleMapping : roleMappers) {
				RoleMapper roleMapper = new RoleMapper();
				BeanUtils.copyProperties(roleMapping, roleMapper);
				roleMapper.set_id(roleMapping.get_id().toString());

				list.add(roleMapper);
			}
			return list;
		} catch (RAException e) {
			throw new RAException(e.getMessage());
		}
	}

	private RoleMapper convertDomainToMappar(Role role) {
		try {
			RoleMapper roleMapper = new RoleMapper();
			BeanUtils.copyProperties(role, roleMapper);
			roleMapper.set_id(role.get_id().toString());

			return roleMapper;
		} catch (RAException e) {
			throw new RAException(e.getMessage());
		}
	}
}
