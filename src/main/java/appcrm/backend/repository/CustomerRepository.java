package appcrm.backend.repository;

import appcrm.backend.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {


    Customer findByEmail(String email);

    boolean existsByEmail(String email);

    @Transactional
    @Modifying
    void deleteByEmail(String email);
}
