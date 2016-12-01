package com.allowify;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.allowify.model.SecurityUser;
import com.allowify.model.User;
import com.allowify.repository.UserRepositoryCustom;

@Component
public class CustomUserDetailsService implements UserDetailsService
{
	@Autowired
	private final UserRepositoryCustom userService;
	

	@Autowired
	public CustomUserDetailsService(UserRepositoryCustom userService) {
		this.userService = userService;
	}

	@Override
	public UserDetails loadUserByUsername(String userName)
			throws UsernameNotFoundException {
		
		User user = userService.findByUserName(userName);
		if(user == null){
			throw new UsernameNotFoundException("UserName "+userName+" not found");
		}
		return new SecurityUser(user);
	}
	
}
