package sk.pga.animalshop.model.projection;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public interface OrderView {
	
	Integer getId();
	
	BigDecimal getTotalPrice();
	
	List<OrderItemView> getOrderItems();
	
	LocalDateTime getConfirmationDateTime();
	
	UserView getCreator();
	
	public interface OrderItemView {
		Integer getId();
		
		ProductView getProduct();
		
		BigDecimal getPrice();
		
		Integer getQuantity();
	}
}
