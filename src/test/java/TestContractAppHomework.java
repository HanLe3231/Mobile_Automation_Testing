import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.net.URL;
import java.time.Duration;
import java.util.Random;
import java.util.UUID;

import static org.testng.AssertJUnit.assertEquals;

public class TestContractAppHomework {
    AppiumDriver driver;

    @BeforeClass
    public void setUp() throws Exception {
        UiAutomator2Options options = new UiAutomator2Options()
                .setUdid("emulator-5554")
                .setNewCommandTimeout(Duration.ofSeconds(50))
                .setAppPackage("com.google.android.contacts")
                .setAppActivity("com.android.contacts.activities.PeopleActivity");
        driver = new AndroidDriver(new URL("http://127.0.0.1:4723"), options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(50));
    }

    @Test
    public void testAddNewContact() throws InterruptedException {
        // Generate dynamic test data
        String firstNameData = "TestFirst" + UUID.randomUUID().toString().substring(0, 5);
        String lastNameData = "TestLast" + UUID.randomUUID().toString().substring(0, 5);
        String contactName = firstNameData + " " + lastNameData;
        String companyData = "Company" + new Random().nextInt(1000);
        String phoneData = "079" + (1000000 + new Random().nextInt(9000000));

        // Add New Contact
        driver.findElement(AppiumBy.accessibilityId("Create contact")).click();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(50));

        wait.until(ExpectedConditions.visibilityOfElementLocated(
                AppiumBy.xpath("//android.widget.EditText[@text=\"First name\"]"))).sendKeys(firstNameData);
        driver.findElement(AppiumBy.xpath("//android.widget.EditText[@text=\"Last name\"]")).sendKeys(lastNameData);
        driver.findElement(AppiumBy.xpath("//android.widget.EditText[@text=\"Company\"]")).sendKeys(companyData);
        driver.findElement(AppiumBy.xpath("//android.widget.EditText[@text=\"Phone\"]")).sendKeys(phoneData);

        // Save the new contact
        driver.findElement(AppiumBy.xpath("//android.widget.Button[@resource-id=\"com.google.android.contacts:id/toolbar_button\"]")).click();

        // Go back to the contact list

        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(AppiumBy.accessibilityId("Navigate up"))).click();
        } catch (Exception e) {
            driver.navigate().back();
        }

        Thread.sleep(2000);


        WebElement newContact = wait.until(ExpectedConditions.visibilityOfElementLocated(
                AppiumBy.xpath("//android.widget.TextView[@text='" + contactName + "']")));
        newContact.click();


        WebElement contactDetailName = wait.until(ExpectedConditions.visibilityOfElementLocated(
                AppiumBy.xpath("//android.widget.TextView[@text='" + contactName + "']")));
        assertEquals(contactName, contactDetailName.getText());
        System.out.println("Successfully created and selected contact: " + contactName);


        driver.findElement(AppiumBy.accessibilityId("More options")).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(
                AppiumBy.xpath("//android.widget.TextView[@text='Delete']"))).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(
                AppiumBy.id("android:id/button1"))).click(); // Assuming 'OK' button is to confirm deletion


        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(AppiumBy.accessibilityId("Navigate up"))).click();
        } catch (Exception e) {
            driver.navigate().back();
        }


        Thread.sleep(2000);


        boolean isContactDeleted;
        try {
            driver.findElement(AppiumBy.xpath("//android.widget.TextView[@text='" + contactName + "']"));
            isContactDeleted = false; // Contact is still present
        } catch (NoSuchElementException e) {
            isContactDeleted = true; // Contact is deleted
        }

        assertEquals(true, isContactDeleted);
        System.out.println("Contact successfully deleted: " + contactName);

    }

    @AfterClass
    public void tearDown() throws Exception {
        if (driver != null) {
            driver.quit();
        }
    }
}
