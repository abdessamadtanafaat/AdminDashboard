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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class LoginTest {
    private WebDriver driver;
    private SeleniumConfig seleniumConfig;
    @BeforeEach
    public void setUp(){
        seleniumConfig = new SeleniumConfig();
        driver = seleniumConfig.getDriver();
        driver.get("http://localhost:3000/login");
    }
    @AfterEach
    public void tearDown() {
        // Close the browser after each test
        if (driver != null) {
            driver.quit();
        }
    }
    @Test
    public void testLoginSuccess() {
        // Locate and fill out form fields
        WebElement emailField = driver.findElement(By.name("email"));
        WebElement passwordField = driver.findElement(By.name("password"));
        WebElement submitButton = driver.findElement(By.xpath("//button[text()='Log in']"));


        emailField.sendKeys("ilias.rouchdi21@gmail.com");
        passwordField.sendKeys("ff");
        submitButton.click();
        String initialUrl = driver.getCurrentUrl();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofMinutes(2));

        // Wait for the URL to change
        wait.until(ExpectedConditions.not(ExpectedConditions.urlToBe(initialUrl)));

        // Then, wait for a specific element to load on the redirected page
//        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("")));
        // Wait for redirection (use explicit waits for dynamic elements in production)
        String currentUrl = driver.getCurrentUrl();
        assertEquals("http://localhost:3000/", currentUrl);

        // Verify user is redirected to the dashboard or homepage
    }

    @Test
    public void testLoginInvalidPassword() {

        // Locate and fill out form fields with invalid credentials
        WebElement emailField = driver.findElement(By.name("email"));
        WebElement passwordField = driver.findElement(By.name("password"));
        WebElement submitButton = driver.findElement(By.xpath("//button[text()='Log in']"));

        emailField.sendKeys("ilias.rouchdi21@gmail.com");
        passwordField.sendKeys("wrongPassword");
        submitButton.click();

        // Wait for the toast notification to appear dynamically
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement toastMessage = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(".//div[text()='You provided an incorrect password']")));
        // Locate the specific toast message body

        assertEquals("You provided an incorrect password", toastMessage.getText(), "The error message is incorrect.");

    }
    @Test
    public void testLoginUnknownEmail(){
        WebElement emailField = driver.findElement(By.name("email"));
        WebElement passwordField = driver.findElement(By.name("password"));
        WebElement submitButton = driver.findElement(By.xpath("//button[text()='Log in']"));
        String email = "ilias.rouchdi100@gmail.com";
        emailField.sendKeys(email);
        passwordField.sendKeys("wrongPassword");
        submitButton.click();
        String toastXPath = String.format(".//div[text()='The %s not found']", email);

        // Wait for the toast message to appear
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofMinutes(2));
        WebElement toastMessage = wait.until(
                ExpectedConditions.presenceOfElementLocated(
                        By.xpath(".//div[contains(text(),'not found')]")
                )
        );
        System.out.println(toastMessage.getText());
        assertTrue(toastMessage.getText().contains("not found"), "The error message is incorrect.");

    }
}
