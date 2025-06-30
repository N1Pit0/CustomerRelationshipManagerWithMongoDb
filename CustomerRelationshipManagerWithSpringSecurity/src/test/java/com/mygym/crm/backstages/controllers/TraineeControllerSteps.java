package com.mygym.crm.backstages.controllers;

import com.mygym.crm.backstages.core.dtos.request.common.CombineUserDtoWithSecurityDto;
import com.mygym.crm.backstages.core.dtos.request.common.UserDto;
import com.mygym.crm.backstages.core.dtos.request.traineedto.TraineeDto;
import com.mygym.crm.backstages.core.dtos.response.traineedto.select.SelectTraineeDto;
import com.mygym.crm.backstages.core.dtos.response.traineedto.select.TraineeCredentialsDto;
import com.mygym.crm.backstages.core.dtos.response.traineedto.update.UpdateTraineeDto;
import com.mygym.crm.backstages.core.services.mapper.TraineeMapper;
import com.mygym.crm.backstages.core.services.utils.UserServiceUtils;
import com.mygym.crm.backstages.domain.models.Trainee;
import com.mygym.crm.backstages.exceptions.custom.NoResourceException;
import com.mygym.crm.backstages.exceptions.custom.ResourceCreationException;
import com.mygym.crm.backstages.exceptions.custom.ResourceUpdateException;
import com.mygym.crm.backstages.exceptions.custom.ValidationException;
import com.mygym.crm.backstages.interfaces.services.TraineeServiceCommon;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.web.servlet.view.ContentNegotiatingViewResolver;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@CucumberContextConfiguration
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class TraineeControllerSteps {

    @Autowired
    private TestRestTemplate restTemplate;

    @MockitoBean
    @Qualifier("traineeDeleteTraining")
    private TraineeServiceCommon traineeService;

    @MockitoBean
    @Qualifier("userServiceUtilsImpl")
    private UserServiceUtils userServiceUtils;

    @MockitoBean
    private TraineeMapper traineeMapper;

    private ResponseEntity<SelectTraineeDto> getTraineeResponse;
    private ResponseEntity<String> getTraineeErrorResponse;

    private ResponseEntity<TraineeCredentialsDto> createTraineeResponse;
    private ResponseEntity<String> createTraineeErrorResponse;

    private ResponseEntity<UpdateTraineeDto> updateTraineeResponse;
    private ResponseEntity<String> updateTraineeErrorResponse;

    private ResponseEntity<Void> deleteTraineeResponse;
    private ResponseEntity<String> deleteTraineeErrorResponse;

    private ResponseEntity<Void> toggleActiveResponse;
    private ResponseEntity<String> toggleActiveErrorResponse;

    @Given("a trainee with userName {string} exists")
    public void aTraineeWithUserNameExists(String userName) {
        Trainee trainee = new Trainee();
        trainee.setUserName(userName);

        when(traineeService.getByUserName(anyString())).thenReturn(Optional.of(trainee));
    }

    @When("I request the trainee profile for userName {string}")
    public void iRequestTheTraineeProfileForUserName(String userName) {
        var selectTraineeDto = new SelectTraineeDto();
        selectTraineeDto.setFirstName("John");
        selectTraineeDto.setLastName("Doe");

        String ENDPOINT = "/users/trainees/";

        when(traineeMapper.traineeToSelectTraineeDto(any(Trainee.class))).thenReturn(selectTraineeDto);
        getTraineeResponse = restTemplate.getForEntity(ENDPOINT + userName, SelectTraineeDto.class);
    }

    @Then("the response status code for get should be {int}")
    public void theResponseStatusCodeForPostShouldBe(int statusCode) {
        assertEquals(HttpStatus.valueOf(statusCode),getTraineeResponse.getStatusCode());
    }

    @Then("the response should contain the trainee profile")
    public void theResponseShouldContainTheTraineeProfile() {
        assertNotNull(getTraineeResponse);
        var responseBody = getTraineeResponse.getBody();
        assertNotNull(responseBody);
        assertEquals("John", responseBody.getFirstName());
        assertEquals("Doe", responseBody.getLastName());
    }

    @Given("a valid trainee payload")
    public void aValidTraineePayload() {

        Trainee responseTrainee = new Trainee();
        responseTrainee.setFirstName("John");
        responseTrainee.setLastName("Doe");
        responseTrainee.setUserName("John.Doe");
        responseTrainee.setPassword("password");

        // Set the necessary fields for the trainee payload
        when(traineeService.create(any(TraineeDto.class))).thenReturn(Optional.of(responseTrainee));
        when(userServiceUtils.getPassword()).thenReturn(responseTrainee.getPassword());


    }

    @When("I send a POST request to {string}")
    public void iSendAPostRequestTo(String endpoint) {
        TraineeDto traineeDto = new TraineeDto();
        traineeDto.setFirstName("John");
        traineeDto.setLastName("Doe");
        createTraineeResponse = restTemplate.postForEntity(endpoint, traineeDto, TraineeCredentialsDto.class);
    }

    @Then("the response status code for post should be {int}")
    public void theResponseStatusCodeShouldBe(int statusCode) {
        assertEquals(HttpStatus.valueOf(statusCode),createTraineeResponse.getStatusCode());
    }

    @Then("the response should contain the trainee credentials")
    public void theResponseShouldContainTheTraineeCredentials() {
        assertNotNull(createTraineeResponse);
        var body = createTraineeResponse.getBody();
        assertNotNull(body);
        assertEquals("John.Doe", body.getUserName());
        assertEquals("password", body.getPassword());

    }

    @When("I send a PUT request to {string} with updated trainee details")
    public void iSendAPutRequestToWithUpdatedTraineeDetails(String endpoint) {
        var combineUserDtoWithSecurityDto = new CombineUserDtoWithSecurityDto<UserDto>();

        var userDto = new UserDto();
        userDto.setFirstName("Updated John");
        userDto.setLastName("Updated Doe");

        combineUserDtoWithSecurityDto.setUserDto(userDto);

        var updatedTrainee = new Trainee();
        updatedTrainee.setFirstName("Updated John");
        updatedTrainee.setLastName("Updated Doe");

        var updatedTraineeDto = new UpdateTraineeDto();
        updatedTraineeDto.setFirstName("Updated John");
        updatedTraineeDto.setLastName("Updated Doe");

        when(traineeService.updateByUserName(anyString(), any(TraineeDto.class))).thenReturn(Optional.of(updatedTrainee));
        when(traineeMapper.traineeToUpdateTraineeDto(any(Trainee.class))).thenReturn(updatedTraineeDto);

        updateTraineeResponse = restTemplate.exchange(endpoint, HttpMethod.PUT, new HttpEntity<>(combineUserDtoWithSecurityDto), UpdateTraineeDto.class);
    }

    @Then("the response status code for put should be {int}")
    public void theResponseStatusCodeForPutShouldBe(int statusCode) {
        assertEquals(HttpStatus.valueOf(statusCode), updateTraineeResponse.getStatusCode());
    }

    @Then("the response should contain the updated trainee profile")
    public void theResponseShouldContainTheUpdatedTraineeProfile() {
        assertNotNull(updateTraineeResponse);
        var responseBody = updateTraineeResponse.getBody();
        assertNotNull(responseBody);
        assertEquals("Updated John", responseBody.getFirstName());
        assertEquals("Updated Doe", responseBody.getLastName());
    }

    @When("I send a DELETE request to {string}")
    public void iSendADeleteRequestTo(String endpoint) {
        when(traineeService.deleteWithUserName(anyString())).thenReturn(Optional.of(new Trainee()));
        deleteTraineeResponse = restTemplate.exchange(endpoint, HttpMethod.DELETE, null, Void.class);
    }

    @Then("the response status code for delete should be {int}")
    public void theResponseStatusCodeForDeleteShouldBe(int statusCode) {
        assertEquals(HttpStatus.valueOf(statusCode), deleteTraineeResponse.getStatusCode());
    }

    @When("I send a PATCH request to {string}")
    public void iSendAPatchRequestTo(String endpoint) {
        when(traineeService.toggleIsActive(anyString())).thenReturn(true);
        toggleActiveResponse = restTemplate.exchange(endpoint, HttpMethod.PATCH, null, Void.class);
    }

    @Then("the response status code for patch should be {int}")
    public void theResponseStatusCodeForPatchShouldBe(int statusCode) {
        assertEquals(HttpStatus.valueOf(statusCode), toggleActiveResponse.getStatusCode());
    }

    @Given("an invalid trainee payload for Post")
    public void anInvalidTraineePayload() {
        TraineeDto traineeDto = new TraineeDto();
        traineeDto.setFirstName("John");

        doThrow(new ValidationException("Request Input Fields Validation Error"))
                .when(userServiceUtils).validateDto(any(TraineeDto.class));
    }

    @When("I send a POST request to {string} with the invalid payload")
    public void iSendAPOSTRequestToWithTheInvalidPayload(String endpoint) {
        TraineeDto traineeDto = new TraineeDto();
        traineeDto.setFirstName("John");

        createTraineeErrorResponse = restTemplate.postForEntity(endpoint, traineeDto, String.class);
    }

    @Then("the error response status code for post should be {int}")
    public void theErrorResponseStatusCodeForPostShouldBe(int statusCode) {
        assertEquals(HttpStatus.valueOf(statusCode), createTraineeErrorResponse.getStatusCode());
    }

    @Then("the response should contain an error message indicating the invalid data")
    public void theResponseShouldContainAnErrorMessageIndicatingTheInvalidData() {
        assertNotNull(createTraineeErrorResponse.getBody());
        Object responseBody = createTraineeErrorResponse.getBody();
        assertEquals("Request Input Fields Validation Error", responseBody);
    }


    @Given("a non-existing trainee username")
    public void aNonExistingTraineeUsername() {
        String username = "non-existing";
    }

    @When("I send a PUT request to {string} with updated trainee details error")
    public void iSendAPUTRequestToWithUpdatedTraineeDetailsError(String endpoint) {
        var combineUserDtoWithSecurityDto = new CombineUserDtoWithSecurityDto<UserDto>();

        var userDto = new UserDto();
        userDto.setFirstName("Updated John");
        userDto.setLastName("Updated Doe");

        combineUserDtoWithSecurityDto.setUserDto(userDto);

        var updatedTrainee = new Trainee();
        updatedTrainee.setFirstName("Updated John");
        updatedTrainee.setLastName("Updated Doe");

        var updatedTraineeDto = new UpdateTraineeDto();
        updatedTraineeDto.setFirstName("Updated John");
        updatedTraineeDto.setLastName("Updated Doe");

        when(traineeService.updateByUserName(anyString(), any(TraineeDto.class))).thenThrow(new NoResourceException("Resource Not Found"));
        when(traineeMapper.traineeToUpdateTraineeDto(any(Trainee.class))).thenReturn(updatedTraineeDto);

        updateTraineeErrorResponse = restTemplate.exchange(endpoint, HttpMethod.PUT, new HttpEntity<>(combineUserDtoWithSecurityDto), String.class);
    }

    @Then("the error response status code for put should be {int}")
    public void theErrorResponseStatusCodeForPutShouldBe(int statusCode) {
        assertEquals(HttpStatus.valueOf(statusCode), updateTraineeErrorResponse.getStatusCode());
    }


    @And("the response should contain an error message indicating the trainee was not found")
    public void theResponseShouldContainAnErrorMessageIndicatingTheTraineeWasNotFound() {
        assertNotNull(updateTraineeErrorResponse.getBody());
        Object responseBody = updateTraineeErrorResponse.getBody();
        assertEquals("Resource Not Found", responseBody);
    }
}