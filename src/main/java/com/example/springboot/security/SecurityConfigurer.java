package com.example.springboot.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.example.springboot.filter.JwtRequestFilter;
import com.example.springboot.services.myUserDetailsService;


/**
 * Переопределяем СТАНДАРТНУЮ аутентификацию Spring Security. Она по умолчанию не даст доступа ни к какому переходу /.
 */
@EnableWebSecurity
public class SecurityConfigurer extends WebSecurityConfigurerAdapter {

	@Autowired
	private myUserDetailsService myUserDetailService;
	
	@Autowired
	private JwtRequestFilter jwtRequestFilter;

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(myUserDetailService);
	}
	
	// так как мы указали пароль уже в классе myUserDetailsService.java
	@Bean
	public PasswordEncoder passwordEncoder() {
		return NoOpPasswordEncoder.getInstance();
	}

	/**
	 * Этот метод нужен чтобы отключить spring security конкретно от огрничения доступа к /authenticate. Ко всем остальным ограничение доступа остается.
	 */
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable()
					.authorizeRequests().antMatchers("/authenticate").permitAll()
					.anyRequest().authenticated()
					.and().sessionManagement()
					.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		http.addFilterBefore(jwtRequestFilter,UsernamePasswordAuthenticationFilter.class);
	}
	
	// решение технической проблемы несовместимости. 
	//Field authenticationManager in com.example.springboot.controllers.Controllers required a bean of type 'org.springframework.security.authentication.AuthenticationManager' that could not be found.
	@Override
	@Bean
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}
	
	
	
}
