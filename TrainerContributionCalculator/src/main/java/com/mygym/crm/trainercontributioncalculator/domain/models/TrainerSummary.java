package com.mygym.crm.trainercontributioncalculator.domain.models;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@EqualsAndHashCode(exclude = {"monthlySummaries"})
@Document(collection = "trainer_summary")
@CompoundIndex(name = "cmp-inx-fname-lname", def = "{'firstName': 1, 'lastName' : 1}")
public class TrainerSummary {

    @Id
    private String id;

    private String username;

    private String firstName;

    private String lastName;

    private Boolean isActive;

    private Set<MonthlySummary> monthlySummaries = new HashSet<>();

}
