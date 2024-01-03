package com.prachi.blog_api.controller;

import java.util.Map;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.prachi.blog_api.payload.CategoryDto;
import com.prachi.blog_api.payload.CommentDto;
import com.prachi.blog_api.service.CommentService;

import jakarta.validation.Valid;


@RestController
@RequestMapping("api/comment")
public class CommentController {
	
	@Autowired
	private CommentService commentService;
	
	
	@PostMapping("/createComment/{postId}")
	public ResponseEntity<CommentDto> createComment(@RequestBody CommentDto commentDto,@PathVariable Integer postId)
	{
		
		CommentDto	 comment=this.commentService.createComment(commentDto,postId);
		return new ResponseEntity<>(comment,HttpStatus.CREATED);
		
	}
	@DeleteMapping("/deleteComment/{commentId}")
	public ResponseEntity<Map<String, String>> deleteComment(@PathVariable("commentId") Integer cid)
	{
		
		this.commentService.deleteComment(cid);
		return ResponseEntity.ok(Map.of("message","deleted Successfully"));
		
	}
}
