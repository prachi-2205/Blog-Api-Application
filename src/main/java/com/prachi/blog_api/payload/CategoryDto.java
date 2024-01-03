package com.prachi.blog_api.payload;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor

public class CategoryDto {

	private Integer categoryId;
	
	@NotBlank(message = "Not Be Blank")
	private String categoryTitle;
	
	@NotBlank(message = "Not Be Blank")
	private String categoryDescription;
	
}
