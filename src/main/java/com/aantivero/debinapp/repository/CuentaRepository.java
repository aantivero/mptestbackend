package com.aantivero.debinapp.repository;

import com.aantivero.debinapp.domain.Cuenta;
import com.aantivero.debinapp.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import java.util.List;

/**
 * Spring Data JPA repository for the Cuenta entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CuentaRepository extends JpaRepository<Cuenta, Long> {

    @Query("select cuenta from Cuenta cuenta where cuenta.user.login = ?#{principal.username}")
    List<Cuenta> findByUserIsCurrentUser();

    @Query("select cuenta from Cuenta cuenta where cuenta.user.login = ?#{principal.username}")
    Page<Cuenta> findByUserIsCurrentUser(Pageable pageable);

    List<Cuenta> findCuentasByUser(User user);
}
