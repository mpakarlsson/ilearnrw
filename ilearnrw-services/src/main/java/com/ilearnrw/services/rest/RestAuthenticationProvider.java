package com.ilearnrw.services.rest;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class RestAuthenticationProvider implements AuthenticationProvider {
	private static Logger LOG = Logger
			.getLogger(RestAuthenticationProvider.class);

	private UserDetailsService userDetailsService;

	public RestAuthenticationProvider(UserDetailsService userDetailsService) {
		this.userDetailsService = userDetailsService;
	}

	@Override
	public Authentication authenticate(Authentication authentication)
			throws AuthenticationException {
		RestToken authToken = (RestToken) authentication;

		String principal = (String) authToken.getPrincipal();
		try {
			UserDetails userDetails = userDetailsService
					.loadUserByUsername(principal);

			LOG.debug("Found user: " + userDetails.getUsername());

			return getAuthenticatedUser(userDetails);

		} catch (UsernameNotFoundException ex) {
			throw new BadCredentialsException("user invalid");
		}

	}

	private Authentication getAuthenticatedUser(UserDetails userDetails) {
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		authorities.add(new SimpleGrantedAuthority("ROLE_API_USER"));
		authorities.addAll(userDetails.getAuthorities());

		return new RestToken(userDetails.getUsername(),
				userDetails.getPassword(), authorities);
	}

	@Override
	/*
	 * Determines if this class can support the token provided by the filter.
	 */
	public boolean supports(Class<?> authentication) {
		// return RestToken.class.equals(authentication);
		return RestToken.class.equals(authentication);
	}
}
