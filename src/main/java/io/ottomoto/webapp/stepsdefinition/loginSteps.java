package io.ottomoto.webapp.stepsdefinition;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class loginSteps {

        @Given("I am in the login page of Ottomoto")
        public void i_am_in_the_login_page_of_ottomoto() {
            System.out.println("Login page");
        }
        @When("I input the {string} and the {string} correctly")
        public void i_input_the_and_the_correctly(String username, String password) {
            System.out.println(String.format("Username %s and Password %s correctly", username, password));
        }
        @Then("I should be logged in the home page")
        public void i_should_be_logged_in_the_home_page() {
            System.out.println("Successfully login");
        }

        @When("I input the {string} and the {string} incorrectly")
        public void iInputTheAndTheIncorrectly(String username, String password) {
            System.out.println(String.format("Username %s and Password %s incorrectly", username, password));
        }

        @Then("I should get a wrong credentials message")
        public void iShouldGetAWrongCredentialsMessage() {
            System.out.println("Wrong credentials!!");
        }

        @When("I input the {string} and the {string} with invalid email format")
        public void iInputTheAndTheWithInvalidEmailFormat(String username, String password) {
            System.out.println(String.format("Username %s and Password %s invalid email", username, password));
        }

        @Then("I should get a invalid email format message")
        public void iShouldGetAInvalidEmailFormatMessage() {
            System.out.println("Invalid email!!");
        }
}
