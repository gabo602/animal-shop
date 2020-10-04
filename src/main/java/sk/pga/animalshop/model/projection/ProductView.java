package sk.pga.animalshop.model.projection;

import java.math.BigDecimal;

import sk.pga.animalshop.model.enums.AnimalCategory;

public interface ProductView {
	Integer getId();
	
	String getName();
	
	BigDecimal getPrice();

	AnimalCategory getAnimalCategory();
}
