package sk.pga.animalshop.model.projection;

import sk.pga.animalshop.model.enums.Role;

public interface UserView {
	String getUsername();
	
	String getEmail();
	
	Role getRole();
}
