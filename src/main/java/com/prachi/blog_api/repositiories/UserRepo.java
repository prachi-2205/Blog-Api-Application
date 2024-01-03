package com.prachi.blog_api.repositiories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.prachi.blog_api.entity.UserInfo;

public interface UserRepo extends JpaRepository<UserInfo, Integer>{

	Optional<UserInfo> findByUsername(String username);
	
	@Query(value = "select * from user_tbl where username=?1",nativeQuery = true) 
	UserInfo getByUserId(String username);
}
