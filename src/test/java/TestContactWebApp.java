import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.net.URL;
import java.time.Duration;

public class TestContactWebApp {
    AppiumDriver driver;

    @BeforeClass
    public void setUp() throws Exception {
        UiAutomator2Options options = new UiAutomator2Options()
                .setUdid("emulator-5554")
                .withBrowserName("Chrome");
        driver = new AndroidDriver(new URL("http://127.0.0.1:4723"), options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }

    @Test
    public void test() {
        driver.get("https://magento.softwaretestingboard.com/push-it-messenger-bag.html");
        WebElement productTitle = driver.findElement(By.cssSelector("h1.page-title"));
        driver.executeScript("arguments[0].scrollIntoView()", productTitle);

        WebElement addToCartButton = driver.findElement(By.id("product-addtocart-button"));
        addToCartButton.click();
    }
    @AfterClass
    public void tearDown() throws Exception {
        if (driver != null) {
            driver.quit();
        }
    }
}
