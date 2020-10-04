package sk.pga.animalshop.model.dto;

import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemDto {
	@NotBlank
	private Integer productId;
	@NotBlank
	private Integer quantity;
}