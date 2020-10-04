package sk.pga.animalshop.model.enums;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import lombok.Getter;

@Getter
public enum Role implements Serializable {
	USER(0, "USER"),
	ADMIN(1, "ADMIN");
	
	private final int						id;
	private final String					role;
	
	private static final Map<Integer, Role>	idMap	= new HashMap<>();
	
	static {
		for (Role role : Role.values()) {
			idMap.put(role.id, role);
		}
	}
	
	public static Role getById(Integer id) {
		return idMap.get(id);
	}
	
	private Role(int id, String role) {
		this.id = id;
		this.role = role;
	}
	
}
