package com.prachi.blog_api.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.prachi.blog_api.payload.CategoryDto;

import com.prachi.blog_api.service.CategoryService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("api/category")
public class CategoryController {
	
	
	
  @Autowired
  private CategoryService categoryService;
  	
  
	@PreAuthorize("hasAuthority('ADMIN')")
	@PostMapping("/createCategory")
	public ResponseEntity<CategoryDto> createCategory(@Valid @RequestBody CategoryDto categoryDto)
	{
		
		CategoryDto	 createCategory=this.categoryService.createCategory(categoryDto);
		return new ResponseEntity<>(createCategory,HttpStatus.CREATED);
		
	}
	@PreAuthorize("hasAuthority('ADMIN')")
	@PutMapping("/updatecategory/{categoryId}")
	public ResponseEntity<CategoryDto> updatecategory(@Valid @RequestBody CategoryDto categoryDto,@PathVariable("categoryId") Integer cid)
	{
		
		CategoryDto updatecategory=this.categoryService.updateCategory(categoryDto, cid);
		return ResponseEntity.ok(updatecategory);
		
	}
	
	
	@PreAuthorize("hasAuthority('ADMIN')")
	@DeleteMapping("/deleteCategory/{categoryId}")
	public ResponseEntity<Map<String, String>> deletecategory(@PathVariable("categoryId") Integer cid)
	{
		
		this.categoryService.deleteCategory(cid);
		return ResponseEntity.ok(Map.of("message","deleted Successfully"));
		
	}
	
	@PreAuthorize("hasAuthority('ADMIN')")
	@GetMapping("/getCategoryById/{categoryId}")
	public ResponseEntity<CategoryDto> getcategoryById(@PathVariable("categoryId") Integer uid)
	{
		CategoryDto getcategory=this.categoryService.getCategoryById(uid);
		return ResponseEntity.ok(getcategory);
	}
	
	@PreAuthorize("hasAuthority('ADMIN')")
	@GetMapping("/getCategory")
	public ResponseEntity<List<CategoryDto>> getAllcategory()
	{
		
		return ResponseEntity.ok(this.categoryService.getAllCategory());
	}
  
}
