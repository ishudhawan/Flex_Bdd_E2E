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
  Scenario: Message accept
    Given I wait for "accept_text"
    And I click on "accept_text"
    And I wait for "text_area"
    And I force a sleep for "3" seconds
    And I input text "Hi, Please let me know your concern" to object "text_area" and press enter
#    And I wait for "end_chat"
#    And I force a sleep for "3" seconds
#    And I click on "end_chat"
#    And I wait for "complete_call"
#    And I force a sleep for "3" seconds
#    And I click on "complete_call"
#    And I force a sleep for "3" seconds
#    And I close all browsers


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
