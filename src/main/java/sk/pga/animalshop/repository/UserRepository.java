package sk.pga.animalshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import sk.pga.animalshop.model.db.User;
import sk.pga.animalshop.model.projection.UserView;

public interface UserRepository extends JpaRepository<User, String> {
	UserView findByUsername(String username);
}
