package kr.leeinwon.devlog.domain.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmailAndProvider(String email, Provider provider);

    boolean existsByEmailAndProvider(String email, Provider provider);

}
