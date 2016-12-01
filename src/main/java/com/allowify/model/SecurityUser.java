package com.allowify.model;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class SecurityUser extends User implements UserDetails, Serializable
{

	private static final long serialVersionUID = 1L;
	
	public SecurityUser() {
		
	}
	
	public SecurityUser(User user) {
		if(user != null)
		{
			this.setId(user.getId());
			this.setUserName(user.getUserName());
			
			this.setPassword(user.getPassword());
			
			this.setRole(user.getRole());
		}		
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		
		Collection<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		
		SimpleGrantedAuthority authority = new SimpleGrantedAuthority("USER");
		authorities.add(authority);
		
		return authorities;
	}

	@Override
	public String getPassword() {
		return super.getPassword();
	}

	@Override
	public String getUsername() {
		return super.getUserName();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}	
}
