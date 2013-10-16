package com.ilearnrw.services.rest;

import java.io.IOException;
import java.security.SecureRandom;
import java.util.Map;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.token.KeyBasedPersistenceTokenService;
import org.springframework.security.core.token.SecureRandomFactoryBean;
import org.springframework.security.core.token.Token;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;


public class CustomRestSecurityFilter extends GenericFilterBean {
	private static Logger LOG = Logger
			.getLogger(CustomRestSecurityFilter.class);

	private AuthenticationManager authenticationManager;
	private AuthenticationEntryPoint authenticationEntryPoint;

	private KeyBasedPersistenceTokenService tokenService;

	public CustomRestSecurityFilter() {
		super();
	}

	// this is not used
	public CustomRestSecurityFilter(AuthenticationManager authenticationManager) {
		this(authenticationManager, new BasicAuthenticationEntryPoint(), null);
		((BasicAuthenticationEntryPoint) authenticationEntryPoint)
				.setRealmName("");
	}

	public CustomRestSecurityFilter(
			AuthenticationManager authenticationManager,
			AuthenticationEntryPoint authenticationEntryPoint,
			KeyBasedPersistenceTokenService tokenService) {
		this.authenticationManager = authenticationManager;
		this.authenticationEntryPoint = authenticationEntryPoint;
		this.tokenService = tokenService;
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) resp;

		LOG.debug("doing Filter in CustomRestSecurityFilter");
		
		// if we passed through the BasicAuthenticationFilter with no authentication, we should require one
		if (SecurityContextHolder.getContext().getAuthentication() == null) {
			LOG.debug("no auth set");
			authenticationEntryPoint.commence(request, response, new AuthenticationCredentialsNotFoundException("you should provide basic auth"));
			return;
		}
		
		Map<String, String[]> parms = request.getParameterMap();
		if (parms.containsKey("token")) {
			String tokenKey = parms.get("token")[0]; // grab the first "token"
													// parameter
	        Token receivedToken = tokenService.verifyToken(tokenKey);
		
			if (TokenUtils.isExpired(receivedToken)) {
				throw new BadCredentialsException("timeout");
			}

	        RestToken token = RestToken.fromToken(receivedToken);
	        
			// set the authentication into the SecurityContext
			SecurityContextHolder.getContext().setAuthentication(
					authenticationManager.authenticate(token));
		}
		// continue thru the filter chain
		chain.doFilter(request, response);

	}

}
