package com.prachi.blog_api.payload;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.prachi.blog_api.entity.Category;
import com.prachi.blog_api.entity.Comment;
import com.prachi.blog_api.entity.UserInfo;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@NoArgsConstructor
public class PostDto {
	
	private String postId;
	
	@NotEmpty(message="Please Enter Title")
	private String title;
	
	@NotEmpty(message="Please Enter Content")
	private String content;
	
	@NotEmpty(message="Please Enter Image")
	private String imageName;
	
	@JsonFormat(pattern = "yyyy-mm-dd")
	private LocalDate addDate;
	
	private CategoryDto category;
	
	private Set<CommentDto> comments=new HashSet<>();
	
	private UserDto user;
}
