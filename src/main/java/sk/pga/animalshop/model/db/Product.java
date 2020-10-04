package sk.pga.animalshop.model.db;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import lombok.Data;
import sk.pga.animalshop.model.enums.AnimalCategory;
import sk.pga.animalshop.model.enums.AnimalCategoryConverter;

//**Product**
//- id
//- name
//- animal categories
//- price
//- description
//- gallery (list of image URLS)

@Data
@Entity
@Table(name = "PRODUCT")
public class Product {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "product_id")
	private int				id;
	
	@NotBlank
	@Column(name = "name", nullable = false)
	private String			name;
	
	@NotBlank
	@Column(name = "animal_category", nullable = false)
	@Convert(converter = AnimalCategoryConverter.class)
	private AnimalCategory	animalCategory;
	
	@NotBlank
	@Column(name = "price", columnDefinition = "NUMERIC(10,2)", nullable = false)
	private BigDecimal		price;
	
	@Column(name = "description")
	private String			description;
	
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<Gallery>	gallery	= new HashSet<>();
}
