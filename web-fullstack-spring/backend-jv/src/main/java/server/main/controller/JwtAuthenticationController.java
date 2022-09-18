package server.main.security;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import server.main.security.JwtUserDetailsService;
import server.main.security.JwtTokenUtil;
import server.main.security.JwtRequest;
import server.main.security.JwtResponse;
import server.main.user.ApiUser;

//---------------------------------------------------------------
//---- This is a controller class that provides mappings for
//---- performing authentication of an existing user or
//---- allowing to register a new user.
//---- MAP: /authenticate - user authentication api
//---- MAP: /register - user registration api
//---------------------------------------------------------------

@RestController
@CrossOrigin
public class JwtAuthenticationController {

	//-----------------------------------------------
	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	private JwtUserDetailsService userDetailsService;
	//-----------------------------------------------

	/**
	 * MAPPING: authenticate a user using the passed variables.
	 * @param authenticationRequest request that contains user name and encrypted password
	 * @return the generated JWT token
	 * @throws Exception if credentials are not accepted
	 */
	@RequestMapping(value = "/authenticate", method = RequestMethod.POST)
	public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {

		//---- 1. Check user credentials, throws exception is credentials are not accepted
		authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());

		//---- 2. Obtain user details and generate JWT token
		final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
		final String token = jwtTokenUtil.generateToken(userDetails);

		//---- Return: JWT token
		return ResponseEntity.ok(new JwtResponse(token));
	}

	/**
	 * MAPPING: register a new user, by adding credentials to the database
	 * @param user credentials such as user name and password
	 * @return OK, or FAIL
	 * @throws Exception
	 */
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public ResponseEntity<?> saveUser(@RequestBody ApiUser user) throws Exception {
		//return ResponseEntity.ok(userDetailsService.save(user));
		return ResponseEntity.ok("OK");
	}

	//-----------------------------------------------

	/**
	 * A helper method for authenticating by checking credentials stored in the database.
	 * @param username user (without encryption)
	 * @param password encrypted password
	 * @throws Exception
	 */
	private void authenticate(String username, String password) throws Exception {
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		}
		catch (DisabledException e) {throw new Exception("USER_DISABLED", e);}
		catch (BadCredentialsException e) {throw new Exception("INVALID_CREDENTIALS", e);}
	}
}