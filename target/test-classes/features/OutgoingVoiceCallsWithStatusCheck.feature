Feature:Outgoing Voice Calls with Status check

  @outgoing-voice
  Scenario: Find available phone numbers API
    Given I set api with URL "twilio"
    And I add service "available_phone" with URL and replace "account" to set API endpoint
    And I get authentication username "username" and password "password" for API authentication
    And I hit "JSON GET" api with API endpoint and request body
    And I save response status to variable "Response_status" and response body to variable "Response_body"
    And I verify value of "Response_status" to be "200"
    And I save value in "JSON" response tag "phone_number__1" to variable "To"
    And I save value in "JSON" response tag "phone_number__2" to variable "From"

  @outgoing-voice
  Scenario: Make Outgoing Call API with status check
    Given I set api with URL "twilio"
    And I add service "make_call" with URL and replace "account" to set API endpoint
    And I get authentication username "username" and password "password" for API authentication
    And I add body param "To,From,Method,StatusCallback,StatusCallbackEvent,StatusCallbackEvent,StatusCallbackMethod,Url" with value "To,From,GET,https://www.myapp.com/events,initiated,answered,POST,http://demo.twilio.com/docs/voice.xml" in API
    And I hit "JSON POST" api with API endpoint and request body
    And I save response status to variable "Response_status" and response body to variable "Response_body"
    And I verify value of "Response_status" to be "201"
    And I validate response tag "status" with value "queued"
    And I save value in "JSON" response tag "sid" to variable "sid"

  @outgoing-voice
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
      | status      | sleep |
      | in-progress | 1     |
      | completed   |10     |
