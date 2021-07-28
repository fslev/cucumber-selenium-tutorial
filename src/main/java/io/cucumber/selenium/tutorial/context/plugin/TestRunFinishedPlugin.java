package io.cucumber.selenium.tutorial.context.plugin;

import io.cucumber.plugin.ConcurrentEventListener;
import io.cucumber.plugin.event.EventPublisher;
import io.cucumber.plugin.event.TestRunFinished;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;

import static io.cucumber.selenium.tutorial.context.hooks.ScenarioInit.WEB_DRIVERS;

public class TestRunFinishedPlugin implements ConcurrentEventListener {
    protected static final Logger LOG = LogManager.getLogger();

    @Override
    public void setEventPublisher(EventPublisher eventPublisher) {
        eventPublisher.registerHandlerFor(TestRunFinished.class, e -> {
            LOG.info("Received Cucumber TestRunFinished event. Closing {} webDrivers", WEB_DRIVERS.size());
            WEB_DRIVERS.forEach(WebDriver::quit);
        });
    }
}
