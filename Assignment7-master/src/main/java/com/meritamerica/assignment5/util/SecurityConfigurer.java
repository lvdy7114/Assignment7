package com.meritamerica.assignment5.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.meritamerica.assignment5.filter.*;
import com.meritamerica.assignment5.util.*;

@EnableWebSecurity
public class SecurityConfigurer extends WebSecurityConfigurerAdapter {

		
	@Autowired
	private MyUserDetailsService myUserDetailsService;
	
	@Autowired
	JwtRequestFilter jwtRequestFilter;
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception{
		auth.userDetailsService(myUserDetailsService);				
				
	}
	
//	antMatchers("/h2-console/**").permitAll().
	@Override
	public void configure(HttpSecurity http) throws Exception {
		http.csrf().disable().authorizeRequests()
		.antMatchers("/authenticate").permitAll()
		.anyRequest().authenticated()

		//here after its all authenticated(Jwt needed)
		.antMatchers("/authenticate/CreateUser").hasAuthority("ADMIN")
		.antMatchers("/AccountHolders/**").hasAuthority("ADMIN")
		.antMatchers("/Me/**").hasAuthority("ACCOUNTHOLDER")
		.antMatchers(HttpMethod.POST, "/CDOfferings").hasAuthority("ADMIN" )
		.antMatchers(HttpMethod.GET,"/CDOfferings").hasAnyAuthority("ADMIN","ACCOUNTHOLDER")		
		.and().sessionManagement()
		.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

	}
	
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return NoOpPasswordEncoder.getInstance();
	}


	@Override
	@Bean 
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}








}
