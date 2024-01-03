package com.prachi.blog_api.service.impl;



import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.prachi.blog_api.entity.Category;
import com.prachi.blog_api.entity.Post;
import com.prachi.blog_api.entity.UserInfo;
import com.prachi.blog_api.exceptions.ResourceNotFoundException;
import com.prachi.blog_api.payload.PostDto;
import com.prachi.blog_api.payload.PostReponse;

import com.prachi.blog_api.repositiories.PostRepo;
import com.prachi.blog_api.repositiories.UserRepo;
import com.prachi.blog_api.repositiories.categoryRepo;
import com.prachi.blog_api.service.PostService;

import jakarta.security.auth.message.AuthException;

@Service
public class PostServiceImpl implements PostService {

	@Autowired
	private UserRepo userRepo;

	@Autowired
	private categoryRepo categoryRepo;

	@Autowired
	private PostRepo postRepo;

	@Autowired
	private ModelMapper modelMapper;

	public PostDto createPost(PostDto postDto, Integer categoryId) {
		// TODO Auto-generated method stub
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username = ((UserDetails)principal).getUsername();
		UserInfo user = userRepo.getByUserId(username);
		
		
		UserInfo user1 = this.userRepo.findById(user.getId())
				.orElseThrow(() -> new ResourceNotFoundException("user", "Id", user.getId()));
		Category category = this.categoryRepo.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category", "Id", categoryId));
		Post post = this.modelMapper.map(postDto, Post.class);
		post.setImageName(postDto.getImageName());
		post.setContent(postDto.getContent());
		post.setAddDate(postDto.getAddDate());
		post.setCategory(category);
		post.setUser(user1);
		Post savePost = this.postRepo.save(post);
		return this.modelMapper.map(savePost, PostDto.class);
	}

	public PostDto updatePost(PostDto postDto, Integer postid) {
		// TODO Auto-generated method stub

		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username = ((UserDetails)principal).getUsername();
		UserInfo user = userRepo.getByUserId(username);
		Post post = this.postRepo.findById(postid)
				.orElseThrow(() -> new ResourceNotFoundException("Post", "Id", postid));

		if(user.getId().equals(post.getUser().getId()))
		{
			post.setImageName(postDto.getImageName());
			post.setTitle(postDto.getTitle());
			post.setContent(postDto.getContent());
			
			
			Post postdto=this.postRepo.save(post);
			return this.modelMapper.map(postdto, PostDto.class);
		}
		else
		{
			new AuthException("You Are Not Authorize To update another User Post");
		}
		return postDto;
	}

	public void deletePost(Integer postId) throws AuthException {
		
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username = ((UserDetails)principal).getUsername();
		UserInfo user = userRepo.getByUserId(username);
		
		
		Post post = this.postRepo.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("Post", "Id", postId));
		if(user.getUserRole().equals("ADMIN"))
		{
			this.postRepo.delete(post);
		}
		else
		{
			if(user.getId().equals(post.getUser().getId()))
			{
				this.postRepo.delete(post);
				
			}
			else
			{
				throw new AuthException("Please Delete Your Post");
			}
		}
		
		
	}

	public PostReponse getAllPost(Integer pageSize,Integer pageNumber,String sortBy) {
		
	
		
		
		org.springframework.data.domain.Pageable p=PageRequest.of(pageNumber, pageSize,Sort.by(sortBy));
		Page<Post> pagePosts = this.postRepo.findAll(p);
		List<Post> posts=pagePosts.getContent();
		

		List<PostDto> postDto = posts.stream().map(post -> this.modelMapper.map(post, PostDto.class))
				.collect(Collectors.toList());
		
		
		PostReponse postreponce=new PostReponse();
		
		postreponce.setContent(postDto);
		postreponce.setPageNumber(pagePosts.getNumber());
		postreponce.setPageSize(pagePosts.getSize() );
		postreponce.setTotalElements(pagePosts.getTotalElements());
		postreponce.setTotalPages(pagePosts.getTotalPages());
		postreponce.setLastPage(pagePosts.isLast());
		return postreponce;
	}

	public PostDto getPostById(Integer postId) {
		Post post = this.postRepo.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("Post", "Id", postId));
		return this.modelMapper.map(post, PostDto.class);
	}

	public List<PostDto> getPostByCategory(Integer cid) {
		
		Category category = this.categoryRepo.findById(cid)
				.orElseThrow(() -> new ResourceNotFoundException("Category", "Id", cid));
		List<Post> posts = this.postRepo.findByCategory(category);

		List<PostDto> postDto = posts.stream().map(post -> this.modelMapper.map(post, PostDto.class))
				.collect(Collectors.toList());
		return postDto;
	}

	public List<PostDto> getPostByUser(Integer userId) {
		// TODO Auto-generated method stub
		UserInfo user = this.userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("user", "Id", userId));
		List<Post> posts = this.postRepo.findByUser(user);

		List<PostDto> postDto = posts.stream().map(post -> this.modelMapper.map(post, PostDto.class))
				.collect(Collectors.toList());
		return postDto;

	}

	public List<PostDto> searchPost(String keyword)
	{
		List<Post> seachByTitle=this.postRepo.findByTitleContaining(keyword);
		List<PostDto> postDto = seachByTitle.stream().map(post -> this.modelMapper.map(post, PostDto.class))
				.collect(Collectors.toList());
		return postDto;
	}

	
}
