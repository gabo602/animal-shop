package sk.pga.animalshop.model.enums;

import javax.persistence.AttributeConverter;

public class AnimalCategoryConverter implements AttributeConverter<AnimalCategory, Integer> {
	
	@Override
	public Integer convertToDatabaseColumn(AnimalCategory attribute) {
		return attribute.getId();
	}
	
	@Override
	public AnimalCategory convertToEntityAttribute(Integer dbData) {
		return AnimalCategory.getById(dbData);
	}
	
}
