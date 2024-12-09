package com.majorMedia.BackOfficeDashboard.service.blackBox;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.util.concurrent.TimeUnit;

public class SeleniumConfig {
    private WebDriver driver;
    public SeleniumConfig(){
//        System.setProperty("webdriver.chrome.driver","D:\\SeleniumDriver\\chromedriver.exe");
        WebDriverManager.chromedriver().setup();
        this.driver= new ChromeDriver();
//        this.driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        this.driver.manage().window().maximize();
    }
    public WebDriver getDriver() {
        return driver;
    }

    public void closeDriver() {
        if (driver != null) {
            driver.quit();
        }
    }
}
