package io.cucumber.selenium.tutorial.context.hooks;

import io.cucumber.guice.ScenarioScoped;
import io.cucumber.java.After;
import io.cucumber.java.AfterStep;
import io.cucumber.selenium.tutorial.context.BaseScenario;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.logging.LogType;

import java.util.Date;

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
}
