package runner.naco;


import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/naco/CucumberFeature/Pacific",
        glue = {"naco.StepDefinitionLatest","naco.hooks"},
        plugin = {
                "progress",
                "html:target/cucumber-report.html",
                "io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm"
        },
        monochrome = true
     //   name = {"County_(DE|FL|GA|MD|NC|SC|VA|WV)"}
)


public class SouthAtlanicRunner {

}
