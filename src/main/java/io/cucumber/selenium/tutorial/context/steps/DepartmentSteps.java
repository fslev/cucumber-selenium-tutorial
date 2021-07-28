package io.cucumber.selenium.tutorial.context.steps;

import io.cucumber.guice.ScenarioScoped;
import io.cucumber.java.en.When;
import io.cucumber.selenium.tutorial.context.BaseScenario;
import io.cucumber.selenium.tutorial.context.services.GroceryPage;

import javax.inject.Inject;

@ScenarioScoped
public class DepartmentSteps extends BaseScenario {

    @Inject
    private GroceryPage groceryPage;

    @When("Go to departments tab")
    public void goToDepartmentsTab() {
        groceryPage.getDepartmentsLink().click();
    }
}