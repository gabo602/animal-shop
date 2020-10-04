package sk.pga.animalshop.controller;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import sk.pga.animalshop.model.projection.ProductFull;
import sk.pga.animalshop.model.projection.ProductView;
import sk.pga.animalshop.service.ProductService;

@RestController
@RequestMapping("/rest")
public class ProductController {
	
	@Autowired
	private ProductService productService;
	
	@RequestMapping(value = { "/getProduct" }, method = RequestMethod.GET)
	public ResponseEntity<ProductFull> findProduct(@RequestParam(value = "productId", required = true) Integer id, UriComponentsBuilder ucBuilder) {
		ProductFull product = productService.findAllById(id);
		
		if (product == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<ProductFull>(product, HttpStatus.OK);
	}
	
	@RequestMapping(value = { "/getProductList" }, method = RequestMethod.GET)
	public ResponseEntity<List<ProductView>> getProductPage(@RequestParam(value = "minPrice", required = false) BigDecimal minPrice,
			@RequestParam(value = "maxPrice", required = false) BigDecimal maxPrice, @RequestParam(value = "nameSequence", required = false) String nameSequence,
			Pageable pageable) {
		
		List<ProductView> result = productService.findAllByPriceBetweenAndNameStartsWith(minPrice, maxPrice, nameSequence, pageable);
		
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
}
