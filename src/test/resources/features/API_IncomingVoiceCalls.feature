Feature:Incoming Voice Calls only from API

  @incoming-voice-api
  Scenario: Find available phone numbers API
    Given I set api with URL "twilio"
    And I add service "available_phone" with URL and replace "account" to set API endpoint
    And I get authentication username "username" and password "password" for API authentication
    And I hit "JSON GET" api with API endpoint and request body
    And I save response status to variable "Response_status" and response body to variable "Response_body"
    And I verify value of "Response_status" to be "200"
    And I save value in "JSON" response tag "phone_number__1" to variable "To"
    And I save value in "JSON" response tag "phone_number__2" to variable "From"

  @incoming-voice-api
  Scenario: Make Incoming Call API
    Given I set api with URL "twilio"
    And I add service "make_call" with URL and replace "account" to set API endpoint
    And I get authentication username "username" and password "password" for API authentication
    And I add body param "To,From,Twiml" with value "+12018440895,From,<Response><Pause>2</Pause><Say>Ahoy! Is anybody out there?</Say></Response>" in API
    And I hit "JSON POST" api with API endpoint and request body
    And I save response status to variable "Response_status" and response body to variable "Response_body"
    And I verify value of "Response_status" to be "201"
    And I validate response tag "status" with value "queued"
    And I save value in "JSON" response tag "sid" to variable "sid"

  @incoming-voice-api
  Scenario Outline: To check for <status> status for Outgoing Voice Call
    Given I set api with URL "twilio"
    And I force a sleep for "<sleep>" seconds
    And I add service "call_status" with URL and replace "account,sid" to set API endpoint
    And I get authentication username "username" and password "password" for API authentication
    And I hit "JSON GET" api with API endpoint and request body
    And I save response status to variable "Response_status" and response body to variable "Response_body"
    And I verify value of "Response_status" to be "200"
    And I validate response tag "status" with value "<status>"

    Examples:
      | status    | sleep |
      | ringing   | 1     |
      | completed | 10    |


