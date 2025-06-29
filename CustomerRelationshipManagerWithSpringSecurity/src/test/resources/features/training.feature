Feature: Trainee Management

  Scenario: Get Trainee Profile
    Given a trainee with userName "John.Doe" exists
    When I request the trainee profile for userName "John.Doe"
    Then the response status code for get should be 200
    And the response should contain the trainee profile

  Scenario: Create a new Trainee
    Given a valid trainee payload
    When I send a POST request to "/users/trainees"
    Then the response status code for post should be 201
    And the response should contain the trainee credentials
