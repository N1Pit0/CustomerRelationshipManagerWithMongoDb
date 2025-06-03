package com.mygym.crm.backstages.interfaces.services;

import com.mygym.crm.backstages.core.dtos.request.trainingdto.TrainingDto;
import com.mygym.crm.backstages.domain.models.Training;
import org.springframework.ui.Model;

import java.util.Optional;

public interface TrainingService {
    Optional<Training> getById(Long id);

    Optional<Training> add(TrainingDto t, Model model);

    int deleteWithTraineeUsername(String traineeUsername);

}
