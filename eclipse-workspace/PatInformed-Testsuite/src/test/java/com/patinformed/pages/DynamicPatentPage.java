package com.patinformed.pages;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.JavascriptExecutor;

public class DynamicPatentPage {
    private WebDriver driver;

    // Locators
    private By searchButton = By.xpath("//button[@class='margin-right']");
    private By termsAndConditionsButton = By.xpath("//dialog[@id='modalsHomepage']//button[contains(text(), 'I have read and agree to the terms')]");
    private By dynamicPatentOption = By.xpath("//table[@class='results']//tr[1]//td[3]//ul//li[1]");
    private By publicationDate = By.xpath("//tr[td/b[text()='Publication date']]/td[@class='nobreak']");
    private By filingDate = By.xpath("//tr[td/b[text()='Filing date']]/td/span[@class='nobreak']");
    private By grantDate = By.xpath("//tr[td/b[text()='Grant date']]/td[@class='nobreak']");

    // Constructor
    public DynamicPatentPage(WebDriver driver) {
        this.driver = driver;
    }

    // Navigate to the page
    public void openPage() {
    	System.out.println("Navigating to the patent search page...");
    }

    // Click on Search Button & Select First Patent
    public void clickOnDynamicPatent() {
    	WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    	WebElement searchBtn = wait.until(ExpectedConditions.elementToBeClickable(searchButton));
    	searchBtn.click();

        
        //Accept Terms and Conditions Button
        //WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        try {
            wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loader")));
            System.out.println("Loader disappeared, now looking for the dialog.");
            
            //Wait for the dialog itself to be visible
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("modalsHomepage")));

            WebElement termsAndConditionsBtn = wait.until(ExpectedConditions.elementToBeClickable(termsAndConditionsButton));
            System.out.println("Button Displayed: " + termsAndConditionsBtn.isDisplayed());
            System.out.println("Button Enabled: " + termsAndConditionsBtn.isEnabled());

            termsAndConditionsBtn.click();  

        } catch (Exception e) {
            System.out.println("Terms and Conditions button was not clickable within the expected time.");
            e.printStackTrace();
        }

        // Proceeding to select patent option
        WebElement patentElement = driver.findElement(dynamicPatentOption);
        String patentName = patentElement.getText();
        System.out.println("Selected Patent Name is : " + patentName);
        patentElement.click();
    }

    // Get the publication dates
    public List<String> getPublicationDates() {
        List<String> publicationDateList = new ArrayList<>();
        List<WebElement> publicationDateElements = driver.findElements(publicationDate);

        for (WebElement dateElement : publicationDateElements) {
            String dateText = dateElement.getText().trim();
            if (!dateText.isEmpty()) {  
                publicationDateList.add(dateText);
            }
        }
        return publicationDateList;
    }

    // Get filing dates
    public List<String> getFilingDates() {
        List<String> filingDateList = new ArrayList<>();
        List<WebElement> filingDateElements = driver.findElements(filingDate);

        for (WebElement filingDateElement : filingDateElements) {
            String dateText = filingDateElement.getText().trim();
            if (!dateText.isEmpty()) {  
                filingDateList.add(dateText);
            }
        }
        return filingDateList;
    }

    // Get grant dates
    public List<String> getGrantDates() {
        List<String> grantDateList = new ArrayList<>();
        List<WebElement> grantDateElements = driver.findElements(grantDate);

        for (WebElement grantDateElement : grantDateElements) {
            String dateText = grantDateElement.getText().trim();
            if (!dateText.isEmpty()) {  
                grantDateList.add(dateText);
            }
        }
        return grantDateList;
    }
    
    //Calculation of Days
    public void calculateDaysBetweenDates(List<String> filingDates, List<String> publicationDates, List<String> grantDates) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        for (int i = 0; i < filingDates.size(); i++) {
            try {
                String filingDateStr = filingDates.get(i);
                String publicationDateStr = publicationDates.get(i);
                String grantDateStr = grantDates.get(i);


                // Check if any of these are headers or empty
                if (filingDateStr.equalsIgnoreCase("Filing date") || filingDateStr.isEmpty() ||
                    publicationDateStr.equalsIgnoreCase("Publication date") || publicationDateStr.isEmpty() ||
                    grantDateStr.equalsIgnoreCase("Grant date") || grantDateStr.isEmpty()) {
                    System.out.println("Skipping invalid date entry for Patent " + (i + 1));
                    continue;
                }

                // Extract only the date part (before the space)
                filingDateStr = filingDateStr.split(" ")[0];
                publicationDateStr = publicationDateStr.split(" ")[0];
                grantDateStr = grantDateStr.split(" ")[0];

                // Debugging print statements after extraction
                System.out.println("Extracted Filing Date: " + filingDateStr);
                System.out.println("Extracted Publication Date: " + publicationDateStr);
                System.out.println("Extracted Grant Date: " + grantDateStr);

                // Convert dates
                LocalDate filingDate = !filingDateStr.isEmpty() ? LocalDate.parse(filingDateStr, formatter) : null;
                LocalDate publicationDate = !publicationDateStr.isEmpty() ? LocalDate.parse(publicationDateStr, formatter) : null;
                LocalDate grantDate = !grantDateStr.isEmpty() ? LocalDate.parse(grantDateStr, formatter) : null;

                // Print only one statement based on priority
                if (filingDate != null && publicationDate != null) {
                    long days = ChronoUnit.DAYS.between(filingDate, publicationDate);
                    System.out.println("Patent " + (i + 1) + " - Filing to Publication: " + days + " days");
                    break;
                } else if (filingDate != null && grantDate != null) {
                    long days = ChronoUnit.DAYS.between(filingDate, grantDate);
                    System.out.println("Patent " + (i + 1) + " - Filing to Grant: " + days + " days");
                    
                } else if (publicationDate != null && grantDate != null) {
                    long days = ChronoUnit.DAYS.between(publicationDate, grantDate);
                    System.out.println("Patent " + (i + 1) + " - Publication to Grant: " + days + " days");
                    
                } else {
                    System.out.println("Patent " + (i + 1) + " - No valid date pairs found for calculation.");
                }

            } catch (Exception e) {
                System.out.println("Error processing patent " + (i + 1) + ": " + e.getMessage());
                e.printStackTrace(); // To capture the exact exception
            }
        }
    }


    }


