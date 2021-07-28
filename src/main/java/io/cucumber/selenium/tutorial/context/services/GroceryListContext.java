package io.cucumber.selenium.tutorial.context.services;

import io.cucumber.selenium.tutorial.context.hooks.ScenarioInit;
import io.selenium.utils.WebContext;
import lombok.Getter;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class GroceryListContext extends WebContext {

    @FindBy(xpath = ".//input[@type='text']")
    private WebElement groceryInput;

    @FindBy(xpath = ".//button[text()='Add']")
    private WebElement groceryAddButton;

    @FindBy(xpath = ".//ul/li")
    @Getter
    private List<Item> groceryItemList;

    public void addGroceryItem(String name) {
        groceryInput.clear();
        groceryInput.sendKeys(name);
        groceryAddButton.click();
    }

    public void checkItemDoesNotExist(WebDriver driver, String name) {
        driver.manage().timeouts().implicitlyWait(Duration.ZERO);
        new WebDriverWait(driver, Duration.ofSeconds(8)).until(
                ExpectedConditions.invisibilityOfElementLocated(By.xpath("//ul//li//app-item/span[text()='" + name + "']")));
        driver.manage().timeouts().implicitlyWait(ScenarioInit.IMPLICIT_WAIT);
    }

    @Getter
    public static class Item extends WebContext {
        @FindBy(xpath = ".//app-item/span")
        private WebElement name;

        @FindBy(xpath = ".//app-item/button[text()='Remove']")
        private WebElement removeButton;

    }
}
