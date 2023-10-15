package digital.metro.pricing.calculator.controller;

import digital.metro.pricing.calculator.dto.CustomerCreateDto;
import digital.metro.pricing.calculator.model.Customer;
import digital.metro.pricing.calculator.service.CustomerService;
import java.util.List;
import javax.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("customers")
public class CustomerController {

    private CustomerService customerService;

    @PostMapping
    public Customer createCustomer(@RequestBody @Valid CustomerCreateDto customerCreateDto) {
        return customerService.createCustomer(customerCreateDto);
    }

    @GetMapping
    public List<Customer> getAll() {
        return customerService.retrieveAll();
    }

}
