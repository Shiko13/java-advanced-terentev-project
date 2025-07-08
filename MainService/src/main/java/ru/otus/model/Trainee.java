package ru.otus.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "trainees")
@NamedEntityGraph(name = "trainee-with-trainers-graph", attributeNodes = {@NamedAttributeNode("trainers")})
@NamedEntityGraph(name = "trainee-with-users-and-trainers-graph",
        attributeNodes = {@NamedAttributeNode("user"), @NamedAttributeNode("trainers")})
public class Trainee {

    @Id
    private Long id;

    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    @Column(name = "address")
    private String address;

    @MapsId
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "id", updatable = false)
    private User user;

    @ManyToMany
    @JoinTable(name = "trainee_trainer", joinColumns = @JoinColumn(name = "trainee_id"),
            inverseJoinColumns = @JoinColumn(name = "trainer_id"))
    private List<Trainer> trainers;
}
