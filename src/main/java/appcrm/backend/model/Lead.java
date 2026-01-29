package appcrm.backend.model;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;


@Entity
@Table(name="tb_leads")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Lead {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String description;
    @Column(precision = 10, scale = 2)
    private BigDecimal initPrice;
    @OneToOne
    @JsonManagedReference
    private Schedule schedule;
    @JoinColumn(name="customer_id")
    @ManyToOne
    @JsonBackReference
    private Customer customer;

}
