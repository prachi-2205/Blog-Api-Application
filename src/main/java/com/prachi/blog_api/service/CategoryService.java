package com.prachi.blog_api.service;

import java.util.List;

import com.prachi.blog_api.payload.CategoryDto;

public interface CategoryService {
	CategoryDto createCategory(CategoryDto categoryDto);
	CategoryDto updateCategory(CategoryDto categoryDto,Integer categoryId);
	
	CategoryDto getCategoryById(Integer categoryId);
	void deleteCategory(Integer categoryId);
	List<CategoryDto> getAllCategory();
}
