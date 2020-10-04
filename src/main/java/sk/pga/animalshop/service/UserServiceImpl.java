package sk.pga.animalshop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;
import sk.pga.animalshop.model.db.User;
import sk.pga.animalshop.model.dto.UserDto;
import sk.pga.animalshop.model.enums.Role;
import sk.pga.animalshop.model.projection.UserView;
import sk.pga.animalshop.repository.UserRepository;
import sk.pga.animalshop.security.SecurityUtils;

@Service("userService")
@Transactional
@Slf4j
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserRepository	userRepository;
	
	@Autowired
	private PasswordEncoder	passwordEncoder;
	
	@Override
	public UserView findByUsername(String username) {
		return userRepository.findByUsername(username);
	}
	
	private void saveUser(UserDto userDto, Role userRole) {
		User user = userRepository.findById(userDto.getUsername()).orElse(null);
		
		if (user != null) {
			logger.error("User with name: " + userDto.getUsername() + " already exists");
			throw new DataIntegrityViolationException("User already exists");
		}
		
		user = new User(userDto.getUsername(), passwordEncoder.encode(userDto.getPassword()), userDto.getEmail(), userRole);
		
		userRepository.save(user);
	}
	
	@Override
	public void saveRegularUser(UserDto userDto) {
		saveUser(userDto, Role.USER);
	}
	
	@Override
	public void saveAdminUser(UserDto userDto) {
		saveUser(userDto, Role.ADMIN);
	}

	@Override
	public User getCurrentUser() {
		if (SecurityUtils.getPrincipal() == null) {
			logger.error("Username for current user not found!");
			return null;
		}
		String username = SecurityUtils.getPrincipal().getUsername();
		if (username == null) {
			logger.error("Username for current user not found!");
			return null;
		}
		
		return userRepository.findById(username).orElse(null);
	}
}
