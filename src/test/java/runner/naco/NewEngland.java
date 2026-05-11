package runner.naco;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;
@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/naco/CucumberFeature/NewEngland",
        glue = {"naco.StepDefinitionLatest","naco.hooks"},
        plugin = {
                "progress",
                "html:target/cucumber-report.html",
                "io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm"
        },
        monochrome = true
    
     //   name = {".*, (ME|MA|NH|VT)$"}
)
public class NewEngland {

}
