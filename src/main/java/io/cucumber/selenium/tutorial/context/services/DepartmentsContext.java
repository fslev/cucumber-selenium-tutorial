package io.cucumber.selenium.tutorial.context.services;

import io.selenium.utils.WebContext;
import lombok.Getter;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class DepartmentsContext extends WebContext {

    @FindBy(xpath = ".//input[@type='text']")
    private WebElement departmentInput;

    @FindBy(xpath = ".//button[text()='Add']")
    private WebElement departmentAddButton;

    @FindBy(xpath = ".//ul/li")
    @Getter
    private List<Department> departmentList;

    public void addDepartment(String name) {
        departmentInput.clear();
        departmentInput.sendKeys(name);
        departmentAddButton.click();
    }

    @Getter
    public static class Department extends WebContext {
        @FindBy(xpath = ".//app-department")
        private WebElement name;

        @FindBy(xpath = ".//app-department/button[text()='Remove']")
        private WebElement removeButton;
    }
}
