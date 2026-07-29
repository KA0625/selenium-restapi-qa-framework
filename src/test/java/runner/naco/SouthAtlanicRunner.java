package runner.naco;


import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;


@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/naco/CucumberFeature/SouthAtlantic/County_SC.feature",
        glue = {"naco.StepDefinitionLatest","naco.hooks"},
     tags="not @BootStrap",
        		plugin = { "pretty",
        		        "io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm"
        			},
        monochrome = true
)


public class SouthAtlanicRunner {

}
