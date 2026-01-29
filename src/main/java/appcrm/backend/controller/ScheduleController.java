package appcrm.backend.controller;

import appcrm.backend.model.ScheduleEntry;
import appcrm.backend.service.ScheduleService;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/leads/schedule")
public class ScheduleController {

    @Autowired
    private ScheduleService service;

    @PostMapping("/{id}")
    public ResponseEntity<?> createSchedule (@PathVariable Long id, @RequestBody String note){
        return service.create(id, note);
    }

    @GetMapping("/{id}")
    public ResponseEntity<List<ScheduleEntry>> getAll (@PathVariable Long id){
        return service.entriesByLead(id);
    }



}
