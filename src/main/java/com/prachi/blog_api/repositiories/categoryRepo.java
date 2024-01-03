package com.prachi.blog_api.repositiories;

import org.springframework.data.jpa.repository.JpaRepository;


import com.prachi.blog_api.entity.Category;


public interface categoryRepo extends JpaRepository<Category,Integer> {
	
	 
	 
}
