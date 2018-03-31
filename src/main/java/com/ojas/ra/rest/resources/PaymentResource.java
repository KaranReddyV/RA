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

import com.ojas.ra.domain.Payment;
import com.ojas.ra.exception.RAException;
import com.ojas.ra.mapper.PaymentMapper;
import com.ojas.ra.service.PaymentService;
import com.ojas.ra.util.MongoOrderByEnum;
import com.ojas.ra.util.MongoSortVO;

@Component
@Path("/payment")
public class PaymentResource {

	@Autowired
	PaymentService paymentService;

	@Path("/createPayment")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public boolean createPayment(@RequestBody PaymentMapper paymentMapper) {
		Payment payment = new Payment();
		BeanUtils.copyProperties(paymentMapper, payment);
		payment.setRegistrationId(new ObjectId(paymentMapper.getRegistrationId()));
		return paymentService.createPayment(payment);
	}

	@Path("/savePayment/{id}")
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public boolean savePayment(@RequestBody PaymentMapper paymentMapper, @PathParam("id") ObjectId id) {
		Payment payment = new Payment();
		BeanUtils.copyProperties(paymentMapper, payment);
		payment.set_id(id);
		payment.setRegistrationId(new ObjectId(paymentMapper.getRegistrationId()));

		return paymentService.savePayment(payment);
	}

	@Path("/listPayments/{pageNo}/{pageSize}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public List<PaymentMapper> findAllByCondition(@Context ServletContext context, @PathParam("pageNo") int pageNo,
			@PathParam("pageSize") int pageSize) {
		try {
			MongoSortVO sort = new MongoSortVO();
			sort.setPrimaryKey("_id");
			sort.setPrimaryOrderBy(MongoOrderByEnum.DESC);
			List<Payment> list = paymentService.getAllObjects(sort, pageNo, pageSize);
			return convertDomainToMapperList(list);
		} catch (RAException e) {
			throw new RAException(e.getMessage());
		}
	}

	@Path("/findPaymentByCondition/{nameOftheProperty}/{valueOftheProperty}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public PaymentMapper findPaymentByCondition(@Context ServletContext context,
			@PathParam("nameOftheProperty") String nameOftheProperty,
			@PathParam("valueOftheProperty") String valueOftheProperty) {
		try {
			Map<String, Object> condition = new HashMap<String, Object>();
			condition.put(nameOftheProperty, valueOftheProperty);
			Payment payment = paymentService.findOneByCondition(condition);
			return convertDomainToMappar(payment);
		} catch (RAException e) {
			throw new RAException(e.getMessage());
		}
	}

	@Path("/findPaymentByPrimaryId/{value}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public PaymentMapper findPaymentByPrimaryId(@Context ServletContext context, @PathParam("value") ObjectId value)
			throws JsonGenerationException, JsonMappingException, IOException {
		try {
			Map<String, Object> condition = new HashMap<String, Object>();
			condition.put("_id", value);

			Payment payments = paymentService.findOneByCondition(condition);

			return convertDomainToMappar(payments);
		} catch (RAException e) {
			throw new RAException(e.getMessage());
		}
	}

	@PUT
	@Path("/inactiveOrActive/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public boolean inactiveOrActive(@RequestBody PaymentMapper paymentMapper, @PathParam("id") ObjectId id) {
		try {
			Payment payment = new Payment();
			BeanUtils.copyProperties(paymentMapper, payment);
			payment.set_id(id);
			Map<String, Object> condition = new HashMap<String, Object>();
			condition.put("_id", id);
			Map<String, Object> target = new HashMap<String, Object>();
			target.put("status", payment.getStatus());

			return paymentService.updateMapByCondition(condition, target);
		} catch (RAException e) {
			throw new RAException(e.getMessage());
		}
	}

	private List<PaymentMapper> convertDomainToMapperList(List<Payment> payments) {
		try {
			List<PaymentMapper> list = new ArrayList<PaymentMapper>();
			for (Payment payment : payments) {
				PaymentMapper paymentMapper = new PaymentMapper();
				BeanUtils.copyProperties(payment, paymentMapper);
				paymentMapper.set_id(payment.get_id().toString());
				list.add(paymentMapper);
			}
			return list;
		} catch (RAException e) {
			throw new RAException(e.getMessage());
		}
	}

	private PaymentMapper convertDomainToMappar(Payment payment) {
		try {
			PaymentMapper paymentMapper = new PaymentMapper();
			BeanUtils.copyProperties(payment, paymentMapper);
			paymentMapper.set_id(payment.get_id().toString());

			return paymentMapper;
		} catch (RAException e) {
			throw new RAException(e.getMessage());
		}
	}
}
