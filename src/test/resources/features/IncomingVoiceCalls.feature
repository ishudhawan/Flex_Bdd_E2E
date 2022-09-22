Feature:Incoming Voice Calls

  @incoming-voice @api
  Scenario: Find available phone numbers API
    Given I set api with URL "twilio"
    And I add service "available_phone" with URL and replace "account" to set API endpoint
    And I get authentication username "username" and password "password" for API authentication
    And I hit "JSON GET" api with API endpoint and request body
    And I save response status to variable "Response_status" and response body to variable "Response_body"
    And I verify value of "Response_status" to be "200"
    And I save value in "JSON" response tag "phone_number__1" to variable "To"
    And I save value in "JSON" response tag "phone_number__2" to variable "From"

  @incoming-voice @api
  Scenario: Making agent available
    Given I open the web window
    And I open the url "flex"
    And I open the url "flex"
    And I wait for "username"
    And I input text "idhawan+1@twilio.com" to object "username"
    And I input text "testdemo" to object "password"
    And I click on "signin"
    And I wait for "agent"
    And I click on "agent"
    And I wait for "offline"
    And I verify non existence of object "call_msg"
    And I click on "offline"
    And I click on "available"
    And I click on "close_notification"

  @incoming-voice @api
  Scenario: Make Incoming Call API
    Given I set api with URL "twilio"
    And I add service "make_call" with URL and replace "account" to set API endpoint
    And I get authentication username "username" and password "password" for API authentication
    And I add body param "To,From,Method,Url" with value "To,From,GET,http://demo.twilio.com/docs/voice.xml" in API
    And I hit "JSON POST" api with API endpoint and request body
    And I save response status to variable "Response_status" and response body to variable "Response_body"
    And I verify value of "Response_status" to be "201"
    And I validate response tag "status" with value "queued"
    And I save value in "JSON" response tag "sid" to variable "sid"

  @incoming-voice @api
  Scenario: Check Incoming Voice Call status API
    Given I set api with URL "twilio"
    And I add service "call_status" with URL and replace "account,sid" to set API endpoint
    And I get authentication username "username" and password "password" for API authentication
    And I hit "JSON GET" api with API endpoint and request body
    And I save response status to variable "Response_status" and response body to variable "Response_body"
    And I verify value of "Response_status" to be "200"
    And I validate response tag "status" with value "in-progress"

  @incoming-voice @api
  Scenario: Call Accept and Call Transfer
    Given I click on "accept_call"
    And I force a sleep for "10" seconds
    And A different agent logs in
    And I force a sleep for "3" seconds
    And I wait for "transfer_call"
    And I click on "transfer_call"
    And I wait for "agents"
    And I mouse hover on "demo_agent"
    And I wait for "transfer_to_agent"
    And I click on "transfer_to_agent"
    And I wait for "complete_call"
    And I force a sleep for "3" seconds
    And I click on "complete_call"
    And I force a sleep for "3" seconds
    And I close all browsers
    And The call is accepted by the agent

  @incoming-voice @api
  Scenario: Check Incoming Voice Call status API for completion
    Given I set api with URL "twilio"
    And I add service "call_status" with URL and replace "account,sid" to set API endpoint
    And I get authentication username "username" and password "password" for API authentication
    And I hit "JSON GET" api with API endpoint and request body
    And I save response status to variable "Response_status" and response body to variable "Response_body"
    And I verify value of "Response_status" to be "200"
    And I validate response tag "status" with value "completed"


