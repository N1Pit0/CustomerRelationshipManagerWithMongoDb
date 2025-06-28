package com.mygym.crm.backstages.core.services;

import com.mygym.crm.backstages.core.dtos.request.traineedto.TraineeDto;
import com.mygym.crm.backstages.core.services.mapper.TraineeMapper;
import com.mygym.crm.backstages.core.services.utils.UserServiceUtils;
import com.mygym.crm.backstages.domain.models.Authorities;
import com.mygym.crm.backstages.domain.models.Trainee;
import com.mygym.crm.backstages.interfaces.daorepositories.TraineeDao;
import com.mygym.crm.backstages.interfaces.services.AuthoritiesService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TraineeServiceImplCommonTest {

    @Mock
    private TraineeDao traineeDao;

    @Mock
    private UserServiceUtils userService;

    @Mock
    private AuthoritiesService authoritiesService;

    @Mock
    private TraineeMapper traineeMapper;

    @Mock
    private Logger LOGGER;


    @InjectMocks
    private TraineeServiceImplCommon traineeService;

    private TraineeDto traineeDto;


    @BeforeEach
    public void setUp(){

        traineeDto = new TraineeDto();
        traineeDto.setFirstName("John");
        traineeDto.setLastName("Doe");
        traineeDto.setAddress("123 Main St");
        traineeDto.setDateOfBirth(LocalDate.of(1980, 1, 1));


    }


    @Test
    void create_ValidTraineeDto_ReturnsCreatedTrainee() {

        // Arrange
        Trainee trainee = new Trainee();
        trainee.setFirstName(traineeDto.getFirstName());
        trainee.setLastName(traineeDto.getLastName());
        trainee.setAddress(traineeDto.getAddress());
        trainee.setDateOfBirth(traineeDto.getDateOfBirth());
        trainee.setIsActive(true);

        when(traineeMapper.traineeDtoToCommonTrainee(any(TraineeDto.class))).thenReturn(trainee);
        when(userService.generatePassword()).thenReturn("generatedPassword");
        when(userService.generateUserName(any(TraineeDto.class))).thenReturn("generatedUsername");

        // Mock the behavior of traineeDao
        when(traineeDao.create(any(Trainee.class))).thenReturn(Optional.of(trainee));

        // Act
        Optional<Trainee> result = traineeService.create(traineeDto);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(trainee, result.get());

        // Verify interactions
        verify(traineeMapper, times(1)).traineeDtoToCommonTrainee(traineeDto); // Ensure the mapper was called
        verify(userService, times(1)).generatePassword();
        verify(userService, times(1)).generateUserName(traineeDto);
        verify(traineeDao, times(1)).create(any(Trainee.class));
        verify(authoritiesService, times(1)).createAuthority(any(Authorities.class));
    }

//    @Test
//    void create_TraineeDaoReturnsEmpty_ReturnsEmptyOptional() {
//        // Arrange
//        Trainee trainee = new Trainee();
//        when(traineeMapper.traineeDtoToCommonTrainee(traineeDto)).thenReturn(trainee);
//        when(userService.generatePassword()).thenReturn("generatedPassword");
//        when(userService.generateUserName(traineeDto)).thenReturn("generatedUsername");
//        when(traineeDao.create(trainee)).thenReturn(Optional.empty());
//
//        // Act
//        Optional<Trainee> result = traineeService.create(traineeDto);
//
//        // Assert
//        assertTrue(result.isEmpty());
//        verify(traineeMapper, times(1)).traineeDtoToCommonTrainee(traineeDto);
//        verify(userService, times(1)).generatePassword();
//        verify(userService, times(1)).generateUserName(traineeDto);
//        verify(traineeDao, times(1)).create(trainee);
//        verify(authoritiesService, never()).createAuthority(any(Authorities.class));
//    }

//    @Test
//    public void testUpdateTrainee_Success() {
////        traineeService.create(traineeDto);
//
//        TraineeDto updatedDto = new TraineeDto();
//        updatedDto.setFirstName("Jana");
//        updatedDto.setLastName("Doe");
////        updatedDto.setActive(false);
//
//        traineeService.update(1L, updatedDto);
//
//        Optional<Trainee> updatedTrainee = traineeDao.select(1L);
//        assertTrue(updatedTrainee.isPresent());
//        assertEquals("Jana", updatedTrainee.get().getFirstName());
//        assertEquals("Doe", updatedTrainee.get().getLastName());
//        assertFalse(updatedTrainee.get().getIsActive());
//    }
////
//    @Test
//    public void testUpdateTrainee_Fail_NotFound() {
//        TraineeDto updatedDto = new TraineeDto();
//
//        updatedDto.setFirstName("Jana");
//        updatedDto.setLastName("Doe");
////        updatedDto.setActive(false);
//
//        assertThrows(NoTraineeException.class, () -> traineeService.update(2L, updatedDto));
//    }
////
//    @Test
//    public void testDeleteTrainee_Success() {
////        traineeService.create(traineeDto);
//
//        traineeService.delete(1L);
//
//        Optional<Trainee> deletedTrainee = traineeDao.select(1L);
//        assertFalse(deletedTrainee.isPresent());
//    }
////
//    @Test
//    public void testGetById_Success() {
////        traineeService.create(traineeDto);
//
//        Optional<Trainee> foundTrainee = traineeService.getById(1L);
//
//        assertTrue(foundTrainee.isPresent());
//        assertEquals("John", foundTrainee.get().getFirstName());
//    }
//
//    @Test
//    public void testGetById_NotFound() {
//        Optional<Trainee> foundTrainee = traineeService.getById(999L);
//
//        assertFalse(foundTrainee.isPresent());
//    }
}
