package sk.pga.animalshop.repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import sk.pga.animalshop.model.db.Product;
import sk.pga.animalshop.model.projection.ProductFull;
import sk.pga.animalshop.model.projection.ProductView;

public interface ProductRepository extends JpaRepository<Product, Integer> {
	List<ProductView> findAllByPriceBetweenAndNameStartsWith(BigDecimal minPrice, BigDecimal maxPrice, String nameSequence, Pageable pageable);

	List<ProductView> findBy(Pageable pageable);

	List<ProductView> findAllByPriceBetween(BigDecimal minPrice, BigDecimal maxPrice, Pageable pageable);

	List<ProductView> findAllByNameStartsWith(String nameSequence, Pageable pageable);

	ProductFull findAllById(Integer id);
}
