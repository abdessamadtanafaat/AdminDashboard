package com.majorMedia.BackOfficeDashboard.service.blackBox;

import org.junit.Before;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ResetPasswordTest {
    private SeleniumConfig seleniumConfig;
    private WebDriver driver;
    @BeforeEach
    public void beforeEach(){
        seleniumConfig = new SeleniumConfig();
        driver = seleniumConfig.getDriver();

        // Navigate to the Reset Password page

    }
    @AfterEach
    public void tearDown() {

        seleniumConfig.closeDriver();
    }
    @Test
    public void testRestPasswordSuccess(){
    }

}
