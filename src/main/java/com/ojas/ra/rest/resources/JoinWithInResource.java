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

import com.ojas.ra.domain.JoinWithIn;
import com.ojas.ra.exception.RAException;
import com.ojas.ra.mapper.JoinWithInMapper;
import com.ojas.ra.service.JoinWithInService;
import com.ojas.ra.util.MongoOrderByEnum;
import com.ojas.ra.util.MongoSortVO;

@Component
@Path("/joinWithIn")
public class JoinWithInResource {

	@Autowired
	private JoinWithInService joinWithInService;

	@Path("/createjoinWithIn")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)

	public boolean createjoinWithIn(@RequestBody JoinWithInMapper joinWithInMapper) {

		int count = 0;
		try {

			MongoSortVO sort = new MongoSortVO();
			sort.setPrimaryKey("_id");
			sort.setPrimaryOrderBy(MongoOrderByEnum.DESC);

			for (JoinWithIn joinWithIn : joinWithInService.getAllJoinWithIn(sort)) {
				if (joinWithIn.getJoinWithIn().equalsIgnoreCase(joinWithInMapper.getJoinWithIn()) == false) {

					count++;
				}

			}
			if (count == joinWithInService.getAllJoinWithIn(sort).size()) {

				JoinWithIn joinWithIn = new JoinWithIn();

				BeanUtils.copyProperties(joinWithInMapper, joinWithIn);

				return joinWithInService.createJoinWithIn(joinWithIn);
			} else {
				return false;
			}

		} catch (RAException e) {
			throw new RAException(e.getMessage());
		}

	}

	@Path("/updatejoinWithIn/{id}")
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public boolean updatejoinWithIn(@RequestBody JoinWithInMapper joinWithInMapper, @PathParam("id") ObjectId id) {

		try {
			JoinWithIn joinWithIn = new JoinWithIn();
			BeanUtils.copyProperties(joinWithInMapper, joinWithIn);
			joinWithIn.set_id(id);
			return joinWithInService.updateJoinWithIn(joinWithIn);
		} catch (RAException e) {
			throw new RAException(e.getMessage());
		}
	}

	@Path("/listJoinWithIn/{pageNo}/{pageSize}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public List<JoinWithInMapper> getAlljoinWithIn(@Context ServletContext context, @PathParam("pageNo") int pageNo,
			@PathParam("pageSize") int pageSize) {

		try {
			MongoSortVO sort = new MongoSortVO();
			sort.setPrimaryKey("_id");
			sort.setPrimaryOrderBy(MongoOrderByEnum.DESC);
			List<JoinWithIn> list = joinWithInService.getAllJoinWithIn(sort, pageNo, pageSize);
			return convertDomainToMapperList(list);
		} catch (RAException e) {
			throw new RAException(e.getMessage());
		}
	}

	public List<JoinWithInMapper> convertDomainToMapperList(List<JoinWithIn> joinWithIns) {
		try {
			List<JoinWithInMapper> list = new ArrayList<JoinWithInMapper>();
			for (JoinWithIn joinWithIn : joinWithIns) {
				JoinWithInMapper joinWithInMapper = new JoinWithInMapper();
				BeanUtils.copyProperties(joinWithIn, joinWithInMapper);
				joinWithInMapper.set_id(joinWithIn.get_id().toString());

				list.add(joinWithInMapper);
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
	public JoinWithInMapper findOneByPrimaryId(@Context ServletContext context, @PathParam("value") ObjectId value)
			throws JsonGenerationException, JsonMappingException, IOException {
		try {
			Map<String, Object> condition = new HashMap<String, Object>();
			condition.put("_id", value);

			JoinWithIn joinWithIn = joinWithInService.getOneByCondition(condition);

			return convertDomainToMappar(joinWithIn);
		} catch (RAException e) {
			throw new RAException(e.getMessage());
		}
	}

	private JoinWithInMapper convertDomainToMappar(JoinWithIn JoinWithIn) {
		try {
			JoinWithInMapper joinWithInMapper = new JoinWithInMapper();
			BeanUtils.copyProperties(JoinWithIn, joinWithInMapper);
			joinWithInMapper.set_id(JoinWithIn.get_id().toString());

			return joinWithInMapper;
		} catch (RAException e) {
			throw new RAException(e.getMessage());
		}
	}
}
