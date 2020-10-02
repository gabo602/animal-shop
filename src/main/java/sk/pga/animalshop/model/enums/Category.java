package sk.pga.animalshop.model.enums;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import lombok.Getter;

@Getter
public enum Category implements Serializable {
	/**
	 * SupplierOrderItem = DO NOT ASSIGN, NOT USED
	 * OrderItem = Just created order item
	 */
	DOG(0, "DOG"),
	/**
	 * SupplierOrderItem = waiting for WS reponse
	 * OrderItem = order placed in supplier warehouse
	 */
	CAT(1, "CAT"),
	/**
	 * SupplierOrderItem = Supplier confirmed the order
	 * OrderItem = Supplier confirmed the order
	 */
	OTHER(2, "OTHER");
	
	private final int									id;
	private final String								status;
	
	private static final Map<Integer, Category>	idMap	= new HashMap<>();
	
	static {
		for (Category status : Category.values()) {
			idMap.put(status.id, status);
		}
	}
	
	public static Category getById(Integer id) {
		return idMap.get(id);
	}
	
	private Category(int id, String status) {
		this.id = id;
		this.status = status;
	}
	
}