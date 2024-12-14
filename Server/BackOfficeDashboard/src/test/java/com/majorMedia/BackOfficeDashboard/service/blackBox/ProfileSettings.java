package com.majorMedia.BackOfficeDashboard.service.blackBox;

import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ProfileSettings {
    private static WebDriver driver;
    private static SeleniumConfig seleniumConfig;
    private static String currentPassword ="ff";
    private static String newPassword="NewPass123";

    @BeforeAll
    public static void setUp() {
        seleniumConfig = new SeleniumConfig();
        driver = seleniumConfig.getDriver();

        // Initialize passwords


        try {
            login();
            Thread.sleep(5000);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        } finally {
            driver.get("http://localhost:3000/profile");
        }
    }
    @AfterAll
    public static void tearDown() {
        // Close the browser after each test
        if (driver != null) {
            driver.quit();
        }
    }
    @Test
    public void changePasswordSuccess() {
        try {
            Thread.sleep(5000);
            WebElement oldPasswordField = driver.findElement(By.name("oldPassword"));
            WebElement passwordField = driver.findElement(By.name("password"));
            WebElement confirmedPassword = driver.findElement(By.name("confirmedPassword"));
            WebElement submitButton = driver.findElement(By.xpath("//button[text()='Change Password']"));

            oldPasswordField.sendKeys(currentPassword);
            passwordField.sendKeys(newPassword);
            confirmedPassword.sendKeys(newPassword);
            submitButton.click();

            Thread.sleep(1000);
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofMinutes(2));
            WebElement toastMessage = wait.until(
                    ExpectedConditions.presenceOfElementLocated(
                            By.xpath(".//div[text()='Password changed successfully']")
                    ));

            assertEquals("Password changed successfully", toastMessage.getText(), "The success message is incorrect.");
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }finally{
            currentPassword=newPassword;
        }
    }

    @Test
    public void changePasswordError() {
        try {
            Thread.sleep(5000);
            WebElement oldPasswordField = driver.findElement(By.name("oldPassword"));
            WebElement passwordField = driver.findElement(By.name("password"));
            WebElement confirmedPassword = driver.findElement(By.name("confirmedPassword"));
            WebElement submitButton = driver.findElement(By.xpath("//button[text()='Change Password']"));

            oldPasswordField.sendKeys(currentPassword);
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
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }
    private static void login() {
        driver.get("http://localhost:3000/login");
        WebElement emailField = driver.findElement(By.name("email"));
        WebElement passwordField = driver.findElement(By.name("password"));
        WebElement submitButton = driver.findElement(By.xpath("//button[text()='Log in']"));

        emailField.sendKeys("ilias.rouchdi21@gmail.com");
        passwordField.sendKeys(currentPassword);
        submitButton.click();
    }
}
