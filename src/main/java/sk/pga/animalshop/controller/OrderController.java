package sk.pga.animalshop.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import sk.pga.animalshop.model.dto.OrderItemDto;
import sk.pga.animalshop.model.projection.OrderView;
import sk.pga.animalshop.service.OrderService;

@RestController
@RequestMapping("/rest")
@PreAuthorize("authenticated")
public class OrderController {
	
	@Autowired
	private OrderService orderService;
	
	@RequestMapping(value = { "/getMyOrders" }, method = RequestMethod.GET)
	public ResponseEntity<List<OrderView>> getOrders4CurrentUser(UriComponentsBuilder ucBuilder) {
		List<OrderView> orders = orderService.findAllByUser();
		
		return new ResponseEntity<List<OrderView>>(orders, HttpStatus.OK);
	}
	
	@RequestMapping(value = { "/placeOrder" }, method = RequestMethod.POST)
	public ResponseEntity<Void> placeOrder(@Validated @RequestBody List<OrderItemDto> orderItems) {
		
		try {
			orderService.createOrder(orderItems);
		} catch (RuntimeException e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return new ResponseEntity<>(HttpStatus.CREATED);
	}
}
