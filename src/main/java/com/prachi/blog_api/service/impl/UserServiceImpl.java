package com.prachi.blog_api.service.impl;

import java.util.ArrayList;




import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import com.prachi.blog_api.entity.UserInfo;
import com.prachi.blog_api.exceptions.ResourceNotFoundException;
import com.prachi.blog_api.payload.UserDto;
import com.prachi.blog_api.repositiories.UserRepo;
import com.prachi.blog_api.service.UserService;

import jakarta.security.auth.message.AuthException;


@Service
public class UserServiceImpl implements UserDetailsService{
	
	@Autowired
	private UserRepo userRepo;
	
	 @Autowired
	   private PasswordEncoder passwordEncoder;
	
	@Autowired
	private ModelMapper modelMapper;
	
	public UserDetails loadUserByUsername(String username){
		UserInfo user = userRepo.findByUsername(username)
                .orElseThrow(()->new com.prachi.blog_api.exceptions.UsernameNotFoundException("User", "User Name", username));
		
        var authorities = new ArrayList<GrantedAuthority>();
        authorities.add(new SimpleGrantedAuthority(user.getUserRole()));

        return new User(user.getUsername(), user.getPassword(), authorities);
	}
	
	public UserDto createUser(UserDto userDto) throws AuthException {
		  String username = userDto.getUsername();
	       String email=userDto.getEmail_id();
	        String password = userDto.getPassword();
	        String userRole = userDto.getUserRole();
	        String about=userDto.getAbout();
	        // Check whether username exists or not
	        Optional<UserInfo> isExists = userRepo.findByUsername(username);

	        if (!isExists.isEmpty()) {
	            throw new AuthException("User Already Exits");
	        }

	        // Create new user
	        UserInfo user = new UserInfo();
	        user.setUsername(username);
	       user.setEmail_id(email);
	       user.setAbout(about);
	        user.setPassword(passwordEncoder.encode(password));
	        user.setUserRole(userRole);

	        // Save user
	        UserInfo savedUser = this.userRepo.save(user);
	        return this.modelMapper.map(savedUser, UserDto.class);
	}
	
	public UserDto updateUser(UserDto userDto,Integer userId)
	{
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username = ((UserDetails)principal).getUsername();
		UserInfo user = userRepo.getByUserId(username);
		
		if(user.getId().equals(userId))
		{
			user.setAbout(userDto.getAbout());
			user.setEmail_id(userDto.getEmail_id());
			user.setUsername(userDto.getUsername());
			user.setPassword(userDto.getPassword());
			UserInfo saveUser=this.userRepo.save(user);
			
			return this.userToDto(saveUser);
		}
		else
		{
			new AuthException("You Are Not Authorize to Update another User Details");
		}
		return userDto;
		
		
		
		
	}
	
	public UserDto getUserById(Integer userId)
	{
		UserInfo user=this.userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundException("user","Id",userId));
		return this.userToDto(user);
	}
	
	public List<UserDto> getAllUser()
	{
		 List<UserInfo> users=this.userRepo.findAll();
		 
		 List<UserDto> usersDto=users.stream().map(user->this.userToDto(user)).collect(Collectors.toList());
		return usersDto;
	}
	
	public void deleteUser(Integer userId)
	{
		UserInfo user=this.userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundException("user","Id",userId));
		this.userRepo.delete(user);
	}
	
	
	public UserInfo dtoUser(UserDto userDto)
	{ 
		UserInfo user=this.modelMapper.map(userDto,UserInfo.class);
		
		return user;
	}
	
	public UserDto userToDto(UserInfo user)
	{ 
		UserDto userDto=this.modelMapper.map(user, UserDto.class);
		
		return userDto;  
	}

	
	

}
