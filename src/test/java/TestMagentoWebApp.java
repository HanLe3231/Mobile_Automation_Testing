import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.net.URL;
import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;


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
        scrollToView(productTitle);
        driver.findElement(By.xpath("(//span[contains(text(),'Add to Cart')])[2]")).click();
        dismissAdIfPresent();

        WebElement Size = driver.findElement(By.xpath("//span[@id='option-label-size-143']"));
        scrollToView(Size);
        dismissAdIfPresent();
        toggleExpandableAd();

        driver.findElement(By.xpath("//div[@id='option-label-size-143-item-166']")).click();


        driver.findElement(By.xpath("//div[@id='option-label-color-93-item-50']")).click();

        WebElement Qty = driver.findElement(By.xpath("//span[normalize-space()='Qty']"));
        scrollToView(Qty);
        driver.findElement(By.xpath("(//input[@id='qty'])[1]")).click();

        WebElement addToCart = driver.findElement(By.xpath("//button[@id='product-addtocart-button']"));
        scrollToView(addToCart);
        driver.findElement(By.xpath("//button[@id='product-addtocart-button']")).click();

        WebElement shoppingCart = driver.findElement(By.xpath("//a[normalize-space()='shopping cart']"));
        scrollToView(shoppingCart);
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
        scrollToView(checkoutButton);
        driver.findElement(By.xpath("//span[normalize-space()='Proceed to Checkout']")).click();

        // Generate dynamic test data
        String firstNameData = "TestFirst" + UUID.randomUUID().toString().substring(0, 5);
        String lastNameData = "TestLast" + UUID.randomUUID().toString().substring(0, 5);
        String emailData = firstNameData + "@gmail.com";
        String cityData = "City" + new Random().nextInt(1000);
        String streetData = "Street" + new Random().nextInt(1000);
        String phoneData = "079" + (1000000 + new Random().nextInt(9000000));
        String postCodeData = String.valueOf(10000 + new Random().nextInt(90000));

        WebElement emailAddress = driver.findElement(By.xpath("(//input[@id='customer-email'])[1]"));
        scrollToView(emailAddress);
        Thread.sleep(2000);
        driver.findElement(By.cssSelector("#customer-email")).sendKeys(emailData);



        WebElement firstName = driver.findElement(By.xpath("//span[normalize-space()='First Name']"));
        scrollToView(firstName);
        driver.findElement(By.name("firstname")).sendKeys(firstNameData);

        driver.findElement(By.name("lastname")).sendKeys(lastNameData);

        WebElement streetAddress = driver.findElement(By.xpath("//span[normalize-space()='Street Address']"));
        scrollToView(streetAddress);

        driver.findElement(By.name("street[0]")).sendKeys(streetData);


        WebElement city = driver.findElement(By.name("city"));
        scrollToView(city);
        driver.findElement(By.name("city")).sendKeys(cityData);

        driver.findElement(By.name("postcode")).sendKeys(postCodeData);

        WebElement dropdownCountry = driver.findElement(By.name("region_id"));
        Select select1 = new Select(dropdownCountry);
        select1.selectByVisibleText("American Samoa");

        driver.findElement(By.name("telephone")).sendKeys(phoneData);

        WebElement radio = driver.findElement(By.xpath("//div[@class='checkout-shipping-method']"));
        scrollToView(radio);
        Thread.sleep(1000);
        driver.findElement(By.xpath("//span[contains(text(),'$0.00')]")).click();


        driver.findElement(By.xpath("//button[@class='button action continue primary']")).click();

        WebElement placeOrder = driver.findElement(By.xpath("//div[@class='billing-address-details']"));
        scrollToView(placeOrder);
        Thread.sleep(1000);
        driver.findElement(By.xpath("//button[@title='Place Order']")).click();


        WebElement checkoutSuccess = driver.findElement(By.xpath("//div[@id='registration']//div[1]"));
        scrollToView(checkoutSuccess);

        String expectedEmail = emailData;
        WebElement displayedEmailElement = driver.findElement(By.xpath("//span[@data-bind='text: getEmailAddress()']"));
        String actualEmail = displayedEmailElement.getText();

        // Verify if the displayed email matches the expected email
        if (actualEmail.equals(expectedEmail)) {
            System.out.println("Email address on the 'Thank You' page matches the provided email.");
        } else {
            System.out.println("Email address on the 'Thank You' page does not match the provided email.");
            System.out.println("Expected: " + expectedEmail + " | Actual: " + actualEmail);
        }


    }
    @AfterClass
    public void tearDown() throws Exception {
        if (driver != null) {
            driver.quit();
        }
    }
    // Helper method to dismiss ads if present
    private void dismissAdIfPresent() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        try {
            // Switch to each iframe on the page to look for the ad close button
            List<WebElement> iframes = driver.findElements(By.tagName("iframe"));
            for (WebElement iframe : iframes) {
                driver.switchTo().frame(iframe);
                try {
                    // Attempt to find the close button within the iframe
                    WebElement adCloseButton = driver.findElement(By.xpath("//*[contains(@aria-label, 'Close') or contains(@class, 'close-button')]"));
                    if (adCloseButton.isDisplayed()) {
                        adCloseButton.click();
                        System.out.println("Ad closed.");
                        break; // Exit after closing the ad
                    }
                } catch (Exception e) {
                    // No ad close button found in this iframe; continue to next iframe
                }
                // Switch back to the main content after checking this iframe
                driver.switchTo().defaultContent();
            }
        } catch (Exception e) {
            // Log or handle if no ad or iframe was found
            System.out.println("No ad to close or unable to locate ad iframe.");
        }
    }

    // Helper method to scroll to a specific element
    private void scrollToView(WebElement element) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
    }

    private void toggleExpandableAd() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        try {
            // Locate and click the expandable ad's toggle arrow (expand)
            WebElement expandArrow = driver.findElement(By.xpath("//button[contains(@class, 'expand-arrow') or @aria-label='Expand']"));
            if (expandArrow.isDisplayed()) {
                expandArrow.click(); // Click to expand the ad
                System.out.println("Expandable ad clicked to expand.");
            }

            // Alternatively, you may want to collapse the ad:
            WebElement collapseArrow = driver.findElement(By.xpath("//button[contains(@class, 'collapse-arrow') or @aria-label='Collapse']"));
            if (collapseArrow.isDisplayed()) {
                collapseArrow.click(); // Click to collapse the ad
                System.out.println("Expandable ad clicked to collapse.");
            }
        } catch (Exception e) {
            System.out.println("Error interacting with expandable ad: " + e.getMessage());
        }
    }
}
