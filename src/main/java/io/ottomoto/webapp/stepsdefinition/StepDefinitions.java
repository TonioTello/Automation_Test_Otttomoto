package io.ottomoto.webapp.stepsdefinition;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;

public class StepDefinitions {

    private String today;
    private String actualAnswer;
    private WebDriver driver;

    String browser = System.getProperty("browser");



    @Given("today is Sunday")
    public void today_is_sunday() {
        today = "Sunday";
        System.setProperty("webdriver.chrome.driver", "src/main/resources/drivers/chromedriver.exe");
        ChromeOptions o= new ChromeOptions();
        o.addArguments("--incognito");
        DesiredCapabilities c = DesiredCapabilities.chrome();
        c.setCapability(ChromeOptions.CAPABILITY, o);
        driver = new ChromeDriver(o);
        driver.get("https://testautomationu.applitools.com");
        //driver.get(globalProps.getFieldValue("url.google"));
        driver.manage().window().maximize();
        //Provide a handler to the home page, from the framework level -->
        System.out.println(driver.getTitle());
        System.out.println("browser = " + browser);
    }
    @When("I ask whether it's Friday yet")
    public void i_ask_whether_it_s_friday_yet() {
        actualAnswer = isItFriday(today);

    }
    @Then("I should be told {string}")
    public void i_should_be_told(String expectedAnswer) {
        System.out.println("expectedAnswer = " + expectedAnswer);
    }
    public String isItFriday(String today) {
        return "Nope";
    }

}
