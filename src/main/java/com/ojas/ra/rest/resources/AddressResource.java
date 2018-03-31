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

import com.ojas.ra.domain.Address;
import com.ojas.ra.exception.RAException;
import com.ojas.ra.mapper.AddressMapper;
import com.ojas.ra.service.AddressService;
import com.ojas.ra.util.MongoAdvancedQuery;
import com.ojas.ra.util.MongoEqualQuery;
import com.ojas.ra.util.MongoOrderByEnum;
import com.ojas.ra.util.MongoSortVO;

@Component
@Path("/address")
public class AddressResource {

	@Autowired
	private AddressService addressService;

	@POST
	@Path("/createAddress")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public boolean createAddress(@RequestBody AddressMapper addressMapper) {
		try {
			Address address = new Address();
			BeanUtils.copyProperties(addressMapper, address);
			address.setRegistrationId(new ObjectId(addressMapper.getRegistrationId()));
			return addressService.createAddress(address);
		} catch (RAException e) {
			throw new RAException(e.getMessage());
		}
	}

	@PUT
	@Path("/saveAddress/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public boolean saveAddress(@RequestBody AddressMapper addressMapper, @PathParam("id") ObjectId id) {
		try {
			Address address = new Address();
			BeanUtils.copyProperties(addressMapper, address);
			address.set_id(id);
			return addressService.saveAddress(address);
		} catch (RAException e) {
			throw new RAException(e.getMessage());
		}
	}

	@DELETE
	@Path("/deleteAddress/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public boolean deleteAddressById(@Context ServletContext context, @PathParam("id") ObjectId id) {
		try {
			return addressService.deleteAddress(id);
		} catch (RAException e) {
			throw new RAException(e.getMessage());
		}
	}

	@Path("/listAddresses/{pageNo}/{pageSize}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public List<AddressMapper> findAllByCondition(@Context ServletContext context, @PathParam("pageNo") int pageNo,
			@PathParam("pageSize") int pageSize) {
		try {
			MongoSortVO sort = new MongoSortVO();
			sort.setPrimaryKey("_id");
			sort.setPrimaryOrderBy(MongoOrderByEnum.DESC);
			List<Address> list = addressService.getAllObjects(sort, pageNo, pageSize);
			return convertDomainToMapperList(list);
		} catch (RAException e) {
			throw new RAException(e.getMessage());
		}
	}

	@Path("/removeByPrimaryId/{id}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public boolean removeByPrimaryId(@Context ServletContext context, @PathParam("id") String id) {
		try {
			return addressService.removeByPrimaryId(id);
		} catch (RAException e) {
			throw new RAException(e.getMessage());
		}
	}

	@Path("/findOneByCondition/{nameOftheProperty}/{valueOftheProperty}}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public AddressMapper findOneByCondition(@Context ServletContext context,
			@PathParam("nameOftheProperty") String nameOftheProperty,
			@PathParam("valueOftheProperty") String valueOftheProperty) {
		try {
			Map<String, Object> condition = new HashMap<String, Object>();
			condition.put(nameOftheProperty, valueOftheProperty);
			Address address = addressService.findOneByCondition(condition);
			return convertDomainToMappar(address);
		} catch (RAException e) {
			throw new RAException(e.getMessage());
		}
	}

	@Path("/findOneByPrimaryId/{value}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public AddressMapper findOneByPrimaryId(@Context ServletContext context, @PathParam("value") ObjectId value)
			throws JsonGenerationException, JsonMappingException, IOException {
		try {
			Map<String, Object> condition = new HashMap<String, Object>();
			condition.put("_id", value);

			Address address = addressService.findOneByCondition(condition);

			return convertDomainToMappar(address);
		} catch (RAException e) {
			throw new RAException(e.getMessage());
		}
	}

	@Path("/findOneAllByCondition/{nameOftheProperty}/{valueOftheProperty}/{pageNo}/{pageSize}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public List<AddressMapper> findOneAllByCondition(@Context ServletContext context,
			@PathParam("nameOftheProperty") String nameOftheProperty,
			@PathParam("valueOftheProperty") String valueOftheProperty, @PathParam("pageNo") int pageNo,
			@PathParam("pageSize") int pageSize) {
		try {
			MongoEqualQuery equal = new MongoEqualQuery();

			equal.setEqualto(valueOftheProperty);
			Map<String, MongoAdvancedQuery> addressMappingcondition = new HashMap<String, MongoAdvancedQuery>();
			addressMappingcondition.put(nameOftheProperty, equal);
			MongoSortVO sort = new MongoSortVO();
			sort.setPrimaryKey("_id");
			sort.setPrimaryOrderBy(MongoOrderByEnum.DESC);
			List<Address> reglist = addressService.advancedFindByCondition(addressMappingcondition, sort, pageNo,
					pageSize);
			return convertDomainToMapperList(reglist);
		} catch (RAException e) {
			throw new RAException(e.getMessage());
		}
	}

	@Path("/findAddressesByRegistrationId/{registrationId}/{pageNo}/{pageSize}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public List<AddressMapper> findAddressesByregistrationId(@Context ServletContext context,
			@PathParam("registrationId") String objectId, @PathParam("pageNo") int pageNo,
			@PathParam("pageSize") int pageSize) {
		try {
			MongoEqualQuery equal = new MongoEqualQuery();

			equal.setEqualto(new ObjectId(objectId));
			Map<String, MongoAdvancedQuery> addressMappingcondition = new HashMap<String, MongoAdvancedQuery>();
			addressMappingcondition.put("registrationId", equal);

			MongoSortVO sort = new MongoSortVO();
			sort.setPrimaryKey("registrationId");
			sort.setPrimaryOrderBy(MongoOrderByEnum.DESC);

			List<Address> addresslist = addressService.advancedFindByCondition(addressMappingcondition, sort, pageNo,
					pageSize);

			return convertDomainToMapperList(addresslist);
		} catch (RAException e) {
			throw new RAException(e.getMessage());
		}
	}

	@PUT
	@Path("/inactiveOrActive/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public boolean inactiveOrActive(@RequestBody AddressMapper addressMapper, @PathParam("id") ObjectId id) {
		try {
			Address address = new Address();
			BeanUtils.copyProperties(addressMapper, address);
			address.set_id(id);
			Map<String, Object> condition = new HashMap<String, Object>();
			condition.put("_id", id);
			Map<String, Object> target = new HashMap<String, Object>();
			target.put("status", address.getStatus());

			return addressService.updateMapByCondition(condition, target);
		} catch (RAException e) {
			throw new RAException(e.getMessage());
		}
	}

	private List<AddressMapper> convertDomainToMapperList(List<Address> addresss) {
		try {
			List<AddressMapper> list = new ArrayList<AddressMapper>();
			for (Address address : addresss) {
				AddressMapper addressMapper = new AddressMapper();
				BeanUtils.copyProperties(address, addressMapper);
				addressMapper.set_id(address.get_id().toString());
				list.add(addressMapper);
			}
			return list;
		} catch (RAException e) {
			throw new RAException(e.getMessage());
		}
	}

	private AddressMapper convertDomainToMappar(Address address) {
		try {
			AddressMapper addressMapper = new AddressMapper();
			BeanUtils.copyProperties(address, addressMapper);
			addressMapper.set_id(address.get_id().toString());

			return addressMapper;

		} catch (RAException e) {
			throw new RAException(e.getMessage());
		}
	}
}
