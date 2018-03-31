package com.ojas.ra.rest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import com.ojas.ra.domain.Role;
import com.ojas.ra.domain.RoleMapping;
import com.ojas.ra.domain.UserAccount;
import com.ojas.ra.service.RoleMappingService;
import com.ojas.ra.service.RoleService;
import com.ojas.ra.service.UserService;

/**
 * 
 * @author msunil
 *
 */
public class AuthenticationTokenProcessingFilter extends GenericFilterBean {

	@Autowired
	private UserService userService;

	@Autowired
	private RoleMappingService roleMappingService;

	@Autowired
	private RoleService roleService;

	public AuthenticationTokenProcessingFilter(UserService userService) {

		this.userService = userService;
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest httpRequest = this.getAsHttpRequest(request);
		System.out.println("request URL : " + httpRequest.getRequestURL().toString());
		if (!httpRequest.getRequestURL().toString().contains("/authenticate")) {
			String accessToken = this.extractAccessTokenFromRequest(httpRequest);
			if (null != accessToken) {
				UserAccount token = this.userService.getUserByToken(accessToken);

				List<RoleMapping> mappingList = roleMappingService.findOneRoleMappingByUserId(token.get_id());
				List<Role> roles = new ArrayList<Role>();
				for (RoleMapping mapping : mappingList) {

					Map<String, Object> rolecondition = new HashMap<String, Object>();
					rolecondition.put("_id", mapping.getRole_id());
					Role findPojo = roleService.findOneByCondition(rolecondition);
					roles.add(findPojo);

				}
				if (null != token) {

					UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(token,
							roles);
					SecurityContextHolder.getContext().setAuthentication(authentication);
				}
			}

			chain.doFilter(request, response);
		}
	}

	private HttpServletRequest getAsHttpRequest(ServletRequest request) {
		if (!(request instanceof HttpServletRequest)) {
			throw new RuntimeException("Expecting an HTTP request");
		}

		return (HttpServletRequest) request;
	}

	private String extractAccessTokenFromRequest(HttpServletRequest httpRequest) {
		/* Get token from header */
		String authToken = httpRequest.getHeader("X-Access-Token");

		/* If token not found get it from request parameter */
		if (authToken == null) {
			authToken = httpRequest.getParameter("token");
		}

		return authToken;
	}
}
