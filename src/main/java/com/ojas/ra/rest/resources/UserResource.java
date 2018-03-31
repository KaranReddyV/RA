package com.ojas.ra.rest.resources;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
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

import com.ojas.ra.domain.AccessToken;
import com.ojas.ra.domain.Requirement;
import com.ojas.ra.domain.Role;
import com.ojas.ra.domain.RoleMapping;
import com.ojas.ra.domain.UserAccount;
import com.ojas.ra.exception.RAException;
import com.ojas.ra.mapper.RequirementMapper;
import com.ojas.ra.mapper.RoleMapper;
import com.ojas.ra.service.RoleMappingService;
import com.ojas.ra.service.RoleService;
import com.ojas.ra.service.UserService;
import com.ojas.ra.transfer.UserTransfer;
import com.ojas.ra.util.MongoAdvancedQuery;
import com.ojas.ra.util.MongoEqualQuery;
import com.ojas.ra.util.MongoOrderByEnum;
import com.ojas.ra.util.MongoSortVO;

/**
 * 
 * @author msunil
 *
 */
@Component
@Path("/user")
public class UserResource {
	@Autowired
	private UserService userService;
	@Autowired
	private RoleMappingService roleMappingService;

	@Autowired
	private RoleService roleService;

	/**
	 * Retrieves the currently logged in user.
	 *
	 * @return A transfer containing the username and the roles.
	 */
	@Path("/userDetails/{userName}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public UserTransfer getUser(@Context ServletContext context, @PathParam("userName") String userName) {
		UserTransfer userTransfer = null;

		try {
			UserAccount userDetails = userService.loadUserByUsername(userName.trim());
			List<RoleMapping> mapings = roleMappingService.findOneRoleMappingByUserId(userDetails.get_id());
			List<Role> roles = new ArrayList<Role>();
			// userTransfer = new UserTransfer(userDetails.getUsername(),
			// this.createRoleMap(roles));
			userTransfer = new UserTransfer();
			BeanUtils.copyProperties(userDetails, userTransfer);
			userTransfer.setRoles(this.createRoleMap(roles));
			for (RoleMapping map : mapings) {
				Map<String, Object> condition = new HashMap<String, Object>();
				condition.put("_id", map.getRole_id());
				Role role = roleService.findOneByCondition(condition);
				roles.add(role);
			}

			if (null != userDetails.getRegistrationId()) {
				userTransfer.setRegistrationId(userDetails.getRegistrationId().toString());
			}
			userTransfer.setRegistrationType(userDetails.getRegistrationType());
			return userTransfer;
		} catch (RAException e) {
			throw new RAException(e.getMessage());
		}
	}

	/**
	 * Authenticates a user and creates an access token.
	 *
	 * @param username
	 *            The name of the user.
	 * @param password
	 *            The password of the user.
	 * @return The generated access token.
	 */
	@Path("/authenticate")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public AccessToken authenticate(@FormParam("username") String username, @FormParam("password") String password) {
		try {
			UserAccount acc = userService.loadUserByUsername(username);

			return this.userService.createAccessToken(acc);
		} catch (RAException e) {
			throw new RAException(e.getMessage());
		}
	}

	private Map<String, Boolean> createRoleMap(List<Role> userRole) {
		try {
			Map<String, Boolean> roles = new HashMap<String, Boolean>();

			for (Role authority : userRole) {
				roles.put(authority.getRoleName(), Boolean.TRUE);
			}
			return roles;
		} catch (RAException e) {
			throw new RAException(e.getMessage());
		}
	}

	@POST
	@Path("/saveUser")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public boolean createUser(@RequestBody UserTransfer userTransfer) {
		boolean b = true;
		if (null == userService.loadUserByUsername(userTransfer.getUsername())) {
			try {
				UserAccount user = new UserAccount();
				BeanUtils.copyProperties(userTransfer, user);
				user.setRegistrationId(new ObjectId(userTransfer.getRegistrationId()));
				userService.create(user);
				if (null != userTransfer.getRoleMapper() && !userTransfer.getRoleMapper().isEmpty()) {
					UserAccount userAccount = userService.loadUserByUsername(user.getUsername());
					for (RoleMapper map : userTransfer.getRoleMapper()) {
						RoleMapping mapping = new RoleMapping();
						mapping.setUser_id(userAccount.get_id());
						mapping.setRole_id(new ObjectId(map.get_id()));
						roleMappingService.createRoleMapping(mapping);
					}
				}

			} catch (RAException e) {
				throw new RAException(e.getMessage()); 
			}
		} else {
			throw new RAException("UserName already exists...");
		}
		return b;
	}

	@PUT
	@Path("/updateUser/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public boolean updateUser(@RequestBody UserTransfer userTransfer, @PathParam("id") ObjectId id) {
		try {
			UserAccount userAccount = new UserAccount();
			BeanUtils.copyProperties(userTransfer, userAccount);
			userAccount.set_id(id);
			userAccount.setRegistrationId(new ObjectId(userTransfer.getRegistrationId()));
			userService.save(userAccount);
			Map<String, Object> condition = new HashMap<String, Object>();
			condition.put("user_id", id);
			roleMappingService.removeByCondition(condition);
			for (RoleMapper map : userTransfer.getRoleMapper()) {
				RoleMapping mapping = new RoleMapping();
				mapping.setUser_id(userAccount.get_id());
				mapping.setRole_id(new ObjectId(map.get_id()));
				roleMappingService.createRoleMapping(mapping);
			}

			return true;

		} catch (RAException e) {
			throw new RAException(e.getMessage());
		}

	}

	@GET
	@Path("/getOneByPrimaryKey/{value}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public UserTransfer findOneByPrimaryId(@Context ServletContext context, @PathParam("value") ObjectId value)
			throws JsonGenerationException, JsonMappingException, IOException {
		try {
			Map<String, Object> condition = new HashMap<String, Object>();
			condition.put("_id", value);
			UserAccount user = userService.findOneByCondition(condition);
			List<RoleMapping> mapping = roleMappingService.findOneRoleMappingByUserId(user.get_id());
			UserTransfer userTransfer = convertDomainToMapper(user);
			List<RoleMapper> list = new ArrayList<RoleMapper>();
			for (RoleMapping mpps : mapping) {
				RoleMapper mapper = new RoleMapper();
				Map<String, Object> rolecondition = new HashMap<String, Object>();
				rolecondition.put("_id", mpps.getRole_id());
				Role role = roleService.findOneByCondition(rolecondition);
				mapper.set_id(role.get_id().toString());
				mapper.setRoleName(role.getRoleName());
				list.add(mapper);
			}
			userTransfer.setRoleMapper(list);
			System.out.println(user.getFirstname());
			return userTransfer;
		} catch (RAException e) {
			throw new RAException(e.getMessage());
		}
	}

	@GET
	@Path("/findAllByRegistrationId/{regId}/{pageNo}/{pageSize}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public List<UserTransfer> findAllByRegistrationId(@Context ServletContext context,
			@PathParam("regId") String objectId, @PathParam("pageNo") int pageNo, @PathParam("pageSize") int pageSize) {
		try {
			MongoEqualQuery equal = new MongoEqualQuery();
			equal.setEqualto(new ObjectId(objectId));
			Map<String, MongoAdvancedQuery> userMappingcondition = new HashMap<String, MongoAdvancedQuery>();
			userMappingcondition.put(objectId, equal);

			MongoSortVO sort = new MongoSortVO();
			sort.setPrimaryKey("registrationId");
			sort.setPrimaryOrderBy(MongoOrderByEnum.DESC);

			List<UserAccount> listUser = userService.findByRegistrationId(userMappingcondition, sort, pageNo, pageSize);

			return convertDomainToMapperList(listUser);
		} catch (RAException e) {
			throw new RAException(e.getMessage());
		}

	}

	@Path("/listUsers/{pageNo}/{pageSize}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public List<UserTransfer> findAllByCondition(@Context ServletContext context, @PathParam("pageNo") int pageNo,
			@PathParam("pageSize") int pageSize) {
		try {
			MongoSortVO sort = new MongoSortVO();
			sort.setPrimaryKey("_id");
			sort.setPrimaryOrderBy(MongoOrderByEnum.DESC);
			List<UserAccount> list = userService.getAllObjects(sort, pageNo, pageSize);
			return convertDomainToMapperList(list);
		} catch (RAException e) {
			throw new RAException(e.getMessage());
		}
	}

	@Path("/deleteUserById/{id}")
	@DELETE
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public boolean deleteUserById(@Context ServletContext context, @PathParam("id") ObjectId id) {
		try {
			return userService.delete(id);
		} catch (RAException e) {
			throw new RAException(e.getMessage());
		}
	}

	@Path("/getAllRoles/{pageNo}/{pageSize}")
	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public List<RoleMapper> getAllRoles(@Context ServletContext context, @PathParam("pageNo") int pageNo,
			@PathParam("pageSize") int pageSize) {

		try {
			MongoSortVO sort = new MongoSortVO();
			sort.setPrimaryKey("_id");
			sort.setPrimaryOrderBy(MongoOrderByEnum.DESC);
			List<Role> list = userService.getAllRoles(sort, pageNo, pageSize);
			return convertToListRoleMapper(list);
		} catch (RAException e) {
			throw new RAException(e.getMessage());
		}

	}
	
	
	
	
	@Path("/findOneAllByCondition/{nameOftheProperty}/{valueOftheProperty}/{pageNo}/{pageSize}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public List<UserTransfer> findOneAllByCondition(@Context ServletContext context,
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
			List<UserAccount> requirementlist = userService.advancedFindByCondition(requirementMappingcondition,
					sort, pageNo, pageSize);

			return convertDomainToMapperList(requirementlist);
		} catch (RAException e) {
			throw new RAException(e.getMessage());
		}
	}
	
	

	private List<UserTransfer> convertDomainToMapperList(List<UserAccount> userList) {
		try {
			List<UserTransfer> transferList = new ArrayList<UserTransfer>();
			for (UserAccount user : userList) {
				UserTransfer userTransfer = new UserTransfer();
				BeanUtils.copyProperties(user, userTransfer);
				userTransfer.set_id(user.get_id().toString());
				if (null != user.getRegistrationId()) {
					userTransfer.setRegistrationId(user.getRegistrationId().toString());
				}
				transferList.add(userTransfer);
			}
			return transferList;
		} catch (RAException e) {
			throw new RAException(e.getMessage());
		}
	}

	private UserTransfer convertDomainToMapper(UserAccount user) {
		try {
			UserTransfer userTransfer = new UserTransfer();
			BeanUtils.copyProperties(user, userTransfer);
			userTransfer.setRegistrationId(user.getRegistrationId().toString());
			return userTransfer;
		} catch (RAException e) {
			throw new RAException(e.getMessage());
		}
	}

	private List<RoleMapper> convertToListRoleMapper(List<Role> roles) {
		try {
			List<RoleMapper> listMapper = new ArrayList<RoleMapper>();
			for (Role role : roles) {
				RoleMapper map = new RoleMapper();
				BeanUtils.copyProperties(role, map);
				map.set_id(role.get_id().toString());
				listMapper.add(map);
			}
			return listMapper;
		} catch (RAException e) {
			throw new RAException(e.getMessage());
		}
	}
}
