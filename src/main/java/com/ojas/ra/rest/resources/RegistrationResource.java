package com.ojas.ra.rest.resources;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;
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
import org.bson.types.ObjectId;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

import com.ojas.ra.domain.Registration;
import com.ojas.ra.domain.Requirement;
import com.ojas.ra.domain.Role;
import com.ojas.ra.domain.RoleMapping;
import com.ojas.ra.domain.UserAccount;
import com.ojas.ra.exception.RAException;
import com.ojas.ra.mapper.RegistrationMapper;
import com.ojas.ra.service.RegistrationService;
import com.ojas.ra.service.RoleMappingService;
import com.ojas.ra.service.RoleService;
import com.ojas.ra.service.UserService;
import com.ojas.ra.util.MongoAdvancedQuery;
import com.ojas.ra.util.MongoEqualQuery;
import com.ojas.ra.util.MongoOrderByEnum;
import com.ojas.ra.util.MongoSortVO;
import com.ojas.ra.util.SendMail;

@Component
@Path("/registration")
public class RegistrationResource {

	@Autowired
	private RegistrationService registrationService;
	@Autowired
	UserService userService;

	@Autowired
	RoleService roleService;

	@Autowired
	RoleMappingService roleMappingService;

	@Autowired
	SendMail sendMail;

	@POST
	@Path("/createRegistration")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public boolean createResgister(@RequestBody RegistrationMapper registration) {
		boolean b = false;
		Registration reg = new Registration();
		BeanUtils.copyProperties(registration, reg);
		try {

			UserAccount accounts = userService.loadUserByUsername(registration.getMailId());
			if (null == accounts) {

				reg.setRegisteredDate(new Date());
				reg.setStatus("Active");
				b = registrationService.create(reg);
				Map<String, Object> condition = new HashMap<String, Object>();
				condition.put("mailId", registration.getMailId());
				Registration regs = registrationService.findOneByCondition(condition);
				if (b) {
					UserAccount userAccount = new UserAccount();
					userAccount.setUsername(registration.getMailId());
					MessageDigest md;
					try {
						md = MessageDigest.getInstance("MD5");

						byte[] messageDigest = md.digest(registration.getMailId().getBytes());
						BigInteger number = new BigInteger(1, messageDigest);
						String hashtext = number.toString(16);
						userAccount.setPassword(hashtext);
						userAccount.setRegistrationId(regs.get_id());
						userAccount.setStatus("active");
						userAccount.setRegistrationType((registration.getRegistrationType()));
						userService.save(userAccount);

						UserAccount account = userService.loadUserByUsername(userAccount.getUsername());
						condition.put("username", account.getUsername());

						Map<String, Object> rolecondition = new HashMap<String, Object>();

						rolecondition.put("roleName", "ADMIN");
						Role role = roleService.findOneByCondition(rolecondition);
						RoleMapping mapping = new RoleMapping();
						mapping.setRole_id(role.get_id());
						mapping.setUser_id(account.get_id());
						roleMappingService.createRoleMapping(mapping);
						String subject = "Successfully Registered..";
						String msg = "your username is: " + registration.getMailId() + "and password is"
								+ userAccount.getPassword();
						sendMail.sendMail(registration.getMailId(), subject, msg);

					} catch (NoSuchAlgorithmException e) {
						throw new RAException(e.getMessage());
					}
				}
			} else {
				throw new RAException("Already Registerd with given Email Id " + accounts.getUsername());
			}
		} catch (RAException e) {
			throw new RAException(e.getMessage());
		}

		return b;
	}
	
	
	@Path("/uploadFile/{id}")
	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public boolean uploadFile(@FormDataParam("uploadFile") InputStream uploadFile, @PathParam("id") ObjectId id) throws IOException {
		try {
			Registration registration=new Registration();
			registration.set_id(id);
			registration.setUploadFile(IOUtils.toByteArray(uploadFile));
			return registrationService.save(registration);
		} catch (RAException e) {
			throw new RAException(e.getMessage());
		}
	}

	@PUT
	@Path("/saveRegistration/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public boolean saveRegister(@RequestBody RegistrationMapper registrationMapper, @PathParam("id") ObjectId id) {
		try {
			Registration registration = new Registration();
			BeanUtils.copyProperties(registrationMapper, registration);
			registration.set_id(id);
			registration.setRegisteredDate(registration.getRegisteredDate());
			return registrationService.save(registration);
		} catch (RAException e) {
			throw new RAException(e.getMessage());
		}
	}

	@DELETE
	@Path("/deleteRegistration/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public boolean deleteRegisterById(@Context ServletContext context, @PathParam("id") ObjectId id) {
		try {
			return registrationService.delete(id);
		} catch (RAException e) {
			throw new RAException(e.getMessage());
		}
	}

	@Path("/listRegistrations/{pageNo}/{pageSize}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public List<RegistrationMapper> findAllByCondition(@Context ServletContext context, @PathParam("pageNo") int pageNo,
			@PathParam("pageSize") int pageSize) {
		try {
			MongoSortVO sort = new MongoSortVO();
			sort.setPrimaryKey("_id");
			sort.setPrimaryOrderBy(MongoOrderByEnum.DESC);
			List<Registration> list = registrationService.getAllObjects(sort, pageNo, pageSize);
			return convertDomainToMapperList(list);
		} catch (RAException e) {
			throw new RAException(e.getMessage());
		}
	}
	
	@Path("/listRegistrations")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public List<RegistrationMapper> findAllByCondition(@Context ServletContext context) {
		try {
			MongoSortVO sort = new MongoSortVO();
			sort.setPrimaryKey("_id");
			sort.setPrimaryOrderBy(MongoOrderByEnum.DESC);
			List<Registration> list = registrationService.getAllObjects(sort);
			return convertDomainToMapperList(list);
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
			return registrationService.removeByPrimaryId(id);
		} catch (RAException e) {
			throw new RAException(e.getMessage());
		}
	}

	@Path("/findOneByCondition/{nameOftheProperty}/{valueOftheProperty}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public RegistrationMapper findOneByCondition(@Context ServletContext context,
			@PathParam("nameOftheProperty") String nameOftheProperty,
			@PathParam("valueOftheProperty") String valueOftheProperty) {
		try {
			Map<String, Object> condition = new HashMap<String, Object>();
			condition.put(nameOftheProperty, valueOftheProperty);
			Registration registration = registrationService.findOneByCondition(condition);
			return convertDomainToMappar(registration);
		} catch (RAException e) {
			throw new RAException(e.getMessage());
		}
	}

	@Path("/findOneByPrimaryId/{value}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public RegistrationMapper findOneByPrimaryId(@Context ServletContext context, @PathParam("value") ObjectId value)
			throws JsonGenerationException, JsonMappingException, IOException {
		try {
			Map<String, Object> condition = new HashMap<String, Object>();
			condition.put("_id", value);

			Registration reg = registrationService.findOneByCondition(condition);

			return convertDomainToMappar(reg);
		} catch (RAException e) {
			throw new RAException(e.getMessage());
		}
	}

	@Path("/findOneAllByCondition/{nameOftheProperty}/{valueOftheProperty}/{pageNo}/{pageSize}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public List<RegistrationMapper> findOneAllByCondition(@Context ServletContext context,
			@PathParam("nameOftheProperty") String nameOftheProperty,
			@PathParam("valueOftheProperty") String valueOftheProperty, @PathParam("pageNo") int pageNo,
			@PathParam("pageSize") int pageSize) {
		try {
			MongoEqualQuery equal = new MongoEqualQuery();

			equal.setEqualto(valueOftheProperty);
			Map<String, MongoAdvancedQuery> registrationMappingcondition = new HashMap<String, MongoAdvancedQuery>();
			registrationMappingcondition.put(nameOftheProperty, equal);
			MongoSortVO sort = new MongoSortVO();
			sort.setPrimaryKey("_id");
			sort.setPrimaryOrderBy(MongoOrderByEnum.DESC);
			List<Registration> reglist = registrationService.advancedFindByCondition(registrationMappingcondition, sort,
					pageNo, pageSize);
			return convertDomainToMapperList(reglist);
		} catch (RAException e) {
			throw new RAException(e.getMessage());
		}
	}

	@PUT
	@Path("/inactiveOrActive/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public boolean inactiveOrActive(@RequestBody RegistrationMapper registrationMapper, @PathParam("id") ObjectId id) {
		try {
			Registration registration = new Registration();
			BeanUtils.copyProperties(registrationMapper, registration);
			registration.set_id(id);
			registration.setRegisteredDate(registration.getRegisteredDate());
			Map<String, Object> condition = new HashMap<String, Object>();
			condition.put("_id", id);
			Map<String, Object> target = new HashMap<String, Object>();
			target.put("status", registration.getStatus());
			
			Map<String, Object> condition1 = new HashMap<String, Object>();
			condition1.put("registrationId", id);
			Map<String, Object> target1 = new HashMap<String, Object>();
			
			target1.put("status", registration.getStatus());
			userService.updateMapByCondition(condition1,target1);
			
			target.put("status", registration.getStatus());
			return registrationService.updateMapByCondition(condition,target);
		} catch (RAException e) {
			throw new RAException(e.getMessage());
		}
	}

	private List<RegistrationMapper> convertDomainToMapperList(List<Registration> registrations) {
		try {
			List<RegistrationMapper> list = new ArrayList<RegistrationMapper>();
			for (Registration registration : registrations) {
				RegistrationMapper registrationMapper = new RegistrationMapper();
				BeanUtils.copyProperties(registration, registrationMapper);
				registrationMapper.set_id(registration.get_id().toString());
				list.add(registrationMapper);
			}
			return list;
		} catch (RAException e) {
			throw new RAException(e.getMessage());
		}
	}

	private RegistrationMapper convertDomainToMappar(Registration registration) {
		try {
			RegistrationMapper registrationMapper = new RegistrationMapper();
			BeanUtils.copyProperties(registration, registrationMapper);
			registrationMapper.set_id(registration.get_id().toString());

			return registrationMapper;
		} catch (RAException e) {
			throw new RAException(e.getMessage());
		}
	}
}
