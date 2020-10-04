package sk.pga.animalshop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import sk.pga.animalshop.model.db.Order;
import sk.pga.animalshop.model.db.User;
import sk.pga.animalshop.model.projection.OrderView;

public interface OrderRepository extends JpaRepository<Order, Integer> {
	
	List<OrderView> findAllByCreator(User creator);
}
