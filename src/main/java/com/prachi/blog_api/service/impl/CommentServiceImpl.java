package com.prachi.blog_api.service.impl;


import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.prachi.blog_api.entity.Comment;
import com.prachi.blog_api.entity.Post;
import com.prachi.blog_api.entity.UserInfo;
import com.prachi.blog_api.exceptions.ResourceNotFoundException;
import com.prachi.blog_api.payload.CommentDto;
import com.prachi.blog_api.repositiories.CommentRepo;
import com.prachi.blog_api.repositiories.PostRepo;
import com.prachi.blog_api.service.CommentService;
@Service
public class CommentServiceImpl implements CommentService {


	
	@Autowired
	private CommentRepo commentRepo;
	
	@Autowired
	private PostRepo postRepo;
	
	@Autowired
	private ModelMapper modelaMapper;
	@Override
	public CommentDto createComment(CommentDto commentDto, Integer postId) {
		// TODO Auto-generated method stub
		
		Post post = this.postRepo.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("Post", "Id", postId));
		
	Comment comment=this.modelaMapper.map(commentDto, Comment.class);
	
	comment.setPost(post);
	
	Comment savedPost=this.commentRepo.save(comment);
	
	
		
		return this.modelaMapper.map(savedPost, CommentDto.class);
	}

	@Override
	public void deleteComment(Integer commentId) {

		Comment comment = this.commentRepo.findById(commentId)
				.orElseThrow(() -> new ResourceNotFoundException("Comment", "Id", commentId));
		this.commentRepo.delete(comment);
	}

}
