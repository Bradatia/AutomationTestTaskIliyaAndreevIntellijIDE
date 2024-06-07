package AutomationTestTask.Runner;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(features = {"src/test/resources/features/LoginPage.feature"},
        glue = {"AutomationTestTask.StepDefinitions"},
        plugin = {"pretty", "html:reports/htmlreport.html"})

public class RunnerTests extends AbstractTestNGCucumberTests {
}
