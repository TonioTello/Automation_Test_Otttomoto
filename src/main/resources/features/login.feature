Feature: Login

  @LoginCorrectCredentials
  Scenario Outline: Login with the correct credentials

    Given I am in the login page of Ottomoto
    When I input the "<user>" and the "<password>" correctly
    Then I should be logged in the home page

    Examples:
      | user                  | password |
      | tonyffer7@hotmail.com | 123*     |


  @LoginWrongCredentials
  Scenario Outline: Login with the wrong credentials

    Given I am in the login page of Ottomoto
    When I input the "<user>" and the "<password>" incorrectly
    Then I should get a wrong credentials message

    Examples:
      | user                  | password |
      | tonyffer7@hotmail.com | 12345*   |

  @LoginInvalidEmailFormat
  Scenario Outline: Login with invalid email format

    Given I am in the login page of Ottomoto
    When I input the "<user>" and the "<password>" with invalid email format
    Then I should get a invalid email format message

    Examples:
      | user            | password |
      | tonyffer7*g.com | 123*     |

