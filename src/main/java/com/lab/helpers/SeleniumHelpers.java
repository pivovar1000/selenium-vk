package com.lab.helpers;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;


import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;
import static org.openqa.selenium.OutputType.FILE;

public class SeleniumHelpers {
    private static final String PATH_RESOURCES = "src/main/resources/";


    public static WebElement explicitWaiting(WebDriver driver, By by, int timeout) {
        WebDriverWait explicitWaiting = new WebDriverWait(driver, timeout);
        return explicitWaiting.until(ExpectedConditions.visibilityOfElementLocated(by));
    }

    @SuppressWarnings("ConstantConditions")
    public static WebElement clickDynamicElement(WebDriver driver, By by) {
        WebElement webElement = driver.findElement(by);
        try {
            webElement.click();
            return webElement;
        } catch (StaleElementReferenceException ignore) {
            webElement = (new WebDriverWait(driver, 10))
                    .until((ExpectedCondition<WebElement>) d -> d.findElement(by));
            webElement.click();
            return webElement;
        }
    }

    @SuppressWarnings("ConstantConditions")
    public static WebElement sendDynamicElement(WebDriver driver, By by, String text) {
        WebElement webElement = driver.findElement(by);
        try {
            webElement.sendKeys(text);
            return webElement;
        } catch (StaleElementReferenceException ignore) {
            webElement = (new WebDriverWait(driver, 10))
                    .until((ExpectedCondition<WebElement>) d -> d.findElement(by));
            webElement.sendKeys(text);
            return webElement;
        }
    }

    public static void takesScreenshot(WebDriver driver, String fileName) {
        try {
            File screenshot = ((TakesScreenshot) driver).getScreenshotAs(FILE);
            File target = new File(PATH_RESOURCES + "screenshots/" + fileName + ".png");
            Files.copy(Paths.get(screenshot.toURI()), Paths.get(target.toURI()), REPLACE_EXISTING);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
