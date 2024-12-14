package com.majorMedia.BackOfficeDashboard.service.blackBox;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

public class ForgotPasswordTest {

    private WebDriver driver;
    private SeleniumConfig seleniumConfig;
    @BeforeEach
    public void setUp(){
        seleniumConfig = new SeleniumConfig();
        driver = seleniumConfig.getDriver();
        driver.get("http://localhost:3000/forgotPassword");
    }
    @AfterEach
    public void tearDown() {
        // Close the browser after each test
        if (driver != null) {
            driver.quit();
        }
    }
    @Test
    public void sendEmailSuccess(){
        WebElement emailField = driver.findElement(By.name("email"));
        WebElement submitButton = driver.findElement(By.xpath(
                "//button[text()='Submit']"));
        String email ="ilias.rouchdi21@gmail.com";
        emailField.sendKeys(email);
        submitButton.click();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofMinutes(10));
        WebElement toastMessage = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(".//div[text()='Please check your mail to reset your password!']")));
        assertEquals("Please check your mail to reset your password!", toastMessage.getText(), "The error message is incorrect.");
        String currentUrl = driver.getCurrentUrl();
        assertEquals("http://localhost:3000/login", currentUrl);
    }
    @Test
    public void sendEmailNoFoundError(){
        WebElement emailField = driver.findElement(By.name("email"));
        WebElement submitButton = driver.findElement(By.xpath(
                "//button[text()='Submit']"));
        String email ="ilias.rouchdi100@gmail.com";
        emailField.sendKeys(email);
        submitButton.click();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofMinutes(10));
        WebElement toastMessage = wait.until(
                ExpectedConditions.presenceOfElementLocated(
                        By.xpath(".//div[contains(text(),'not found')]")
                )
        );
        assertTrue(toastMessage.getText().contains("not found"),
                "The error message is incorrect.");
        String currentUrl = driver.getCurrentUrl();
        assertNotEquals("http://localhost:3000/login", currentUrl);
    }
}
