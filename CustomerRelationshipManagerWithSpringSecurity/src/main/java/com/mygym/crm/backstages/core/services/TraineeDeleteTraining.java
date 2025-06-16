package com.mygym.crm.backstages.core.services;

import com.mygym.crm.backstages.core.services.communication.TrainerContributionQueueSender;
import com.mygym.crm.backstages.core.services.mapper.TrainerMapper;
import com.mygym.crm.backstages.domain.models.Trainee;
import com.mygym.crm.backstages.domain.models.Training;
import com.mygym.crm.backstages.exceptions.custom.NoTraineeException;
import com.mygym.crm.backstages.exceptions.custom.NoTrainingException;
import com.mygym.crm.backstages.interfaces.services.TraineeServiceCommon;
import com.mygym.crm.sharedmodule.ActionEnum;
import com.mygym.crm.sharedmodule.TrainerWorkloadDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
public class TraineeDeleteTraining extends TraineeServiceDecorator{

    private final TrainerContributionQueueSender queueSender;
    private final TrainerMapper trainerMapper;
    private final static Logger LOGGER = LoggerFactory.getLogger(TraineeDeleteTraining.class);

    @Autowired
    public TraineeDeleteTraining(@Qualifier("traineeServiceImplCommon") TraineeServiceCommon service, TrainerContributionQueueSender queueSender, TrainerMapper trainerMapper) {
        super(service);
        this.queueSender = queueSender;
        this.trainerMapper = trainerMapper;
    }

    @Override
    public Optional<Trainee> delete(Long id) {
        Optional<Trainee> OptionalTrainee = super.getById(id);

        Trainee trainee = OptionalTrainee.orElseThrow( () -> {
            LOGGER.error("Trainee with id {} not found", id);
            return new NoTraineeException("Trainee with id " + id + " not found");
        });

        sendDeleteTraineeToQueue(trainee);

        return super.delete(id);
    }

    @Override
    public Optional<Trainee> deleteWithUserName(String userName) {
        LOGGER.debug("Trying to delete Trainee with name {}", userName);
        Optional<Trainee> OptionalTrainee = super.getByUserName(userName);

        Trainee trainee = OptionalTrainee.orElseThrow( () -> {
            LOGGER.error("Trainee with userName {} not found", userName);

            return new NoTraineeException("Trainee with userName " + userName + " not found");
        });

        LOGGER.info("Sending a delete signal to the queue to calculate trainer contributions");
        sendDeleteTraineeToQueue(trainee);

        return super.deleteWithUserName(userName);
    }

    private void sendDeleteTraineeToQueue(Trainee trainee) {

        trainee.getTrainings().forEach(training -> {
            LOGGER.debug("Mapping training {} to TrainerWorkLoadDto", training);

            TrainerWorkloadDto trainerWorkloadDto = trainerMapper.mapTrainingToTrainerWorkloadDto(training);

            LOGGER.debug("successfully mapped to TrainerWorkloadDto {}", trainerWorkloadDto);

            trainerWorkloadDto.setActionType(ActionEnum.DELETE);

            LOGGER.debug("Sending trainerWorkloadDto to queue");
            queueSender.sendMessage(trainerWorkloadDto);
        });
    }
}
