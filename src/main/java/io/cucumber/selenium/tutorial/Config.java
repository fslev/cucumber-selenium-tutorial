package io.cucumber.selenium.tutorial;

import io.jtest.utils.common.ResourceUtils;

import java.io.IOException;
import java.util.Properties;

public class Config {

    public static final Properties PROPERTIES = getLocalConfig();
    public static final String ENV = PROPERTIES.getProperty("env");

    public static final String BROWSER = System.getProperty("browser.type", "chrome");

    public static String SELENIUM_HUB_ADDRESS = PROPERTIES.getProperty("selenium.hub.address");
    public static String GROCERY_WEBSITE_ADDRESS = PROPERTIES.getProperty("grocery.website.address");

    public static boolean isLocalEnv() {
        return ENV.equals("local");
    }

    public static boolean isProdEnv() {
        return ENV.equals("prod");
    }

    private static Properties getLocalConfig() {
        try {
            return ResourceUtils.readProps("env.properties");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}


