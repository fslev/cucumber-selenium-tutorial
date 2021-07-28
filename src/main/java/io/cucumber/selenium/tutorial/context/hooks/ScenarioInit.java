package io.cucumber.selenium.tutorial.context.hooks;

import io.cucumber.guice.ScenarioScoped;
import io.cucumber.java.Before;
import io.cucumber.java.PendingException;
import io.cucumber.selenium.tutorial.Config;
import io.cucumber.selenium.tutorial.context.BaseScenario;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static io.cucumber.selenium.tutorial.Config.IMPLICIT_WAIT;

@ScenarioScoped
public class ScenarioInit extends BaseScenario {

    public static final Set<WebDriver> WEB_DRIVERS = Collections.synchronizedSet(new HashSet<>());
    public static final ThreadLocal<WebDriver> WEB_DRIVER_THREAD_LOCAL = new ThreadLocal<>();

    @Before(order = 0)
    public void liveSafety() {
        if (Config.isProdEnv() && !scenarioUtils.getScenario().getSourceTagNames().contains("@prod")) {
            throw new PendingException("LIVE safety exception: I am not running scenarios on LIVE environment without '@prod' tag");
        }
    }

    @Before(order = 1)
    public void initScenarioPropsWithConfigProperties() {
        try {
            scenarioProps.putAll((Map) Config.PROPERTIES);
        } catch (Throwable t) {
            scenarioUtils.log("Cannot initialise scenario properties from config properties");
            throw t;
        }
    }

    @Before(value = "@ui", order = 2)
    public void initWebDriver() throws MalformedURLException {
        WebDriver driver = WEB_DRIVER_THREAD_LOCAL.get();
        scenarioUtils.log("Using {} web driver", Config.BROWSER);
        if (driver == null) {
            scenarioUtils.log("Initialising web driver...");
            driver = Config.BROWSER.equals("chrome") ? getChromeWebDriver() : getFirefoxWebDriver();
            WEB_DRIVER_THREAD_LOCAL.set(driver);
            WEB_DRIVERS.add(driver);
        } else {
            scenarioUtils.log("Web driver already initialised for current thread context. Refresh address");
        }
        driver.manage().timeouts().implicitlyWait(IMPLICIT_WAIT);
        driver.get(Config.GROCERY_WEBSITE_ADDRESS);
    }

    private static WebDriver getFirefoxWebDriver() throws MalformedURLException {
        FirefoxOptions options = new FirefoxOptions();
        return new RemoteWebDriver(new URL(Config.SELENIUM_HUB_ADDRESS), options);
    }

    private static WebDriver getChromeWebDriver() throws MalformedURLException {
        ChromeOptions options = new ChromeOptions();
        return new RemoteWebDriver(new URL(Config.SELENIUM_HUB_ADDRESS), options);
    }
}
