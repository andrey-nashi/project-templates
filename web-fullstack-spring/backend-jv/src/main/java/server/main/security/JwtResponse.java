package server.main.security;

import java.io.Serializable;

//---------------------------------------------------------------
//---- This class implements response container that is returned
//---- by the authentication procedure. In its simplest case
//---- it is just a JWT token as a string.
//---------------------------------------------------------------
public class JwtResponse implements Serializable {

	private static final long serialVersionUID = -8091879091924046844L;
	private final String jwttoken;

	public JwtResponse(String jwttoken) {
		this.jwttoken = jwttoken;
	}

	public String getToken() {
		return this.jwttoken;
	}
}