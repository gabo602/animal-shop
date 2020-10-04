package sk.pga.animalshop.model.db;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import sk.pga.animalshop.model.enums.Role;
import sk.pga.animalshop.model.enums.RoleConverter;

//**User**
//- email
//- username

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "USER")
public class User {
	
	@Id
	@Column(name = "username")
	@NotEmpty
	private String	username;
	
	@NotEmpty
	@Column(name = "password", nullable = false)
	@JsonProperty(access = Access.WRITE_ONLY)
	private String	password;
	
	@NotEmpty
	@Column(name = "email", nullable = false)
	private String	email;
	
	@Column(name = "role", nullable = false)
	@Convert(converter = RoleConverter.class)
	private Role	role;
}
