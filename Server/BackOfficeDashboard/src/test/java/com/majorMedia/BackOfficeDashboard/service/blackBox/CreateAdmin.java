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

public class CreateAdmin {
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
        catch(InterruptedException ex){ex.printStackTrace();}
        finally {driver.get("http://localhost:3000/admin/create-admin");}
    }
    private void login(){
        driver.get("http://localhost:3000/login");
        WebElement emailField = driver.findElement(By.name("email"));
        WebElement passwordField = driver.findElement(By.name("password"));
        WebElement submitButton = driver.findElement(By.xpath("//button[text()='Log in']"));
        emailField.sendKeys("ilias.rouchdi21@gmail.com");
        passwordField.sendKeys("ff");
        submitButton.click();
    }
    @AfterEach
    public void tearDown() {
        // Close the browser after each test
        if (driver != null) {
            driver.quit();
        }
    }
    @Test
    public void createAdminSuccess(){
        try{Thread.sleep(5000);
            WebElement emailFieldCreate = driver.findElement(By.name("email"));
            WebElement firstnameFieldCreate = driver.findElement(By.name("firstname"));
            WebElement lastnameFieldCreate = driver.findElement(By.name("lastname"));
            WebElement submitButton = driver.findElement(By.xpath(
                    "//button[text()='Create']"));
            emailFieldCreate.sendKeys("admin@admin.com");
            firstnameFieldCreate.sendKeys("admin");
            lastnameFieldCreate.sendKeys("admin");
            submitButton.click();Thread.sleep(500);
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofMinutes(2));
            WebElement toastMessage = wait.until(
                    ExpectedConditions.presenceOfElementLocated(By.xpath(".//div[text()='Admin Created Successfully']")));
            assertEquals("Admin Created Successfully", toastMessage.getText(), "The error message is incorrect.");
            String currentUrl = driver.getCurrentUrl();
            assertEquals("http://localhost:3000/admins", currentUrl);
        }
        catch(InterruptedException ex){
            ex.printStackTrace();
        }
    }
    @Test
    public void createAdminAlreadyExistError(){
        try{Thread.sleep(5000);
            WebElement emailFieldCreate = driver.findElement(By.name("email"));
            WebElement firstnameFieldCreate = driver.findElement(By.name("firstname"));
            WebElement lastnameFieldCreate = driver.findElement(By.name("lastname"));
            WebElement submitButton = driver.findElement(By.xpath("//button[text()='Create']"));
            emailFieldCreate.sendKeys("admin@admin.com");
            firstnameFieldCreate.sendKeys("admin");
            lastnameFieldCreate.sendKeys("admin");
            submitButton.click();Thread.sleep(500);
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofMinutes(2));
            WebElement toastMessage = wait.until(
                    ExpectedConditions.presenceOfElementLocated(By.xpath(".//div[contains(text(),'already exists')]")));            // Locate the specific toast message body
            assertTrue(toastMessage.getText().contains("already exists"), "The error message is incorrect.");
            String currentUrl = driver.getCurrentUrl();
            assertEquals("http://localhost:3000/admin/create-admin", currentUrl);
        }
        catch(InterruptedException ex){
            ex.printStackTrace();
        }
    }



}
