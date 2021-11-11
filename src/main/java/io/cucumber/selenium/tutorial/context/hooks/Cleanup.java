package io.cucumber.selenium.tutorial.context.hooks;

import io.cucumber.guice.ScenarioScoped;
import io.cucumber.java.After;
import io.cucumber.java.AfterAll;
import io.cucumber.java.AfterStep;
import io.cucumber.selenium.tutorial.context.BaseScenario;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.logging.LogType;

import java.util.Date;

import static io.cucumber.selenium.tutorial.context.hooks.ScenarioInit.WEB_DRIVERS;

@ScenarioScoped
public class Cleanup extends BaseScenario {

    @AfterStep(value = "@ui", order = 0)
    public void screenshot() {
        TakesScreenshot screenshot = (TakesScreenshot) ScenarioInit.WEB_DRIVER_THREAD_LOCAL.get();
        scenarioUtils.getScenario().attach(screenshot.getScreenshotAs(OutputType.BYTES), "image/png", "screenshot");
    }

    @After(value = "@ui", order = 0)
    public void browserLogs() {
        try {
            LogEntries logs = ScenarioInit.WEB_DRIVER_THREAD_LOCAL.get().manage().logs().get(LogType.BROWSER);
            logs.getAll().forEach(logEntry -> scenarioUtils.log("{} [{}] {}", new Date(logEntry.getTimestamp()),
                    logEntry.getLevel(), logEntry.getMessage()));
        } catch (Exception e) {
            scenarioUtils.log(e.getMessage());
        }
    }

    @AfterAll
    public static void closeWebDrivers() {
        LOG.info("Closing {} webDrivers", WEB_DRIVERS.size());
        WEB_DRIVERS.forEach(WebDriver::quit);
    }
}
