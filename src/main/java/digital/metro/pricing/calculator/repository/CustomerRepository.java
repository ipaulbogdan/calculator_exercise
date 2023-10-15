package digital.metro.pricing.calculator.repository;

import digital.metro.pricing.calculator.model.Customer;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    Optional<Customer> findByPublicId(UUID customerId);

    Optional<Customer> findByName(String name);
}
