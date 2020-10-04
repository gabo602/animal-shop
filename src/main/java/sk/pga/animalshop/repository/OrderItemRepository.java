package sk.pga.animalshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import sk.pga.animalshop.model.db.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem, Integer> {
}
