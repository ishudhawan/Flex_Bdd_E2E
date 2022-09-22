Feature:Flex login

  @flex
  Scenario: Login to Flex UI
    Given I open the web window
    And I open the url "flex"
    And I wait for "email"
    And I input text "" to object "email"
    And I click on "next"
    And I wait for "password"
    And I input text "" to object "password"
    And I click on "next"



