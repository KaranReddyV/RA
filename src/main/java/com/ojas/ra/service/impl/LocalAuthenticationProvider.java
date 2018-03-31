package com.ojas.ra.service.impl;

import org.springframework.beans.factory.annotation.Autowired;

import com.ojas.ra.domain.UserAccount;
import com.ojas.ra.service.UserService;

/*
 Extend AbstractUserDetailsAuthenticationProvider when you want to
 prehandle authentication, as in throwing custom exception messages,
 checking status, etc. 
 */

public class LocalAuthenticationProvider  {
	
	
	@Autowired UserService userService;
	

	
	protected void additionalAuthenticationChecks(UserAccount userDetails)
			 {
	}

	
	public UserAccount retrieveUser(String username)
			 {
/*        String password = (String) authentication.getCredentials();
        if (!StringUtils.hasText(password)) {
        	logger.warn("Username {}: no password provided", username);
            throw new BadCredentialsException("Please enter password");
        }

        UserAccount user = userService.getByUsername(username);
        if (user == null) {
        	logger.warn("Username {} password {}: user not found", username, password);
            throw new UsernameNotFoundException("Invalid Login");
        }
        
        if (!encoder.matches(password, user.getPassword())) {
        	logger.warn("Username {} password {}: invalid password", username, password);
            throw new BadCredentialsException("Invalid Login");
        }
        
        if (!(UserAccountStatus.active.name().equals(user.getStatus()))) {
        	logger.warn("Username {}: not approved", username);
            throw new BadCredentialsException("User has not been approved");
        }
        if (!user.getEnabled()) {
        	logger.warn("Username {}: disabled", username);
            throw new BadCredentialsException("User disabled");
        }

        final List<GrantedAuthority> auths;
        if (!user.getRoles().isEmpty()) {
	    	auths = AuthorityUtils.commaSeparatedStringToAuthorityList(user.getRolesCSV());
        } else {
        	auths = AuthorityUtils.NO_AUTHORITIES;
        }

        return new User(username, password, user.getEnabled(), // enabled
                true, // account not expired
                true, // credentials not expired
                true, // account not locked
                auths);
*/	
		return null;}

}
