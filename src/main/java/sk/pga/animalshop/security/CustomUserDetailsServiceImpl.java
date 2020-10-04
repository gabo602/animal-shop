package sk.pga.animalshop.security;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;
import sk.pga.animalshop.model.db.User;
import sk.pga.animalshop.model.enums.Role;
import sk.pga.animalshop.repository.UserRepository;

@Slf4j
public class CustomUserDetailsServiceImpl implements CustomUserDetailsService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Transactional(readOnly = true)
	public CustomUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.getOne(username);
		logger.debug("User : {}", user);
		if (user == null) {
			logger.info("User not found, userId = " + username);
			throw new UsernameNotFoundException("Username not found");
		}
		return new CustomUserDetails(user.getUsername(), user.getPassword(), true,
				user.getRole().getRole(), getGrantedAuthorities(user.getRole()));
	}
	
	public static Collection<? extends GrantedAuthority> getGrantedAuthorities(Role role) {
		return List.of(new SimpleGrantedAuthority("ROLE_" + role.getRole()));
	}
	
}
