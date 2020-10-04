package sk.pga.animalshop.security;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import lombok.Getter;

@Getter
public class CustomUserDetails extends User {
	
	private static final long	serialVersionUID	= -4540963415644159294L;
	
	private final String		role;
	
	public CustomUserDetails(String username, String password, boolean enabled, String role, Collection<? extends GrantedAuthority> authorities) {
		super(username, password, enabled, true, true, true, authorities);
		this.role = role;
	}
	
	@Override
	public String toString() {
		return "User " + getUsername();
	}
}
