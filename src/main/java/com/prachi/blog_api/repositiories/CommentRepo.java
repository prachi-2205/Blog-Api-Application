package com.prachi.blog_api.repositiories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.prachi.blog_api.entity.Comment;

public interface CommentRepo extends JpaRepository<Comment, Integer>  {

}
