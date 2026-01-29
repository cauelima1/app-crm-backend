package appcrm.backend.service;

import appcrm.backend.model.Customer;
import appcrm.backend.model.DTOs.CustomerDto;
import appcrm.backend.model.DTOs.LeadDto;
import appcrm.backend.model.Lead;
import appcrm.backend.repository.CustomerRepository;
import appcrm.backend.repository.LeadsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LeadService {


    private final LeadsRepository repository;

    public LeadService(LeadsRepository repository) {
        this.repository = repository;
    }

    @Autowired
    private CustomerRepository customerRepository;

    public ResponseEntity<Lead> create(LeadDto leadDto) {
        if (customerRepository.existsByEmail(leadDto.email())) {
            Lead newLead = new Lead();
            newLead.setCustomer(customerRepository.findByEmail(leadDto.email()));
            newLead.setDescription(leadDto.description());
            newLead.setInitPrice(leadDto.initPrice());
            repository.save(newLead);
            return ResponseEntity.ok(newLead);
        }
        return ResponseEntity.badRequest().build();
    }

    public ResponseEntity<List<Lead>> getLeads(String email) {
        return ResponseEntity.ok(customerRepository.findByEmail(email).getLeads());
    }

    public ResponseEntity<?> deleteLead(Long id) {
        return repository.findById(id)
                .map(lead -> {
                    repository.delete(lead);
                    return ResponseEntity.ok().build();
                })
                .orElse(ResponseEntity.badRequest().build());
    }

    public ResponseEntity<?> editLead (Long id, LeadDto leadDto){
        Optional<Lead> lead = repository.findById(id);
        if (lead.isPresent()){
            if (leadDto.description() != null){
                lead.get().setDescription(leadDto.description());
            }
            if (leadDto.initPrice() != null){
                lead.get().setInitPrice(leadDto.initPrice());
            }

            repository.save(lead.get());
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();
    }

}
