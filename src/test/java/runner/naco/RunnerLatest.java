package runner.naco;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

import org.junit.BeforeClass;
import org.junit.runner.RunWith;

import featuregenrator.naco.DynamicFeatureGenerator;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/naco/CucumberFeature",
        glue = {"naco.StepDefinitionLatest","naco.hooks"},
     tags="not @BootStrap",
        		plugin = {"progress",
        			    "html:target/cucumber-report.html",
        			    "io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm"
        			},
        monochrome = true
)
public class RunnerLatest {
	
}
