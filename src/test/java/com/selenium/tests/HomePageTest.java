package com.selenium.tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.selenium.base.Base;
import com.selenium.pages.HomePage;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class HomePageTest extends Base {

    private static final Logger log = LogManager.getLogger(HomePageTest.class);

    @Test
    public void verifyHomePageLoadsSuccessfully(){
        log.info("Starting Home Page load verification test.");

        HomePage homePage = new HomePage(driver);

        Assert.assertTrue(homePage.isHomePageLoaded(), "Home page did not load correctly.");
        log.info("Home page loaded successfully.");
    }

    @Test
    public void verifyElementsCardIsVisibleAndClickable(){
        log.info("Starting test for visibility and clickability of 'Elements' card.");

        HomePage homePage = new HomePage(driver);

        Assert.assertTrue(homePage.isCardVisible("elements"),"'Elements' card not visible.");
        log.info("'Elements' card is visible.");

        homePage.clickCard("elements");
        log.info("Clicked on 'Elements' card.");
    }

}
