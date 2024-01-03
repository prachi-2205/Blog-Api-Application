package com.prachi.blog_api.payload;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CommentDto {
	
	private String id;
	
	@NotEmpty
	private String content;
}
