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

public class ProfileSettings {
    private WebDriver driver;
    private SeleniumConfig seleniumConfig;
    @BeforeEach
    public void setUp(){
        seleniumConfig = new SeleniumConfig();
        driver = seleniumConfig.getDriver();
        try{
            login();
            Thread.sleep(5000);
        }
        catch(InterruptedException ex){
            ex.printStackTrace();
        }
        finally {
            driver.get("http://localhost:3000/profile");
        }

    }
    @AfterEach
    public void tearDown() {
        // Close the browser after each test
        if (driver != null) {
            driver.quit();
        }
    }
    @Test
    public void changePasswordSuccess(){
        try{
            Thread.sleep(5000);
            WebElement oldPasswordField = driver.findElement(By.name("oldPassword"));
            WebElement passwordField = driver.findElement(By.name("password"));
            WebElement confirmedPassword = driver.findElement(By.name("confirmedPassword"));
            WebElement submitButton = driver.findElement(By.xpath("//button[text()='Change Password']"));
            oldPasswordField.sendKeys("NewPass123");
            passwordField.sendKeys("NewPass123");
            confirmedPassword.sendKeys("NewPass123");
            submitButton.click();
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofMinutes(2));
            WebElement toastMessage = wait.until(
                    ExpectedConditions.presenceOfElementLocated(
                            By.xpath(".//div[text()='Password changed successfully']")
                    ));
            // Locate the specific toast message body
            assertEquals("Password changed successfully", toastMessage.getText(), "The error message is incorrect.");

        }
        catch(InterruptedException ex){
            ex.printStackTrace();
        }
    }
    @Test
    public void changePasswordError(){
        try{
            Thread.sleep(5000);
            WebElement oldPasswordField = driver.findElement(By.name("oldPassword"));
            WebElement passwordField = driver.findElement(By.name("password"));
            WebElement confirmedPassword = driver.findElement(By.name("confirmedPassword"));
            WebElement submitButton = driver.findElement(By.xpath("//button[text()='Change Password']"));
            oldPasswordField.sendKeys("NewPass123");
            passwordField.sendKeys("NewPass");
            confirmedPassword.sendKeys("NewPass");
            submitButton.click();
            Thread.sleep(3000);
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofMinutes(2));
            WebElement toastMessage = wait.until(
                    ExpectedConditions.presenceOfElementLocated(
                            By.xpath(".//div[contains(text(),'Password must be')]")
                    ));

            assertTrue(toastMessage.getText().contains("Password must be"), "The error message is incorrect.");


        }
        catch(InterruptedException ex){
            ex.printStackTrace();
        }
    }
    private void login(){
        driver.get("http://localhost:3000/login");
        WebElement emailField = driver.findElement(By.name("email"));
        WebElement passwordField = driver.findElement(By.name("password"));
        WebElement submitButton = driver.findElement(By.xpath("//button[text()='Log in']"));
        emailField.sendKeys("ilias.rouchdi21@gmail.com");
        passwordField.sendKeys("NewPass123");
        submitButton.click();

    }
}
