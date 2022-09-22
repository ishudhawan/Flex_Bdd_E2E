Feature:Incoming Messaging

  @message
  Scenario: Find available phone numbers API
    Given I set api with URL "twilio"
    And I add service "available_phone" with URL and replace "account" to set API endpoint
    And I get authentication username "username" and password "password" for API authentication
    And I hit "JSON GET" api with API endpoint and request body
    And I save response status to variable "Response_status" and response body to variable "Response_body"
    And I verify value of "Response_status" to be "200"
    And I save value in "JSON" response tag "phone_number__1" to variable "To"
    And I save value in "JSON" response tag "phone_number__2" to variable "From"

  @message
  Scenario: Incoming Message API
    Given I set api with URL "twilio"
    And I add service "message" with URL and replace "account" to set API endpoint
    And I get authentication username "username" and password "password" for API authentication
    And I add body param "To,From,Body" with value "To,From,Hi there" in API
    And I hit "JSON POST" api with API endpoint and request body
    And I save response status to variable "Response_status" and response body to variable "Response_body"
    And I verify value of "Response_status" to be "201"
    And I validate response tag "status" with value "queued"
    And I save value in "JSON" response tag "sid" to variable "sid"

  @message
  Scenario: Check message status API
    Given I set api with URL "twilio"
    And I force a sleep for "10" seconds
    And I add service "message_status" with URL and replace "account,sid" to set API endpoint
    And I get authentication username "username" and password "password" for API authentication
    And I hit "JSON GET" api with API endpoint and request body
    And I save response status to variable "Response_status" and response body to variable "Response_body"
    And I verify value of "Response_status" to be "200"
    And I validate response tag "status" with value "delivered"
