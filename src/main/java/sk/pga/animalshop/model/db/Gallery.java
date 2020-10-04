package sk.pga.animalshop.model.db;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

//**Product**
//- id
//- name
//- animal categories
//- price
//- description
//- gallery (list of image URLS)

@Entity
@Table(name = "GALLERY")
public class Gallery {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "gallery_id")
	private int		id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "product_id", referencedColumnName = "product_id")
	@JsonIgnore
	private Product	product;
	
	@Column(name = "url", nullable = false)
	private String	url;
}
