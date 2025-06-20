package com.mygym.crm.trainercontributioncalculator.repositories;

import com.mygym.crm.trainercontributioncalculator.domain.models.TrainerSummary;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrainerSummaryRepository extends MongoRepository<TrainerSummary, Integer> {
    TrainerSummary findByUsername(String username);
}
