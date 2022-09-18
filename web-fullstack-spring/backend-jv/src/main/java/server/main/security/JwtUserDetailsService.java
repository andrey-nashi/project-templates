package server.main.security;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import server.main.user.ApiUserService;
import server.main.user.ApiUser;

@Service
public class JwtUserDetailsService implements UserDetailsService {

	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private ApiUserService apiUserService;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		ApiUser user = apiUserService.findByUsername(username);
		System.out.println(username);
		if (user == null) {
			System.out.println("NOT FOUND");
			throw new UsernameNotFoundException("User not found with username: " + username);
		}
		return new org.springframework.security.core.userdetails.User(user.getUsername(), passwordEncoder.encode(user.getPassword()),
				new ArrayList<>());
	}
	

}