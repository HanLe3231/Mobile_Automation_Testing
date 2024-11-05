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
import java.util.Map;

public class TestMagentoWebApp {
    AppiumDriver driver;

    @BeforeClass
    public void setUp() throws Exception {
        UiAutomator2Options options = new UiAutomator2Options()
                .setUdid("emulator-5554")
                .withBrowserName("Chrome");
        options.setCapability("chromeOptions", Map.of(
                "args", new String[]{
                        "--disable-infobars",
                        "--incognito",
                        "--disable-extensions",
                        "--start-maximized",
                        "--disable-notifications",
                        "--ignore-certificate-errors",
                        "--disable-popup-blocking", // Ensure this is not set as it disables popup blocking
                        "--enable-features=OverlayScrollbar", // Use overlay scrollbars
                        "--disable-gesture-requirement-for-media-playback", // Allow media playback without user gestures
                        "--allow-insecure-localhost", // Allow insecure localhost connections
                        "--enable-automation", // Enable automation features
                        "--headless" // Run headless if GUI is not required
                }
        ));
        driver = new AndroidDriver(new URL("http://127.0.0.1:4723"), options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
    }

    @Test
    public void test() throws InterruptedException {
        driver.get("https://magento.softwaretestingboard.com/");



        driver.findElement(By.xpath("//header/div[2]/span[1]")).click();

        driver.findElement(By.xpath("//a[@id='ui-id-4']")).click();

        driver.findElement(By.xpath("(//span[contains(text(),'Jackets')])[1]")).click();

        WebElement productTitle = driver.findElement(By.xpath("//span[@class='base']"));
        driver.executeScript("arguments[0].scrollIntoView()", productTitle);
        driver.findElement(By.xpath("(//span[contains(text(),'Add to Cart')])[2]")).click();

        WebElement Size = driver.findElement(By.xpath("//span[@id='option-label-size-143']"));
        driver.executeScript("arguments[0].scrollIntoView()", Size);
        driver.findElement(By.xpath("//div[@id='option-label-size-143-item-166']")).click();


        driver.findElement(By.xpath("//div[@id='option-label-color-93-item-50']")).click();

        WebElement Qty = driver.findElement(By.xpath("//span[normalize-space()='Qty']"));
        driver.executeScript("arguments[0].scrollIntoView()", Qty);
        driver.findElement(By.xpath("(//input[@id='qty'])[1]")).click();

        WebElement addToCart = driver.findElement(By.xpath("//button[@id='product-addtocart-button']"));
        driver.executeScript("arguments[0].scrollIntoView()", addToCart);
        driver.findElement(By.xpath("//button[@id='product-addtocart-button']")).click();

        WebElement shoppingCart = driver.findElement(By.xpath("//a[normalize-space()='shopping cart']"));
        driver.executeScript("arguments[0].scrollIntoView()",shoppingCart);
        driver.findElement(By.xpath("//a[normalize-space()='shopping cart']")).click();
        WebElement yourProduct = driver.findElement(By.xpath("(//a[normalize-space()='Juno Jacket'])[2]"));
        driver.executeScript("arguments[0].scrollIntoView()", yourProduct);

        // Verify the item in the cart
        String expectedItemName = "Juno Jacket";
        String expectedColor = "Blue";
        String expectedSize = "XS";
        String expectedItemPrice = "$77.00";

        WebElement itemName = driver.findElement(By.xpath("(//a[normalize-space()='Juno Jacket'])[2]"));
        String actualItemName = itemName.getText();

        WebElement itemColor = driver.findElement(By.xpath("//dd[contains(text(),'Blue')]"));
        String actualItemColor = itemColor.getText();

        WebElement itemSize = driver.findElement(By.xpath("//dd[contains(text(),'XS')]"));
        String actualItemSize = itemSize.getText();

        WebElement itemPrice = driver.findElement(By.cssSelector("td[class='col price'] span[class='price']"));
        String actualItemPrice = itemPrice.getText();

        // Verify that the item name and price match expected values
        if (actualItemName.equals(expectedItemName) && actualItemPrice.equals(expectedItemPrice) && actualItemSize.equals(expectedSize) && actualItemColor.equals(expectedColor)) {
            System.out.println("Item is correctly listed in the cart.");
        } else {
            System.out.println("Item verification failed.");
        }

        WebElement checkoutButton = driver.findElement(By.xpath("//span[normalize-space()='Proceed to Checkout']"));
        driver.executeScript("arguments[0].scrollIntoView()", checkoutButton);
        driver.findElement(By.xpath("//span[normalize-space()='Proceed to Checkout']")).click();


        WebElement emailAddress = driver.findElement(By.xpath("(//input[@id='customer-email'])[1]"));
        driver.executeScript("arguments[0].scrollIntoView()", emailAddress);
        Thread.sleep(2000);
        driver.findElement(By.cssSelector("#customer-email")).sendKeys("hanle@gmail.com");



        WebElement firstName = driver.findElement(By.xpath("//span[normalize-space()='First Name']"));
        driver.executeScript("arguments[0].scrollIntoView()", firstName);
        driver.findElement(By.name("firstname")).sendKeys("Han");

        driver.findElement(By.name("lastname")).sendKeys("Le");

        WebElement streetAddress = driver.findElement(By.xpath("//span[normalize-space()='Street Address']"));
        driver.executeScript("arguments[0].scrollIntoView()", streetAddress);

        driver.findElement(By.name("street[0]")).sendKeys("street1");








    }
    @AfterClass
    public void tearDown() throws Exception {
        if (driver != null) {
            driver.quit();
        }
    }
}
