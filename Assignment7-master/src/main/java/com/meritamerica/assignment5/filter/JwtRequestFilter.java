package com.meritamerica.assignment5.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ResourceProperties.Chain;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.meritamerica.assignment5.util.JwtUtil;
import com.meritamerica.assignment5.util.MyUserDetailsService;


@Component
public class JwtRequestFilter extends OncePerRequestFilter {
	
	
	private Logger log = LoggerFactory.getLogger(this.getClass() );
	
	@Autowired
	private MyUserDetailsService userDetailsService;
	
	@Autowired
	private JwtUtil jwtUtil;
	

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		final String authorizationHeader = request.getHeader("Authorization");
		String username = null;
		String jwt= null;
		// authorazathionHeader contains (Bearer then space and jwt)
		//verification
		log.info(jwt + "super happy fun time");
		if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
			//substring(7)- leaving out "Bearer "
			jwt = authorizationHeader.substring(7);
			username = jwtUtil.extractUsername(jwt);
		}
		log.info(jwt + "super happy fun time times 2");
		//if the user name isnt null,next condition
		if(username != null && SecurityContextHolder.getContext().getAuthentication()==null) {
			UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
			
			if (jwtUtil.validateToken(jwt, userDetails)) {
				
			UsernamePasswordAuthenticationToken usernamePassewordAuthenticationToken = new UsernamePasswordAuthenticationToken(
						userDetails, null, userDetails.getAuthorities());
				usernamePassewordAuthenticationToken.setDetails( new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(usernamePassewordAuthenticationToken);
			}			
		}
		//means the	next filter continues---	
		//chain.doFilter(request, response);			
	filterChain.doFilter(request, response);		
					
					
					
			
		
	}

}
