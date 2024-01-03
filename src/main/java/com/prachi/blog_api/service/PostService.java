package com.prachi.blog_api.service;

import java.util.List;

import com.prachi.blog_api.entity.Post;
import com.prachi.blog_api.payload.CategoryDto;
import com.prachi.blog_api.payload.PostDto;
import com.prachi.blog_api.payload.PostReponse;

import jakarta.security.auth.message.AuthException;
import jakarta.validation.Valid;

public interface PostService {
	PostDto createPost(PostDto postDto, Integer categoryId);
	 
	 List<PostDto> getPostByCategory(Integer cid);
	 List<PostDto> getPostByUser(Integer userId);
	 PostDto getPostById(Integer postId) ;
	 PostReponse getAllPost(Integer pageSize,Integer pageNumber,String sortBys);
	 void deletePost(Integer id) throws AuthException;
	 PostDto updatePost(PostDto postDto, Integer postid);
	 List<PostDto> searchPost(String keyword);
	 
}
