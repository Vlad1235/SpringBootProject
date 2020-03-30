package com.example.springboot.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.springboot.services.myUserDetailsService;
import com.example.springboot.util.JwtUtil;
/**
 * Этот фильтр будет перехватывать request и просматривать header запроса
 * За счет того что он наследуется от OncePerRequestFilter он будет перехватывать лишь 1 раз	
 *
 * Он будет выискивать валидный(то есть совпадающий с тем, что в системе) jwt в запросе в его header. Если находит, он будет пропускать логин/пароль
 */
@Component
public class JwtRequestFilter extends OncePerRequestFilter {
	
		@Autowired
	    private myUserDetailsService userDetailsService;

	    @Autowired
	    private JwtUtil jwtUtil;

	    @Override
	    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {

	        final String authorizationHeader = request.getHeader("Authorization");

	        String username = null;
	        String jwt = null;

	        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
	            jwt = authorizationHeader.substring(7);
	            username = jwtUtil.extractUsername(jwt);
	        }

	        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
	            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
	            if (jwtUtil.validateToken(jwt, userDetails)) {
	                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = 
	                		new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
	                usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
	                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
	            }
	        }
	        chain.doFilter(request, response);
	    }
}
