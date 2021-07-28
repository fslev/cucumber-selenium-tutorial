
package io.cucumber.selenium.tutorial;

import io.cucumber.testng.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.ITest;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;

@CucumberOptions(features = "src/test/resources/features",
        glue = {"com.cucumber.utils", "io.cucumber.selenium.tutorial.context"},
        plugin = {"json:target/cucumber-report/report.json"}, tags = "not @Ignore and not @ignore")
public class CucumberSeleniumTest implements ITest {
    private static final Logger LOG = LogManager.getLogger();

    private static final Predicate<Pickle> isSerialGroup1 = pickle -> pickle.getTags().contains("@serial_group1");
    private static final Predicate<Pickle> isSerialGroup2 = pickle -> pickle.getTags().contains("@serial_group2");
    private static final Predicate<Pickle> isSerialGroup3 = pickle -> pickle.getTags().contains("@serial_group3");

    private final ThreadLocal<String> testName = new ThreadLocal<>();
    private TestNGCucumberRunner testNGCucumberRunner;

    private final AtomicInteger totalTestCount = new AtomicInteger();

    @BeforeClass(alwaysRun = true)
    public void setUpClass() {
        testNGCucumberRunner = new TestNGCucumberRunner(this.getClass());
    }

    @Test(groups = "cucumber", description = "Runs Cucumber Parallel Scenarios", dataProvider = "parallelScenarios")
    public void runParallelScenario(PickleWrapper pickleWrapper, FeatureWrapper featureWrapper) {
        LOG.info("Running parallel scenario: [{}]", pickleWrapper.getPickle().getName());
        testNGCucumberRunner.runScenario(pickleWrapper.getPickle());
    }

    @Test(groups = "cucumber", description = "Runs Cucumber Serial Scenarios from Group 1", dataProvider = "serialGroup1Scenarios")
    public void runSerialGroup1Scenario(PickleWrapper pickleWrapper, FeatureWrapper featureWrapper) {
        LOG.info("Running scenario from Serial Group 1: [{}]", pickleWrapper.getPickle().getName());
        testNGCucumberRunner.runScenario(pickleWrapper.getPickle());
    }

    @Test(groups = "cucumber", description = "Runs Cucumber Serial Scenarios from Group 2", dataProvider = "serialGroup2Scenarios")
    public void runSerialGroup2Scenario(PickleWrapper pickleWrapper, FeatureWrapper featureWrapper) {
        LOG.info("Running scenario from Serial Group 2: [{}]", pickleWrapper.getPickle().getName());
        testNGCucumberRunner.runScenario(pickleWrapper.getPickle());
    }

    @Test(groups = "cucumber", description = "Runs Cucumber Serial Scenarios from Group 3", dataProvider = "serialGroup3Scenarios")
    public void runSerialGroup3Scenario(PickleWrapper pickleWrapper, FeatureWrapper featureWrapper) {
        LOG.info("Running scenario from Serial Group 3: [{}]", pickleWrapper.getPickle().getName());
        testNGCucumberRunner.runScenario(pickleWrapper.getPickle());
    }

    @DataProvider(parallel = true)
    public Object[][] parallelScenarios() {
        return dataProvider(isSerialGroup1.negate().and(isSerialGroup2.negate()).and(isSerialGroup3.negate()));
    }

    @DataProvider
    public Object[][] serialGroup1Scenarios() {
        return dataProvider(isSerialGroup1);
    }

    @DataProvider
    public Object[][] serialGroup2Scenarios() {
        return dataProvider(isSerialGroup2);
    }

    @DataProvider
    public Object[][] serialGroup3Scenarios() {
        return dataProvider(isSerialGroup3);
    }

    @AfterClass(alwaysRun = true)
    public void tearDownClass() {
        if (testNGCucumberRunner == null) {
            return;
        }
        testNGCucumberRunner.finish();
    }

    private Object[][] dataProvider(Predicate<Pickle> accept) {
        if (testNGCucumberRunner == null) {
            totalTestCount.set(1);
            return new Object[0][0];
        }
        Object[][] scenarios = testNGCucumberRunner.provideScenarios();
        totalTestCount.set(scenarios.length);
        return filter(scenarios, accept);
    }

    private static Object[][] filter(Object[][] scenarios, Predicate<Pickle> accept) {
        return Arrays.stream(scenarios).filter(objects -> {
            PickleWrapper candidate = (PickleWrapper) objects[0];
            return accept.test(candidate.getPickle());
        }).toArray(Object[][]::new);
    }

    @BeforeMethod
    public void testName(Method method, Object[] testData, ITestContext ctx) {
        if (testData.length > 0) {
            Pickle pickle = ((PickleWrapper) testData[0]).getPickle();
            testName.set(pickle.getName() + " [" + pickle.hashCode() + "]");
            ctx.setAttribute("testName", testName.get());
        } else
            ctx.setAttribute("testName", method.getName());
    }

    @AfterMethod
    public void testResult(ITestResult result) {
        LOG.info("{} | {}", getStatus(result.getStatus()), result.getName());
    }

    @AfterMethod
    public void progress(Method method, Object[] testData, ITestContext ctx) {
        int passed = ctx.getPassedTests().size();
        int failed = ctx.getFailedTests().size();
        int skipped = ctx.getSkippedTests().size();
        LOG.info("Progress: {}% (passed: {}, failed: {}, skipped: {}, total: {})", (passed + failed + skipped) * 100 / totalTestCount.get(),
                passed, failed, skipped, totalTestCount.get());
    }

    @Override
    public String getTestName() {
        return testName.get();
    }

    private static String getStatus(int status) {
        switch (status) {
            case ITestResult.FAILURE:
                return "FAILURE";
            case ITestResult.SUCCESS:
                return "SUCCESS";
            case ITestResult.SKIP:
                return "SKIP";
        }
        return "UNKNOWN";
    }
}