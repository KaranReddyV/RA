package com.ojas.ra.rest.resources;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ojas.ra.domain.MailClass;
import com.ojas.ra.util.SendMail;

@Component
@Path("/userMail")
public class MailResource {

	@Autowired
	private SendMail sendMail;

	@Path("/mail")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public @ResponseBody ResponseEntity<MailClass> sendingMail(@RequestBody MailClass mailClass) {
		sendMail.sendMail(mailClass.getTo(), mailClass.getSubject(), mailClass.getMsg());
		return new ResponseEntity<MailClass>(HttpStatus.OK);
	}

}
