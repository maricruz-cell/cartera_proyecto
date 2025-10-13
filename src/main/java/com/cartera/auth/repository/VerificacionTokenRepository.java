package com.cartera.auth.repository;

import com.cartera.auth.model.VerificacionToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface VerificacionTokenRepository extends JpaRepository<VerificacionToken, Long> {
    Optional<VerificacionToken> findByToken(String token);
}
