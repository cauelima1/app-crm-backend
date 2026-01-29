package appcrm.backend.controller;

import appcrm.backend.model.DTOs.LeadDto;
import appcrm.backend.model.Lead;
import appcrm.backend.service.LeadService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/leads")
@RestController
public class LeadController {

    @Autowired
    private LeadService service;

    @PostMapping
    public ResponseEntity<Lead> create (@RequestBody LeadDto leadDto){
        return service.create(leadDto);
    }

    @GetMapping("/{email}")
    public ResponseEntity<List<Lead>> getLeads(@PathVariable String email){
        return service.getLeads(email);
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<?> editLead(@PathVariable Long id , @RequestBody @Valid LeadDto leadDto){
        return service.editLead(id, leadDto);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteLead(@PathVariable Long id){
        return service.deleteLead(id);
    }
}
