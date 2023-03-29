package uj.jwzp.ticketmaster.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import uj.jwzp.ticketmaster.entities.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long>{
    User findByUsernameAndPassword(String username, String password);
    Optional<User> findByUsername(String username);
}
