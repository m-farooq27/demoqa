package com.selenium.tests;

import com.selenium.base.Base;
import com.selenium.pages.LoginPage;

import org.testng.Assert;
import org.testng.annotations.Test;

public class LoginPageTest extends Base {

    @Test
    public void verifyValidLogin() {

        driver.get("https://demoqa.com/login");

        LoginPage loginPage = new LoginPage(driver);

        loginPage.enterUsername("your-username");
        loginPage.enterPassword("your-password");
        loginPage.clickLogin();

        Assert.assertTrue(loginPage.isUserLoggedIn(), "Login failed");
    }
}
