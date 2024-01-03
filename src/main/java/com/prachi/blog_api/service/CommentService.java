package com.prachi.blog_api.service;

import com.prachi.blog_api.payload.CommentDto;

public interface CommentService {
	CommentDto createComment(CommentDto commentDto,Integer postId);
	void deleteComment(Integer commentId);
}
