package runner.naco;


import org.junit.runner.RunWith;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)
@CucumberOptions(
      //  features = "src/test/resources/naco/CucumberFeature",
        glue = {"naco.StepDefinitionLatest","naco.hooks"},
       plugin = {
                "progress",
                "html:target/cucumber-report.html",
                "json:target/jsonReports/cucumber-report.json",
                "io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm"
            },
                
        
        monochrome = true)
        		

public class MasterRunner {
	
}
