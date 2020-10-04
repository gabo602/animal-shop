package sk.pga.animalshop.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.domain.Pageable;

import sk.pga.animalshop.model.projection.ProductFull;
import sk.pga.animalshop.model.projection.ProductView;

public interface ProductService {
	
	List<ProductView> findAllByPriceBetweenAndNameStartsWith(BigDecimal minPrice, BigDecimal maxPrice, String nameSequence, Pageable pageable);
	
	ProductFull findAllById(Integer id);
}
