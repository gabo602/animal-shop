package sk.pga.animalshop.model.dto;

import java.util.List;

import lombok.Data;

@Data
public class OrderDto {
	private String	username;
	private String	password;
	private List<OrderItemDto> orderItems;
}