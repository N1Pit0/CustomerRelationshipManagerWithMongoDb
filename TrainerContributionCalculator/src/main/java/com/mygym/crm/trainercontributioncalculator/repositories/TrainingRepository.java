package com.mygym.crm.trainercontributioncalculator.repositories;

import com.mygym.crm.trainercontributioncalculator.domain.models.TrainerSummary;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TrainingRepository extends JpaRepository<TrainerSummary, Integer> {
    TrainerSummary findByUsername(String username);
}
