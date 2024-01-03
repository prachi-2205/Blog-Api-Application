package com.prachi.blog_api.service;

import java.util.List;




import com.prachi.blog_api.payload.UserDto;

public interface UserService {
	UserDto createUser(UserDto user);
	UserDto updateUser(UserDto user,Integer userId);
	 UserDto getUserById(Integer userId);
	 void deleteUser(Integer userId);
	 List<UserDto> getAllUser();
	UserService loadUserByUsername(String username);
}
