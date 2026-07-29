package runner.naco;
import org.junit.runner.RunWith;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)
@CucumberOptions(
	    features = {
	        "src/test/resources/naco/CucumberFeature/MidAtlantic/County_NJ.feature",
	        "src/test/resources/naco/CucumberFeature/MidWestEastCentral/County_IN.feature",
	        "src/test/resources/naco/CucumberFeature/Mountain/County_ID.feature",
	        "src/test/resources/naco/CucumberFeature/SouthCentral/County_TX.feature"
	    },
	    glue = {"naco.StepDefinitionLatest", "naco.hooks"},
	    plugin = {"pretty", "io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm"},
	    monochrome = true)
public class RunnerLatest {
	
}

