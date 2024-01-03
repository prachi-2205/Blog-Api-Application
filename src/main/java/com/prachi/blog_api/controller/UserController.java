package com.prachi.blog_api.controller;

import java.util.List;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.prachi.blog_api.exceptions.*;
import com.prachi.blog_api.payload.AuthRequest;
import com.prachi.blog_api.payload.UserDto;
import com.prachi.blog_api.security.JwtProvider;

import com.prachi.blog_api.service.impl.UserServiceImpl;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/users")
public class UserController {

	@Autowired
	private UserServiceImpl userService;
	  @Autowired
	    private JwtProvider jwtProvider;

	    @Autowired
	    private PasswordEncoder passwordEncoder;
	
	    @GetMapping("/token")
	    public String getToken(@Valid @RequestBody AuthRequest authRequest){
	        // Get user details
	        UserDetails userDetails = userService.loadUserByUsername(authRequest.getUsername());

	        if(passwordEncoder.matches(authRequest.getPassword(), userDetails.getPassword())){
	            // Generate token
	            return jwtProvider.generateToken(authRequest.getUsername());
	        }

	        throw new AuthException("Password does not match with database!..");
	    }
    
	    
	    
	@PostMapping("/createUser")
	public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto) throws jakarta.security.auth.message.AuthException
	{
		
		UserDto createUser=this.userService.createUser(userDto);
		return new ResponseEntity<>(createUser,HttpStatus.CREATED);
		
	}
	
	@PreAuthorize("hasAuthority('USER')")
	@PutMapping("/updateUser/{userId}")
	public ResponseEntity<UserDto> updateUser(@Valid @RequestBody UserDto userDto,@PathVariable("userId") Integer uid)
	{
		
		UserDto updateUser=this.userService.updateUser(userDto,uid);
		return ResponseEntity.ok(updateUser);
		
	}
	 @PreAuthorize("hasAuthority('ADMIN')")
	@DeleteMapping("/deleteUser/{userId}")
	public ResponseEntity<Map<String, String>> deleteUser(@PathVariable("userId") Integer uid)
	{
		
		this.userService.deleteUser(uid);
		return ResponseEntity.ok(Map.of("message","deleted Successfully"));
		
	}
	 
	@PreAuthorize("hasAuthority('ADMIN')") 
	@GetMapping("/GetSingleUser/{userId}")
	public ResponseEntity<UserDto> getUserById(@PathVariable("userId") Integer uid)
	{
		UserDto getUser=this.userService.getUserById(uid);
		return ResponseEntity.ok(getUser);
	}
	
	@PreAuthorize("hasAuthority('ADMIN')")
	@GetMapping("/")
	public ResponseEntity<List<UserDto>> getAllUser()
	{
		
		return ResponseEntity.ok(this.userService.getAllUser());
	}
}
