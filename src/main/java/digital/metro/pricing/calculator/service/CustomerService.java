package digital.metro.pricing.calculator.service;

import digital.metro.pricing.calculator.dto.CustomerCreateDto;
import digital.metro.pricing.calculator.model.Customer;
import digital.metro.pricing.calculator.repository.CustomerRepository;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CustomerService {

    private CustomerRepository customerRepository;

    public Customer createCustomer(CustomerCreateDto customerCreateDto) {
        if (customerRepository.findByName(customerCreateDto.getName()).isPresent()) {
            throw new IllegalArgumentException("Customer with name already exits");
        }

        var customer = new Customer()
                .setName(customerCreateDto.getName())
                .setPublicId(UUID.randomUUID());

        return customerRepository.save(customer);
    }

    public List<Customer> retrieveAll() {
        return customerRepository.findAll();
    }
}
