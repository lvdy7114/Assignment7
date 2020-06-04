package com.meritamerica.assignment5.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.meritamerica.assignment5.models.AuthenticationRequest;
import com.meritamerica.assignment5.models.AuthenticationResponse;
import com.meritamerica.assignment5.models.Users;
import com.meritamerica.assignment5.repositories.UsersRepository;



@RestController
//@RequestMapping("/authenticate")
public class AuthenticationController {
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private MyUserDetailsService userDetailsService;
	
	@Autowired
	private JwtUtil jwtTokenUtil;

	@Autowired
	private UsersRepository usersRepository;


	//@PostMapping("/authenticate/createUser")
	//Public @ResposeBody User CreatesaUser()

	
	@RequestMapping(value= "/authenticate", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) throws Exception {
		try {
		authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(authenticationRequest.getUserName(), authenticationRequest.getPassword())
				);
		}catch (BadCredentialsException e) {
			throw new Exception("Incorrect username or password", e);
		}
	
	final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUserName());
	
	final String jwt = jwtTokenUtil.generateToken(userDetails);
			//generateToken(userDetails);
	
	return ResponseEntity.ok(new AuthenticationResponse(jwt));
	
	
	}

	@PostMapping(value = "/authenticate/CreateUser")
	public ResponseEntity<?> createUser(@RequestBody Users users){
		usersRepository.save(users);
		return ResponseEntity.ok(users);
	}









}
