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

  Scenario: Update Trainee Profile
    Given a trainee with userName "John.Doe" exists
    When I send a PUT request to "/users/trainees/John.Doe" with updated trainee details
    Then the response status code for put should be 200
    And the response should contain the updated trainee profile

  Scenario: Delete Trainee Profile
    Given a trainee with userName "John.Doe" exists
    When I send a DELETE request to "/users/trainees/John.Doe"
    Then the response status code for delete should be 204

  Scenario: Toggle Trainee Active Status
    Given a trainee with userName "John.Doe" exists
    When I send a PATCH request to "/users/trainees/John.Doe/toggleActive"
    Then the response status code for patch should be 200


  Scenario: Create trainee with invalid data
    Given an invalid trainee payload for Post
    When I send a POST request to "/users/trainees" with the invalid payload
    Then the error response status code for post should be 409
    And the response should contain an error message indicating the invalid data

  Scenario: Update non-existing trainee
    Given a non-existing trainee username
    When I send a PUT request to "/users/trainees/non-existing" with updated trainee details error
    Then the error response status code for put should be 404
    And the response should contain an error message indicating the trainee was not found

