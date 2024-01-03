package com.prachi.blog_api.entity;
import java.time.LocalDate;

import java.util.*;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="post")
@Getter
@Setter
@NoArgsConstructor
public class Post {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer postId;
	private String title;
	
	
	@Column(length = 10000)
	private String content;
	
	
	private String imageName;
	
	
	private LocalDate addDate;
	
	@ManyToOne
	@JoinColumn(name="c_id")
	private Category category;
	
	@ManyToOne
	@JoinColumn(name="u_id")
	private UserInfo user;
	
	@OneToMany(mappedBy = "post",cascade = CascadeType.ALL)
	
	private Set<Comment> comments=new HashSet<>();
}
