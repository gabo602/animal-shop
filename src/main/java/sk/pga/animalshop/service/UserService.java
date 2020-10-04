package sk.pga.animalshop.service;

import org.springframework.security.access.prepost.PreAuthorize;

import sk.pga.animalshop.model.db.User;
import sk.pga.animalshop.model.dto.UserDto;
import sk.pga.animalshop.model.projection.UserView;

public interface UserService {
	
	@PreAuthorize("authenticated")
	UserView findByUsername(String username);

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	void saveAdminUser(UserDto userDto);

	void saveRegularUser(UserDto userDto);
	
	@PreAuthorize("authenticated")
	User getCurrentUser();
}