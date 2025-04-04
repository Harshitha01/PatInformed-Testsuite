package runnerFiles;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
    features = "src/test/resources/Features",
    glue = "stepDefinitionFiles",
    plugin = {"pretty","html:target/cucumber-html-report"},
    monochrome = true
)
public class TestRunner {
}
