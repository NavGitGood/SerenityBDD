 package bdd;

import io.cucumber.junit.CucumberOptions;
import net.serenitybdd.cucumber.CucumberWithSerenity;
import org.junit.runner.RunWith;

@RunWith(CucumberWithSerenity.class)
@CucumberOptions(
//        dryRun = true,
        plugin = {"pretty"},
        features = "classpath:features",
        glue = "bdd.step_definitions",
        tags = "@positive"
)
public class TestRunner {

}
