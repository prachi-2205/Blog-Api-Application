package com.prachi.blog_api.controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.util.StreamUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.prachi.blog_api.payload.CategoryDto;
import com.prachi.blog_api.payload.PostDto;
import com.prachi.blog_api.payload.PostReponse;
import com.prachi.blog_api.payload.UserDto;
import com.prachi.blog_api.service.FileService;
import com.prachi.blog_api.service.PostService;

import jakarta.security.auth.message.AuthException;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/posts")
public class PostController {	
	
	@Autowired
	private PostService postService;
	
	@Autowired
	private FileService fileService;
	
	@Value("${project.image}")
	private String path;
	
	@PostMapping("/createPost/{categoryId}")
	public ResponseEntity<PostDto> createPost(@Valid @RequestBody PostDto postDto,@PathVariable Integer categoryId,@RequestParam("image") MultipartFile image) throws IOException
	{
		String fileName=this.fileService.uploadImage(path, image);
		postDto.setImageName(fileName);
		PostDto	 createPost=this.postService.createPost(postDto,categoryId);
		return new ResponseEntity<>(createPost,HttpStatus.CREATED);
		
	}
	@PreAuthorize("hasAuthority('ADMIN')")
	@GetMapping("/getPostByUser/{userId}")
	public ResponseEntity<List<PostDto>> getPostByUser(@PathVariable Integer userId)
	{
		List<PostDto> post=this.postService.getPostByUser(userId);
		return new ResponseEntity<List<PostDto>>(post,HttpStatus.OK);
	}
	
	@GetMapping("/getPostByCategory/{categoryId}")
	public ResponseEntity<List<PostDto>> getPostByCategory(@PathVariable Integer categoryId)
	{
		List<PostDto> post=this.postService.getPostByCategory(categoryId);
		return new ResponseEntity<List<PostDto>>(post,HttpStatus.OK);
	}
	
	
	@GetMapping("/getPostById/{postId}")
	public ResponseEntity<PostDto> getPostById(@PathVariable Integer postId)
	{
		PostDto getPost=this.postService.getPostById(postId);
		return ResponseEntity.ok(getPost);
	}
	@GetMapping("/getAllPost")
	public ResponseEntity<PostReponse> getAllPost(@RequestParam(value="pageNumber",defaultValue="0",required=false)Integer pageNumber,@RequestParam(value="pageSize",defaultValue="5",required = false)Integer pageSize
			,@RequestParam(value = "sortBy",defaultValue="title",required = false)String sortBy)
	{
		
		return ResponseEntity.ok(this.postService.getAllPost(pageSize,pageNumber,sortBy));
	}
	
	
	@DeleteMapping("/deletePost/{postId}")
	public ResponseEntity<Map<String, String>> deletePost(@PathVariable Integer postId) throws AuthException
	{
		
		this.postService.deletePost(postId);
		return ResponseEntity.ok(Map.of("message","deleted Successfully"));
		
	}
	
	@PutMapping("/updatePost/{postId}")
	public ResponseEntity<PostDto> updatePost(@Valid @RequestBody PostDto postDto,@PathVariable Integer postId)
	{
		
		PostDto updatePost=this.postService.updatePost(postDto,postId);
		return ResponseEntity.ok(updatePost);
		
	}
	@GetMapping("/search/{keyword}")
	public ResponseEntity<List<PostDto>> searchPost(@PathVariable String keyword)
	{
		List<PostDto> post=this.postService.searchPost(keyword);
		return new ResponseEntity<List<PostDto>>(post,HttpStatus.OK);
	}
	
	@PostMapping("/updatePostImage/{postId}")
	public ResponseEntity<PostDto> updatePostImage(@RequestParam("image") MultipartFile image,@PathVariable Integer postId) throws IOException
	{
		String fileName=this.fileService.uploadImage(path, image);
		PostDto postDto=this.postService.getPostById(postId);
		postDto.setImageName(fileName);
		this.postService.updatePost(postDto, postId);
		return new ResponseEntity<PostDto>(postDto,HttpStatus.OK); 
	}
	
	@GetMapping(value="/image/{imageName}",produces = MediaType.IMAGE_JPEG_VALUE)
	public void downloadImage(@PathVariable String imageName,HttpServletResponse response) throws IOException
	{
		try {
			InputStream resource=this.fileService.getResource(path, imageName);
			response.setContentType(MediaType.IMAGE_JPEG_VALUE);
			org.springframework.util.StreamUtils.copy(resource, response.getOutputStream());
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
