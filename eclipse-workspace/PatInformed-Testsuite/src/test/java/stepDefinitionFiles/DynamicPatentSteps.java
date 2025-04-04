package stepDefinitionFiles;

import com.patinformed.pages.DynamicPatentPage;
import io.cucumber.java.en.*;
import java.util.List;

public class DynamicPatentSteps {
    DynamicPatentPage patentPage;

    @Given("Open the patent information page")
    public void openPatentPage() {
        patentPage = new DynamicPatentPage(Hooks.driver);  // Reuse WebDriver from Hooks
        patentPage.openPage();
    }

    @When("Click on the search button and select the first patent")
    public void selectFirstPatent() {
        patentPage.clickOnDynamicPatent();
    }

    @Then("Calculate the number of days between available dates")
    public void calculateDaysBetweenDates() {
        List<String> filingDates = patentPage.getFilingDates();
        List<String> publicationDates = patentPage.getPublicationDates();
        List<String> grantDates = patentPage.getGrantDates();

      
        patentPage.calculateDaysBetweenDates(filingDates, publicationDates, grantDates);
    }
}
