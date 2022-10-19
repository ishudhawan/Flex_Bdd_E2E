Feature:Incoming Voice Calls negative testing

  @incoming-voice-negative @api1
  Scenario: Find available phone numbers API
    Given I set api with URL "twilio"
    And I add service "available_phone" with URL and replace "account" to set API endpoint
    And I get authentication username "username" and password "password" for API authentication
    And I hit "JSON GET" api with API endpoint and request body
    And I save response status to variable "Response_status" and response body to variable "Response_body"
    And I verify value of "Response_status" to be "200"
    And I save value in "JSON" response tag "phone_number__1" to variable "To"

  @incoming-voice-negative @api1
  Scenario Outline: Incoming Call API verification for FROM attribute
    Given I set api with URL "twilio"
    And I add service "make_call" with URL and replace "account" to set API endpoint
    And I get authentication username "username" and password "password" for API authentication
    And I add body param "To,From,Method,Url,Timeout" with value "To,<value>,GET,http://demo.twilio.com/docs/voice.xml,120" in API
    And I hit "JSON POST" api with API endpoint and request body
    And I save response status to variable "Response_status" and response body to variable "Response_body"
    And I verify value of "Response_status" to be "400"
    And I validate response tag "message" with value "<error>"

    Examples:
      | value           | error                                                                                                                                                                        |
      | +18667786       | From is not a valid phone number: +18667786                                                                                                                                  |
      | +17890987654465 | From is not a valid phone number: +17890987654465                                                                                                                            |
      | gdygadba        | The source phone number provided, gdygadba, is not yet verified for your account. You may only make calls from phone numbers that you've verified or purchased from Twilio.  |
      | gygyg2288       | The source phone number provided, gygyg2288, is not yet verified for your account. You may only make calls from phone numbers that you've verified or purchased from Twilio. |
      | !!###$%         | From is not a valid phone number: !!###$%                                                                                                                                    |
      |                 | No 'From' number is specified                                                                                                                                                |

  @incoming-voice-negative @api1
  Scenario Outline: Incoming Call API verification for TO attribute
    Given I set api with URL "twilio"
    And I add service "make_call" with URL and replace "account" to set API endpoint
    And I get authentication username "username" and password "password" for API authentication
    And I add body param "To,From,Method,Url,Timeout" with value "<value>,To,GET,http://demo.twilio.com/docs/voice.xml,120" in API
    And I hit "JSON POST" api with API endpoint and request body
    And I save response status to variable "Response_status" and response body to variable "Response_body"
    And I verify value of "Response_status" to be "400"
    And I validate response tag "message" with value "<error>"

    Examples:
      | value           | error                                                                       |
      | +18667786       | The phone number you are attempting to call, +18667786, is not valid.       |
      | +17890987654465 | The phone number you are attempting to call, +17890987654465, is not valid. |
      | gdygadba        | Account not allowed to call +43942322                                       |
#      | gygyg2288       | The phone number you are attempting to call, gygyg2288, is not valid.       |
      | !!###$%         | The phone number you are attempting to call, !!###$%, is not valid.         |
      |                 | No 'To' number is specified                                                 |
