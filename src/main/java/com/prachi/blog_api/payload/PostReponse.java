package com.prachi.blog_api.payload;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@NoArgsConstructor
public class PostReponse {
	private List<PostDto> Content;
	private int  pageNumber;
	private int pageSize;
	private long totalElements;
	private int totalPages;
	
	private boolean lastPage;
}
