package io.cucumber.selenium.tutorial.context.steps;

import com.google.inject.Inject;
import io.cucumber.guice.ScenarioScoped;
import io.cucumber.java.en.When;
import io.cucumber.selenium.tutorial.context.BaseScenario;
import io.cucumber.selenium.tutorial.context.services.GroceryListContext;
import io.cucumber.selenium.tutorial.context.services.GroceryPage;
import io.jtest.utils.exceptions.PollingTimeoutException;
import io.jtest.utils.polling.Polling;

import java.time.Duration;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;


@ScenarioScoped
public class GroceryListSteps extends BaseScenario {

    @Inject
    private GroceryPage groceryPage;

    @When("Go to grocery list tab")
    public void goToGroceryListTab() {
        groceryPage.getGroceryListLink().click();
    }

    @When("Check grocery list size={}")
    public void checkGroceryListSize(int size) {
        assertEquals(size, groceryPage.getGroceryListContext().getGroceryItemList().size());
    }

    @When("Add grocery item with name={}")
    public void addGroceryItem(String name) {
        groceryPage.getGroceryListContext().addGroceryItem(name);
    }

    @When("Remove grocery item with name={}")
    public void removeGroceryItem(String name) {
        Optional<GroceryListContext.Item> item = groceryPage.getGroceryListContext().getGroceryItemList().stream()
                .filter(el -> el.getName().getText().equals(name)).findAny();
        assertTrue(item.isPresent());
        item.get().getRemoveButton().click();
    }

    @When("Check grocery list contains item with name={}")
    public void checkGroceryListItem(String name) {
        List<GroceryListContext.Item> itemList = groceryPage.getGroceryListContext().getGroceryItemList();
        assertTrue(itemList.stream().anyMatch(item -> item.getName().getText().equals(name)));
    }

    @When("Check grocery list does not contain item with name={}")
    public void checkGroceryListItemDoesNotExist(String name) throws PollingTimeoutException {
//        groceryPage.getGroceryListContext().checkItemDoesNotExist(name);
        assertFalse(new Polling<Boolean>().duration(Duration.ofSeconds(8), 500L)
                .supplier(() -> groceryPage.getGroceryListContext().getGroceryItemList().stream()
                        .anyMatch(item -> item.getName().getText().equals(name))).until(res -> !res).get(), "Item still exists");
    }
}