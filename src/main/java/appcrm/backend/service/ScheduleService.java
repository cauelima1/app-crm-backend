package appcrm.backend.service;

import appcrm.backend.model.Lead;
import appcrm.backend.model.Schedule;
import appcrm.backend.model.ScheduleEntry;
import appcrm.backend.repository.CustomerRepository;
import appcrm.backend.repository.LeadsRepository;
import appcrm.backend.repository.ScheduleEntryRepository;
import appcrm.backend.repository.ScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ScheduleService {

    @Autowired
    private ScheduleRepository repository;

    @Autowired
    private ScheduleEntryRepository entryRepository;

    @Autowired
    private LeadsRepository leadsRepository;
    @Autowired
    private CustomerRepository customerRepository;

    public ResponseEntity<Schedule> create (Long leadId, String note){
        Optional<Lead> lead = leadsRepository.findById(leadId);
        if (lead.isPresent()){
            if (lead.get().getSchedule() == null){
                return ResponseEntity.ok(newSchedule(lead.get(), note));
            } else {
                return ResponseEntity.ok(existingSchedule(lead.get().getSchedule(), note));
            }
        }
        return ResponseEntity.badRequest().build();
    }

    public ResponseEntity<List<ScheduleEntry>> entriesByLead(Long id){
        return ResponseEntity.ok(entryRepository.findAll().stream().filter( e -> e.getId().equals(id)).toList());
    }

    private Schedule newSchedule (Lead lead, String note){
        Schedule newSchedule = new Schedule();
        newSchedule.setLead(lead);

        List<ScheduleEntry> entries = new ArrayList<>();
        entries.add(new ScheduleEntry(OffsetDateTime.now(), note));

        newSchedule.setEntries(entries);
        return repository.save(newSchedule);
    }

    private Schedule existingSchedule(Schedule schedule, String note){
        List<ScheduleEntry> entries = schedule.getEntries();
        entries.add(new ScheduleEntry(OffsetDateTime.now(), note));

        schedule.setEntries(entries);
        return repository.save(schedule);
    }

}
