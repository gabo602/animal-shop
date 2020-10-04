package sk.pga.animalshop.model.enums;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import lombok.Getter;

@Getter
public enum AnimalCategory implements Serializable {
	DOG(0, "DOG"),
	CAT(1, "CAT"),
	OTHER(2, "OTHER");
	
	private final int							id;
	private final String						category;
	
	private static final Map<Integer, AnimalCategory>	idMap	= new HashMap<>();
	
	static {
		for (AnimalCategory category : AnimalCategory.values()) {
			idMap.put(category.id, category);
		}
	}
	
	public static AnimalCategory getById(Integer id) {
		return idMap.get(id);
	}
	
	private AnimalCategory(int id, String category) {
		this.id = id;
		this.category = category;
	}
	
}
