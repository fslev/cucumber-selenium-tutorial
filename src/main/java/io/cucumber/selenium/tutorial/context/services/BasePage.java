package io.cucumber.selenium.tutorial.context.services;

import io.cucumber.selenium.tutorial.context.hooks.ScenarioInit;
import io.selenium.utils.ElementContextLocatorFactory;
import io.selenium.utils.FieldContextDecorator;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

import java.time.Duration;
import java.util.Collections;

public abstract class BasePage {

    protected WebDriver driver;

    protected BasePage() {
        this(ScenarioInit.WEB_DRIVER_THREAD_LOCAL.get());
    }

    protected BasePage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(new FieldContextDecorator(new ElementContextLocatorFactory(driver, Duration.ofSeconds(10),
                Collections.singletonList(StaleElementReferenceException.class))), this);
    }

    public WebDriver getDriver() {
        return driver;
    }
}
