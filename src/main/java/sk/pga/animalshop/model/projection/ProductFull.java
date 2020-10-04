package sk.pga.animalshop.model.projection;

import java.math.BigDecimal;
import java.util.List;

import sk.pga.animalshop.model.enums.AnimalCategory;

public interface ProductFull {
	Integer getId();
	
	String getName();
	
	BigDecimal getPrice();
	
	AnimalCategory getAnimalCategory();
	
	String getDescription();
	
	List<GalleryView> getGallery();
	
	interface GalleryView {
		Integer getId();
		
		String getUrl();
	}
}
