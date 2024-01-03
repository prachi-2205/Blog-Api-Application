package com.prachi.blog_api.repositiories;

import java.awt.print.Pageable;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

import com.prachi.blog_api.entity.Category;
import com.prachi.blog_api.entity.Post;
import com.prachi.blog_api.entity.UserInfo;

public interface PostRepo extends JpaRepository<Post, Integer> {
	List<Post> findByUser(UserInfo user);
	List<Post> findByCategory(Category category);
	//Page<Post> findAll(Pageable p);
	
	List<Post> findByTitleContaining(String title);
}
