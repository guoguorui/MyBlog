package com.example.demo.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.example.demo.domain.MyUser;

public class MyUserService implements UserDetailsService{
	private final MyUserRepository mup;
	
	public MyUserService(MyUserRepository mup) {
		this.mup=mup;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		MyUser user=mup.findByUsername(username);
		if(user!=null) {
			List<GrantedAuthority> authorities=new ArrayList<GrantedAuthority>();
			authorities.add(new SimpleGrantedAuthority("ROLE_GGR"));
			return new User(user.getUsername(),user.getPassword(),authorities);
		}
		throw new UsernameNotFoundException("User "+username+" not found");
	}
}
