package sk.pga.animalshop.model.enums;

import javax.persistence.AttributeConverter;

public class CategoryConverter implements AttributeConverter<Category, Integer> {
	
	@Override
	public Integer convertToDatabaseColumn(Category attribute) {
		return attribute.getId();
	}
	
	@Override
	public Category convertToEntityAttribute(Integer dbData) {
		return Category.getById(dbData);
	}
	
}
