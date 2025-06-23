package com.selenium.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class HomePage {

    private static final Logger log = LogManager.getLogger(HomePage.class);

    private WebDriver driver;

    // Constructor: initializes elements using PageFactory
    public HomePage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
        log.info("HomePage initialized with PageFactory");
    }

    // Locators using @FindBy
    @FindBy(xpath = "//*[@id=\"app\"]/header/a/img") private WebElement logo; 

    @FindBy(xpath = "//*[@id=\"fixedban\"]/div/div[1]") private WebElement footer; 

    @FindBy (xpath = "//*[@id=\"app\"]/div/div/div/div[1]/div/div/div[1]/span/div") private WebElement elementsCard;

    @FindBy(xpath = "//*[@id=\"app\"]/div/div/div/div[1]/div/div/div[2]/span/div") private WebElement formsCard;

    @FindBy(xpath = "//*[@id=\"app\"]/div/div/div/div[1]/div/div/div[3]/span/div") private WebElement alertsCard;

    @FindBy(xpath = "//*[@id=\"app\"]/div/div/div/div[1]/div/div/div[4]/span/div") private WebElement widgetsCard;

    @FindBy(xpath = "//*[@id=\"app\"]/div/div/div/div[1]/div/div/div[5]/span/div") private WebElement interactionsCard;

    @FindBy(xpath = "//*[@id=\"app\"]/div/div/div/div[1]/div/div/div[6]/span/div") private WebElement bookStoreCard;

    // Page Actions

    public boolean isLogoVisible() {
        boolean visible = logo.isDisplayed();
        log.info("Logo visible: {}", visible);
        return visible;
    }

    public boolean isFooterVisible() {
        boolean visible = footer.isDisplayed();
        log.info("Footer visible: {}", visible);
        return visible;
    }

    public boolean isCardVisible(String cardName) {
        log.info("Checking card visibility: {}", cardName);
        switch (cardName.toLowerCase()) {
            case "elements":
                return elementsCard.isDisplayed();
            case "forms":
                return formsCard.isDisplayed();
            case "alerts":
                return alertsCard.isDisplayed();
            case "widgets":
                return widgetsCard.isDisplayed();
            case "interactions":
                return interactionsCard.isDisplayed();
            case "book store":
                return bookStoreCard.isDisplayed();
            default:
                log.error("Invalid card name: {}", cardName);
                throw new IllegalArgumentException("Unknown card name: " + cardName);
        }
    }

    public void clickCard(String cardName) {
        log.info("Clicking card: {}", cardName);
        switch (cardName.toLowerCase()) {
            case "elements":
                elementsCard.click();
                break;
            case "forms":
                formsCard.click();
                break;
            case "alerts":
                alertsCard.click();
                break;
            case "widgets":
                widgetsCard.click();
                break;
            case "interactions":
                interactionsCard.click();
                break;
            case "book store":
                bookStoreCard.click();
                break;
            default:
                log.error("Invalid card name clicked: {}", cardName);
                throw new IllegalArgumentException("Unknown card name: " + cardName);
        }
    }

    public String getHomePageTitle() {
        String title = driver.getTitle();
        log.info("Page title: {}", title);
        return title;
    }

    public boolean isHomePageLoaded() {
        boolean loaded = isLogoVisible() && isFooterVisible();
        log.info("Home page loaded status: {}", loaded);
        return loaded;
    }

}
