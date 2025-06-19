package ru.otus.model;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "trainers")
@NamedEntityGraph(name = "trainer-only-graph")
@NamedEntityGraph(name = "trainer-with-users-training-type-and-trainees-graph",
        attributeNodes = {@NamedAttributeNode("user"), @NamedAttributeNode("trainingType"),
                @NamedAttributeNode("trainees")})
public class Trainer {

    @Id
    private Long id;

    @ManyToOne
    @JoinColumn(name = "specialization")
    private TrainingType trainingType;

    @MapsId
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "id", updatable = false)
    private User user;

    @ManyToMany(mappedBy = "trainers",
            cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    private List<Trainee> trainees;
}
