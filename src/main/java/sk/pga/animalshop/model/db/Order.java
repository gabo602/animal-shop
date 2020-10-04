package sk.pga.animalshop.model.db;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import sk.pga.animalshop.converter.LocalDateTimeDeserializer;
import sk.pga.animalshop.converter.LocalDateTimeSerializer;

//**Order**
//- id
//- total price
//- list of items with id of the product, price at time of purchase, count
//- time

@Getter
@Setter
@EqualsAndHashCode(exclude = { "orderItems" })
@Entity
@Table(name = "CUSTOMER_ORDER")
public class Order {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "order_id")
	private int				id;
	
	@Column(name = "total_price", columnDefinition = "NUMERIC(10,2)", nullable = false)
	private BigDecimal		totalPrice;
	
	@OneToMany(mappedBy = "order", fetch = FetchType.EAGER)
	@JsonManagedReference
	private Set<OrderItem>	orderItems	= new HashSet<>();
	
	@Column(name = "confirmation_datetime", nullable = false)
	@JsonDeserialize(using = LocalDateTimeDeserializer.class)
	@JsonSerialize(using = LocalDateTimeSerializer.class)
	private LocalDateTime	confirmationDateTime;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "creator", referencedColumnName = "username", nullable = false)
	private User			creator;
}
