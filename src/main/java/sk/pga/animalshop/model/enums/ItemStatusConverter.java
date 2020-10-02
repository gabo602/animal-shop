package sk.pga.animalshop.model.enums;

import javax.persistence.AttributeConverter;

public class ItemStatusConverter implements AttributeConverter<ItemStatus, Integer> {

	@Override
	public Integer convertToDatabaseColumn(ItemStatus attribute) {
		return attribute.getId();
	}

	@Override
	public ItemStatus convertToEntityAttribute(Integer dbData) {
		return ItemStatus.getById(dbData);
	}
	
}
