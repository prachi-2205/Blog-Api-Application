package com.prachi.blog_api.payload;







import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor  
@Getter
@Setter
public class UserDto {
	
	private Integer id;
	@NotEmpty
	@Size(min=4,message="Username must be min of 4 characters")
	private String username;
	
	@Email(message="Please Enter Valid Email Id")
	private String email_id;
	
	@NotEmpty
	@Size(min=8,max=11,message = "Password must be minimum of 8 characters and max of 11 characters!....")
	@Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,11}$")
	private String password;
	
	@NotEmpty
	private String about;
	
	private String userRole;
	
}
