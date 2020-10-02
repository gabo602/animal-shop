package sk.pga.animalshop.model.enums;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import lombok.Getter;

@Getter
public enum ItemStatus implements Serializable {
	/**
	 * SupplierOrderItem = DO NOT ASSIGN, NOT USED
	 * OrderItem = Just created order item
	 */
	OPEN(0, "OPEN"),
	/**
	 * SupplierOrderItem = waiting for WS reponse
	 * OrderItem = order placed in supplier warehouse
	 */
	SUPPLIER_ORDER(1, "SUPPLIER_ORDER"),
	/**
	 * SupplierOrderItem = Supplier confirmed the order
	 * OrderItem = Supplier confirmed the order
	 */
	SUPPLIER_CONFIRM(2, "SUPPLIER_CONFIRM"),
	/**
	 * SupplierOrderItem = KGB can only assign CANCEL, when there is no alternative or its after order time
	 * OrderItem = whole quantity canceled in SupplierOrderItem = CANCELED 
	 */
	SUPPLIER_CANCEL(3, "SUPPLIER_CANCEL"),
	/**
	 * SupplierOrderItem = WS can only assign REORDER, when failed to order
	 * OrderItem = DO NOT ASSIGN, NOT USED
	 */
	SUPPLIER_REORDER(4, "SUPPLIER_REORDER"),
	/**
	 * SupplierOrderItem = WS can only assign REORDERED, when another place order was set
	 * OrderItem = DO NOT ASSIGN, NOT USED
	 */
	SUPPLIER_REORDERED(5, "SUPPLIER_REORDERED");
	
	private final int									id;
	private final String								status;
	
	private static final Map<Integer, ItemStatus>	idMap	= new HashMap<>();
	
	static {
		for (ItemStatus status : ItemStatus.values()) {
			idMap.put(status.id, status);
		}
	}
	
	public static ItemStatus getById(Integer id) {
		return idMap.get(id);
	}
	
	private ItemStatus(int id, String status) {
		this.id = id;
		this.status = status;
	}
	
}
