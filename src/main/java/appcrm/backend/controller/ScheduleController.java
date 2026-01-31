package appcrm.backend.controller;

import appcrm.backend.model.DTOs.ScheduleEntryDto;
import appcrm.backend.model.Schedule;
import appcrm.backend.model.ScheduleEntry;
import appcrm.backend.service.ScheduleService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/leads/schedule")
public class ScheduleController {

    @Autowired
    private ScheduleService service;

    @PostMapping("/{leadId}")
    public ResponseEntity<ScheduleEntry> createSchedule(@PathVariable Long leadId, @RequestBody ScheduleEntryDto entryDto) {
        return service.create(leadId, entryDto.chatHistory());
    }

    @GetMapping("/{id}")
    public ResponseEntity<List<ScheduleEntry>> getAll (@PathVariable Long id){
        return service.entriesByLead(id);
    }

    @GetMapping
    public ResponseEntity<List<Schedule>> getAllSchedules(){
        List<Schedule> allSchedules = service.getAll();
        return ResponseEntity.ok(allSchedules);
    }



}
