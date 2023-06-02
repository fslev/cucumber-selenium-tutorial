package io.cucumber.selenium.tutorial.context.steps;

import com.google.inject.Inject;
import io.cucumber.guice.ScenarioScoped;
import io.cucumber.java.en.When;
import io.cucumber.selenium.tutorial.context.BaseScenario;
import io.cucumber.selenium.tutorial.context.services.DepartmentsContext;
import io.cucumber.selenium.tutorial.context.services.GroceryPage;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ScenarioScoped
public class DepartmentSteps extends BaseScenario {

    @Inject
    private GroceryPage groceryPage;

    @When("Go to departments tab")
    public void goToDepartmentsTab() {
        groceryPage.getDepartmentsLink().click();
    }

    @When("Add department with name={}")
    public void addDepartment(String name) {
        groceryPage.getDepartmentsContext().addDepartment(name);
    }

    @When("Check department list size={}")
    public void checkDepartmentListSize(int size) {
        assertEquals(size, groceryPage.getDepartmentsContext().getDepartmentList().size());
    }

    @When("Remove department with name={}")
    public void removeDepartment(String name) {
        Optional<DepartmentsContext.Department> department = groceryPage.getDepartmentsContext().getDepartmentList().stream()
                .filter(d -> d.getName().getText().contains(name)).findFirst();
        assertTrue(department.isPresent());
        department.get().getRemoveButton().click();
    }
}