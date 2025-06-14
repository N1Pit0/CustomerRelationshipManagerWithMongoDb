package com.mygym.crm.backstages.core.services;

import com.mygym.crm.backstages.core.dtos.request.traineedto.TraineeDto;
import com.mygym.crm.backstages.domain.models.Trainee;
import com.mygym.crm.backstages.domain.models.Trainer;
import com.mygym.crm.backstages.domain.models.Training;
import com.mygym.crm.backstages.interfaces.services.TraineeServiceCommon;

import java.time.LocalDate;
import java.util.Optional;
import java.util.Set;

public class TraineeServiceDecorator implements TraineeServiceCommon {

    private TraineeServiceCommon traineeService;

    public TraineeServiceDecorator(TraineeServiceCommon traineeService) {
        this.traineeService = traineeService;
    }

    @Override
    public Optional<Trainee> delete(Long id) {
        return traineeService.delete(id);
    }

    @Override
    public Optional<Trainee> deleteWithUserName(String userName) {
        return traineeService.deleteWithUserName(userName);
    }

    @Override
    public Optional<Set<Training>> getTraineeTrainings(String username, LocalDate fromDate, LocalDate toDate, String trainerName, String trainingTypeName) {
        return traineeService.getTraineeTrainings(username, fromDate, toDate, trainerName, trainingTypeName);
    }

    @Override
    public Optional<Set<Trainer>> getTrainersNotTrainingTraineesWithUserName(String TraineeUserName) {
        return traineeService.getTrainersNotTrainingTraineesWithUserName(TraineeUserName);
    }

    @Override
    public Optional<Trainee> create(TraineeDto traineeDto) {
        return traineeService.create(traineeDto);
    }

    @Override
    public Optional<Trainee> update(Long id, TraineeDto traineeDto) {
        return traineeService.update(id, traineeDto);
    }

    @Override
    public Optional<Trainee> getByUserName(String userName) {
        return traineeService.getByUserName(userName);
    }

    @Override
    public Optional<Trainee> getById(Long id) {
        return traineeService.getById(id);
    }

    @Override
    public Optional<Trainee> updateByUserName(String userName, TraineeDto trainerDto) {
        return traineeService.updateByUserName(userName, trainerDto);
    }

    @Override
    public boolean changePassword(String username, String newPassword) {
        return traineeService.changePassword(username, newPassword);
    }

    @Override
    public boolean toggleIsActive(String username) {
        return traineeService.toggleIsActive(username);
    }
}
