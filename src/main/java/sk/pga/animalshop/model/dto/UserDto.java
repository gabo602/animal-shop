package sk.pga.animalshop.model.dto;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class UserDto {
	@NotBlank
	private String	username;
	@NotBlank
	private String	password;
	@NotBlank
	private String	email;
}