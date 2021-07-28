package io.cucumber.selenium.tutorial.context.services;

import io.cucumber.guice.ScenarioScoped;
import lombok.Getter;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

@ScenarioScoped
@Getter
public class GroceryPage extends BasePage {

    @FindBy(xpath = "//app-root/a[text()='List']")
    private WebElement groceryListLink;

    @FindBy(xpath = "//app-root/a[text()='Departments']")
    private WebElement departmentsLink;

    @FindBy(xpath = "//app-root//app-list")
    private GroceryListContext groceryListContext;

    @FindBy(xpath = "//app-root//app-departments")
    private GroceryListContext departmentsContext;
}
