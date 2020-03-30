package com.example.springboot.services;

import java.util.ArrayList;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class myUserDetailsService implements UserDetailsService{

	/**
	 * будет идти сравнение с логином и паролем, которые я тут задал. Если все сходится, то доступ к API открыт.
	 * Это упрощенный вариант. В реальном проекте, тут будет подключение к базе данных.
	 */
	@Override
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
		return new User("me","mysupercoolpassword", new ArrayList<>());
	}
	
}
