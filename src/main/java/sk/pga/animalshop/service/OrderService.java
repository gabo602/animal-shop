package sk.pga.animalshop.service;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;

import sk.pga.animalshop.model.dto.OrderItemDto;
import sk.pga.animalshop.model.projection.OrderView;

@PreAuthorize("authenticated")
public interface OrderService {
	
	List<OrderView> findAllByUser();
	
	void createOrder(List<OrderItemDto> orderItems);
}
