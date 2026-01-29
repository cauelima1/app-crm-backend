package appcrm.backend.controller;


import appcrm.backend.model.Customer;
import appcrm.backend.model.DTOs.CustomerDto;
import appcrm.backend.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    private CustomerService service;

    @PostMapping
    private ResponseEntity<?> create(@RequestBody CustomerDto customer) {
        return ResponseEntity.ok(service.create(customer));
    }

    @GetMapping
    private ResponseEntity<List<CustomerDto>> getCustomers() {
        return service.getAll();
    }

    @GetMapping("/{email}")
    public ResponseEntity<CustomerDto> getCustomer(@PathVariable String email){
        return service.getCustomer(email);
    }

    @DeleteMapping("/{email}")
    public ResponseEntity<?> deleteCustomer(@PathVariable String email){
        return service.deleteCustomer(email);
    }

    @PutMapping("/{email}")
    public ResponseEntity<?> editCustomer(@PathVariable String email, @RequestBody CustomerDto customerDto){
        return service.edit(email, customerDto);
    }
}
