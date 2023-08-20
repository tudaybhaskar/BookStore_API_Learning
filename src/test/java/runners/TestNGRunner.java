package runners;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import io.cucumber.testng.TestNGCucumberRunner;

@CucumberOptions(
		features = "src/test/resources/bookStore/features",
		glue = "stepDefinitions",
		plugin = { "pretty","html:target/cucumber-reports","timeline:test-output-thread/" },
		tags = "not @NotRun",
		monochrome = true
		)
public class TestNGRunner extends AbstractTestNGCucumberTests {
	
	

}
