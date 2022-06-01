import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/main/resources/features",
        glue = {""},
        plugin = {"pretty"},
        tags = "@LoginWrongCredentials or @testFeature"
)
public class RunCucumberTest {

}
