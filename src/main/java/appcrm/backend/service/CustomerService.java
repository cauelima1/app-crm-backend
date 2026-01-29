package appcrm.backend.service;

import appcrm.backend.model.Customer;
import appcrm.backend.model.DTOs.CustomerDto;
import appcrm.backend.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;


@Service
public class CustomerService {

    @Autowired
    private CustomerRepository repository;

    public ResponseEntity<Customer> create (CustomerDto customer){
        Customer newCustomer = repository.findByEmail(customer.email());
        if(newCustomer == null){
            OffsetDateTime createdAt = OffsetDateTime.now();
            newCustomer = new Customer(customer.name(), customer.fone(), customer.email(), customer.notes(), createdAt);
            repository.save(newCustomer);
            return ResponseEntity.ok(newCustomer);
        }
        throw new RuntimeException("User exists.");
    }

    public ResponseEntity<List<CustomerDto>> getAll (){
        List<CustomerDto> customers = repository.findAll().stream()
                .map(c -> new CustomerDto(c.getName(), c.getFone(), c.getEmail(), c.getNotes())).toList();
        return ResponseEntity.ok(customers);
    }

    public ResponseEntity<CustomerDto> getCustomer(String email){
        Customer customer = repository.findByEmail(email);
        if (customer != null){
           CustomerDto customerDto = dtoConverter(customer);
           return ResponseEntity.ok(customerDto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    public ResponseEntity<?> deleteCustomer(String email){
        if (repository.existsByEmail(email)) {
            repository.deleteByEmail(email);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    public ResponseEntity<CustomerDto> edit (String email, CustomerDto customerDto){
        Customer customerToEdit = repository.findByEmail(email);
        if (customerToEdit != null){
            if (customerDto.email() != null){
                customerToEdit.setEmail(customerDto.email());
            }
            if (customerDto.name() != null){
                customerToEdit.setName(customerDto.name());
            }
            if (customerDto.notes() != null){
                customerToEdit.setNotes(customerDto.notes());
            }
            if (customerDto.fone() != null){
                customerToEdit.setFone(customerDto.fone());
            }

            repository.save(customerToEdit);
            return ResponseEntity.ok(dtoConverter(customerToEdit));
        }
        throw new RuntimeException("Customer not exists.");
    }

    private CustomerDto dtoConverter (Customer customer){
        return new CustomerDto(customer.getName(),
                customer.getFone(),
                customer.getEmail(),
                customer.getNotes());
    }

}
