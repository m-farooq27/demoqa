package stepdefinitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import org.testng.Assert;

public class LoginSteps {
 private boolean isLoggedIn = false;

 @Given("the user is on the login page")
 public void theUserIsOnTheLoginPage() {
 System.out.println("User is on login page");
 }

 @When("they enter valid credentials")
 public void theyEnterValidCredentials() {
 isLoggedIn = true; // Simulating successful login
 }

 @Then("they should be redirected to the dashboard")
 public void theyShouldBeRedirectedToTheDashboard() {
 Assert.assertTrue(isLoggedIn, "User logged in");
 System.out.println("User is redirected to the dashboard");

 }
}
