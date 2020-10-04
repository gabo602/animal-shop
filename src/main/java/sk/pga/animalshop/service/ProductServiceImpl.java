package sk.pga.animalshop.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import sk.pga.animalshop.model.projection.ProductFull;
import sk.pga.animalshop.model.projection.ProductView;
import sk.pga.animalshop.repository.ProductRepository;

@Service("productService")
@Transactional
public class ProductServiceImpl implements ProductService {
	
	@Autowired
	private ProductRepository productRepository;
	
	@Override
	public List<ProductView> findAllByPriceBetweenAndNameStartsWith(BigDecimal minPrice, BigDecimal maxPrice, String nameSequence, Pageable pageable) {
		if ((minPrice == null || maxPrice == null) && (nameSequence == null || nameSequence.isBlank())) {
			return productRepository.findBy(pageable);
		}
		
		if (minPrice == null && maxPrice == null && nameSequence != null && !nameSequence.isBlank()) {
			return productRepository.findAllByNameStartsWith(nameSequence, pageable);
		}
		
		if (minPrice != null && maxPrice != null && (nameSequence == null || nameSequence.isBlank())) {
			return productRepository.findAllByPriceBetween(minPrice, maxPrice, pageable);
		}
		
		return productRepository.findAllByPriceBetweenAndNameStartsWith(minPrice, maxPrice, nameSequence, pageable);
	}
	
	@Override
	public ProductFull findAllById(Integer id) {
		return productRepository.findAllById(id);
	}
}
