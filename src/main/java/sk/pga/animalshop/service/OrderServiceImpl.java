package sk.pga.animalshop.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;
import sk.pga.animalshop.model.db.Order;
import sk.pga.animalshop.model.db.OrderItem;
import sk.pga.animalshop.model.db.Product;
import sk.pga.animalshop.model.db.User;
import sk.pga.animalshop.model.dto.OrderItemDto;
import sk.pga.animalshop.model.projection.OrderView;
import sk.pga.animalshop.repository.OrderItemRepository;
import sk.pga.animalshop.repository.OrderRepository;
import sk.pga.animalshop.repository.ProductRepository;

@Service("orderService")
@Transactional
@Slf4j
public class OrderServiceImpl implements OrderService {
	
	@Autowired
	private OrderRepository		orderRepository;
	
	@Autowired
	private OrderItemRepository	orderItemRepository;
	
	@Autowired
	private ProductRepository	productRepository;
	
	@Autowired
	private UserService			userService;
	
	@Override
	public List<OrderView> findAllByUser() {
		User currentUser = userService.getCurrentUser();
		if (currentUser == null) {
			logger.error("Current user not found!");
			return List.of((OrderView) null);
		}
		return orderRepository.findAllByCreator(currentUser);
	}
	
	@Override
	public void createOrder(List<OrderItemDto> orderItems) {
		User currentUser = userService.getCurrentUser();
		if (currentUser == null) {
			logger.error("Current user not found!");
			return;
		}
		
		if (orderItems.isEmpty()) {
			logger.error("There are no items to save.");
			throw new RuntimeException("There are no items to save.");
		}
		
		List<Product> productList = productRepository.findAllById(orderItems.stream().map(orderItemDto -> orderItemDto.getProductId()).collect(Collectors.toList()));
		if (productList == null || productList.isEmpty()) {
			logger.error("There are no valid items to save.");
			throw new RuntimeException("There are no valid items to save.");
		}
		
		Map<Integer, Product> productMap = productList.stream().distinct().collect(Collectors.toMap(Product::getId, Function.identity()));
		
		BigDecimal totalPrice = BigDecimal.ZERO;
		Order order = new Order();
		
		order.setCreator(currentUser);
		order.setConfirmationDateTime(LocalDateTime.now());
		order.setTotalPrice(totalPrice);
		
		Order savedOrder = orderRepository.save(order);
		Set<OrderItem> orderItemSet = new HashSet<>();
		
		for (OrderItemDto orderItemDto : orderItems) {
			OrderItem orderItem = new OrderItem();
			orderItem.setQuantity(orderItemDto.getQuantity());
			
			orderItem.setOrder(savedOrder);
			
			Product product = productMap.get(orderItemDto.getProductId());
			
			if (product == null) {
				logger.error("Product with id: " + orderItemDto.getProductId() + " not found! Skipping product.");
				return;
			}
			
			BigDecimal orderItemPrice = product.getPrice().multiply(BigDecimal.valueOf(orderItemDto.getQuantity()));
			orderItem.setPrice(orderItemPrice);
			totalPrice = totalPrice.add(orderItemPrice);
			
			orderItem = orderItemRepository.save(orderItem);
			
			orderItemSet.add(orderItem);
		}
		
		savedOrder.setTotalPrice(totalPrice);
		
		orderRepository.save(savedOrder);
	}
}
