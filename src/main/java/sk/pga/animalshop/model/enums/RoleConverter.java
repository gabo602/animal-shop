package sk.pga.animalshop.model.enums;

import javax.persistence.AttributeConverter;

public class RoleConverter implements AttributeConverter<Role, Integer> {
	
	@Override
	public Integer convertToDatabaseColumn(Role attribute) {
		return attribute.getId();
	}
	
	@Override
	public Role convertToEntityAttribute(Integer dbData) {
		return Role.getById(dbData);
	}
	
}
