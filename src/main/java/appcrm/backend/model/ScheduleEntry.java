package appcrm.backend.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name="tb_scheduleEntry")
public class ScheduleEntry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private OffsetDateTime time;
    private String chatHistory;
    @JoinColumn(name = "schedule_id")
    @ManyToOne
    @JsonBackReference
    private Schedule schedule;

    public ScheduleEntry (OffsetDateTime time, String chatHistory){
        this.time = time;
        this.chatHistory = chatHistory;

    }

}

