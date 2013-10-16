package com.ilearnrw.services.rest;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.token.KeyBasedPersistenceTokenService;
import org.springframework.security.core.token.Token;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
public class AuthController {

	private static Logger LOG = Logger
			.getLogger(AuthController.class);

	@Autowired
	private UserDetailsService usersService;
	
	@Autowired
	private KeyBasedPersistenceTokenService tokenService;
	
	@RequestMapping(value = "/test", method = RequestMethod.GET)
	public @ResponseBody
	String test() {
		return "test back";
	}

	@RequestMapping(value = "/user/newtokens", method = RequestMethod.GET)
	public @ResponseBody
	Tokens newTokens(
			@RequestParam(value = "refresh", required = true) String refreshToken,
			HttpServletResponse httpResponse) {

		Token receivedToken = tokenService.verifyToken(refreshToken);

		if (TokenUtils.isExpired(receivedToken)) {
			throw new BadCredentialsException("timeout");
		}
		
		ObjectMapper mapper = new ObjectMapper();
		try {
			RefreshTokenData upp = mapper.readValue(receivedToken.getExtendedInformation(), RefreshTokenData.class);
			return authenticateUser(upp.getUserName(), upp.getPassword(), httpResponse);
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		throw new BadCredentialsException("invalid refresh token");
	}


	@RequestMapping(value = "/user/auth", method = RequestMethod.GET)
	public @ResponseBody
	Tokens authenticateUser(
			@RequestParam(value = "username", required = true) String username,
			@RequestParam(value = "pass", required = true) String pass,
			HttpServletResponse httpResponse) {

		RefreshTokenData upp = new RefreshTokenData(username, pass);
		ObjectMapper mapper = new ObjectMapper();

		try {
			
			UserDetails userDetails = usersService.loadUserByUsername(username);

			if (userDetails.getPassword().compareTo(pass) != 0)
			{
				httpResponse.sendError(HttpServletResponse.SC_FORBIDDEN);
				return null;
			}
			
			String serialized = mapper.writeValueAsString(upp);

			Token refreshToken = tokenService.allocateToken(serialized);
			Token authToken = tokenService.allocateToken(username);
			return new Tokens(authToken.getKey(), refreshToken.getKey());

		} catch (JsonProcessingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
	}

}