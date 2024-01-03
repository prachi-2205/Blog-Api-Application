package com.prachi.blog_api.service.impl;

import java.util.List;

import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.prachi.blog_api.entity.Category;

import com.prachi.blog_api.exceptions.ResourceNotFoundException;
import com.prachi.blog_api.payload.CategoryDto;

import com.prachi.blog_api.repositiories.categoryRepo;
import com.prachi.blog_api.service.CategoryService;
@Service
public class CategoryServiceImpl  implements CategoryService{
	
	@Autowired
	private categoryRepo categoryRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	
	public CategoryDto createCategory(CategoryDto categoryDto)
	{
		Category cat=this.modelMapper.map(categoryDto,Category.class);
		Category addedCat=this.categoryRepo.save(cat);
		return this.modelMapper.map(addedCat, CategoryDto.class);
	}
	public CategoryDto updateCategory(CategoryDto categoryDto,Integer categoryId)
	{
		Category category=this.categoryRepo.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category","Id",categoryId));
		
		
		category.setCategoryDescription(categoryDto.getCategoryDescription());
		category.setCategoryTitle(categoryDto.getCategoryTitle());
		Category updateCateogry=this.categoryRepo.save(category);
		return this.modelMapper.map(updateCateogry, CategoryDto.class);
	}
	public CategoryDto getCategoryById(Integer categoryId)
	{
		Category category=this.categoryRepo.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category","Id",categoryId));
		return this.modelMapper.map(category,CategoryDto.class);
	}
	public void deleteCategory(Integer categoryId)
	{
		Category category=this.categoryRepo.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category","Id",categoryId));
		this.categoryRepo.delete(category);
	}
	
	public List<CategoryDto> getAllCategory()
	{
		 List<Category> categorys=this.categoryRepo.findAll();
		 
		 List<CategoryDto> categoryDto=categorys.stream().map(category->this.modelMapper.map(category,CategoryDto.class)).collect(Collectors.toList());
		return categoryDto;
	}
	
}
