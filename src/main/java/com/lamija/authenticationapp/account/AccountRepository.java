package com.lamija.authenticationapp.account;

import com.lamija.authenticationapp.account.models.AccountEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<AccountEntity, Long> {
    boolean existsById(Long id);

    boolean existsByEmail(String email);

    Optional<AccountEntity> findByEmail(String email);
}
