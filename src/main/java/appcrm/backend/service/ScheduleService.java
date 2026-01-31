package appcrm.backend.service;

import appcrm.backend.model.Lead;
import appcrm.backend.model.Schedule;
import appcrm.backend.model.ScheduleEntry;
import appcrm.backend.repository.CustomerRepository;
import appcrm.backend.repository.LeadsRepository;
import appcrm.backend.repository.ScheduleEntryRepository;
import appcrm.backend.repository.ScheduleRepository;
import org.apache.coyote.Response;
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

    public ResponseEntity<ScheduleEntry> create (Long leadId, String chatHistory){
        Optional<Lead> lead = leadsRepository.findById(leadId);
        if (lead.isPresent()){
            if (lead.get().getSchedule() == null){
                return ResponseEntity.ok(handleNewSchedule(lead.get(), chatHistory));
            } else {
                return ResponseEntity.ok(handleExistingSchedule(lead.get().getSchedule(), chatHistory));
            }
        }
        return ResponseEntity.badRequest().build();
    }

    public ResponseEntity<List<ScheduleEntry>> entriesByLead(Long id){
        return ResponseEntity.ok(entryRepository.findAll().stream().filter( e -> e.getId().equals(id)).toList());
    }

    private ScheduleEntry handleNewSchedule (Lead lead, String chatHistory){
        Schedule newSchedule = new Schedule();
        newSchedule.setLead(lead);

        ScheduleEntry newEntry = new ScheduleEntry();
        newEntry.setSchedule(newSchedule);
        newEntry.setChatHistory(chatHistory);
        newEntry.setTime(OffsetDateTime.now());

        List<ScheduleEntry> entries = new ArrayList<>();
        entries.add(newEntry);

        newSchedule.setEntries(entries);
        repository.save(newSchedule);
        return newSchedule.getEntries().getLast();
    }

    private ScheduleEntry handleExistingSchedule(Schedule schedule, String chatHistory){
        ScheduleEntry entry = new ScheduleEntry();
        entry.setSchedule(schedule);
        entry.setChatHistory(chatHistory);
        entry.setTime(OffsetDateTime.now());

        List<ScheduleEntry> entries = schedule.getEntries();
        entries.add(entry);

        schedule.setEntries(entries);
        repository.save(schedule);
        return schedule.getEntries().getLast();
    }

    public List<Schedule> getAll(){
        return repository.findAll();
    }

}
