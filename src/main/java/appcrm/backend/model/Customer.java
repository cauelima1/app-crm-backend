package appcrm.backend.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;
import java.util.List;

@Entity
@Table(name= "tb_customer")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 100)
    @NotNull
    private String name;
    @Column(length = 15)
    private String fone;
    @Column(length = 100)
    @NotNull
    private String email;
    private OffsetDateTime createdAt;
    @Column(nullable = true)
    private String notes;
    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Lead> leads;

    public Customer (String name, String fone, String email, String notes, OffsetDateTime createdAt){
        this.name = name;
        this.fone = fone;
        this.email = email;
        this.notes = notes;
        this.createdAt = createdAt;
    }




}
