package dao;

import model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // Find a user by email (used for login/authentication)
    Optional<User> findByEmail(String email);

    // Check if an email is already registered
    boolean existsByEmail(String email);
}
