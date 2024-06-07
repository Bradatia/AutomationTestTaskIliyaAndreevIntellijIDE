package AutomationTestTask.StepDefinitions;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.java.en.And;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import java.time.Duration;
import java.text.DecimalFormat;

public class TestDefinitions {
    private static WebDriver driver;
    public static String firstProductNameString;
    public static String secondProductNameString;
    public static Float firstProductPriceValue;
    public static Float secondProductPriceValue;
    public final static int TIMEOUT = 5;
    public final static DecimalFormat df = new DecimalFormat("0.00");

    @Before
    public void setUp() {

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");
        driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(TIMEOUT));

    }

    @Given("User is on Swag Labs page {string}")
    public void loadLoginPaget(String url) {

        driver.get(url);

    }

    @When("User enters username as {string} and password as {string}")
    public void enterLoginCredentials(String userName, String passWord) {

        // Enter credentials to fields
        driver.findElement(By.id("user-name")).sendKeys(userName);
        driver.findElement(By.id("password")).sendKeys(passWord);

    }

    @And("User click on Login button")
    public void clickLoginButton() {

        // Click on Login Button
        driver.findElement(By.id("login-button")).click();

    }

    @Then("User should be able to login successfully and new page open")
    public void verifyLogin() {

        String inventoryPageUrl = driver.getCurrentUrl();
        String inventoryPageHeading  = driver.findElement(By.xpath("//*[@id=\"header_container\"]/div[2]/span")).getText();

        //Verify new page - Inventory Page
        Assert.assertEquals(inventoryPageUrl, "https://www.saucedemo.com/inventory.html");
        Assert.assertEquals(inventoryPageHeading, "Products");
    }

    @Then("Add products to the Cart")
    public void selectProducts(){

        driver.findElement(By.id("add-to-cart-sauce-labs-backpack")).click();
        driver.findElement(By.id("add-to-cart-sauce-labs-fleece-jacket")).click();

    }
    @And("Check number of products in the Cart")
    public void verifyNumberOfProductsInTheCart() {

        String numberOfProductsInTheCart = driver.findElement(By.xpath("//*[@id=\"shopping_cart_container\"]/a/span")).getText();
        Assert.assertEquals(numberOfProductsInTheCart, "2");

    }

    @Then("Remove products from Cart")
    public void removeProductsFromCart() {

        driver.findElement(By.id("remove-sauce-labs-backpack")).click();
        driver.findElement(By.id("remove-sauce-labs-fleece-jacket")).click();

    }
    @And("Check products are removed from Cart")
    public void verifyProductsRemovedFromCart() {

        Assert.assertTrue(driver.findElements(By.xpath("//*[@id=\"shopping_cart_container\"]/a/span")).isEmpty());

    }

    @Then("Select again products to the Cart")
    public void selectAgainProducts(){

        driver.findElement(By.id("add-to-cart-sauce-labs-bike-light")).click();
        driver.findElement(By.id("add-to-cart-sauce-labs-bolt-t-shirt")).click();

        // Get name of the products and prices for later use
        firstProductNameString      = driver.findElement(By.xpath("//*[@id=\"item_0_title_link\"]/div")).getText();
        secondProductNameString     = driver.findElement(By.xpath("//*[@id=\"item_1_title_link\"]/div")).getText();
        firstProductPriceValue      = Float.valueOf((driver.findElement(By.xpath("//*[@id=\"inventory_container\"]/div/div[2]/div[2]/div[2]/div")).getText()).substring(1));
        secondProductPriceValue     = Float.valueOf((driver.findElement(By.xpath("//*[@id=\"inventory_container\"]/div/div[3]/div[2]/div[2]/div")).getText()).substring(1));

    }

    @And("Check again number of products in the Cart")
    public void verifyAgainNumberOfProductsInTheCart() {

        String numberOfProductsInTheCart = driver.findElement(By.xpath("//*[@id=\"shopping_cart_container\"]/a/span")).getText();
        Assert.assertEquals(numberOfProductsInTheCart, "2");
    }

    @Then("Go to Cart page")
    public void clickCartButton() {

        driver.findElement(By.className("shopping_cart_link")).click();

    }

    @And("Verify redirect to Shopping Cart page")
    public void verifyRedirectToCart() {

        String cartPageUrl      = driver.getCurrentUrl();
        String cartPageHeading  = driver.findElement(By.xpath("//*[@id=\"header_container\"]/div[2]/span")).getText();

        // Verify new page - Cart Page
        Assert.assertEquals(cartPageUrl, "https://www.saucedemo.com/cart.html");
        Assert.assertEquals(cartPageHeading, "Your Cart");

    }

    @Then("Verify products in the Cart")
    public void verifyProductsInTheCart() {

        String verifyFirstProductName   = driver.findElement(By.xpath("//*[@id=\"item_0_title_link\"]/div")).getText();
        String verifySecondProductName  = driver.findElement(By.xpath("//*[@id=\"item_1_title_link\"]/div")).getText();
        Float verifyFirstProuctValue    = Float.valueOf((driver.findElement(By.xpath("//*[@id=\"cart_contents_container\"]/div/div[1]/div[3]/div[2]/div[2]/div")).getText()).substring(1));
        Float verifySecondProuctValue   = Float.valueOf((driver.findElement(By.xpath("//*[@id=\"cart_contents_container\"]/div/div[1]/div[4]/div[2]/div[2]/div")).getText()).substring(1));

        // Verify Product Properties from current page equals reference values
        Assert.assertEquals(verifyFirstProductName, firstProductNameString);
        Assert.assertEquals(verifySecondProductName, secondProductNameString);
        Assert.assertEquals(df.format(verifyFirstProuctValue), df.format(firstProductPriceValue));
        Assert.assertEquals(df.format(verifySecondProuctValue), df.format(secondProductPriceValue));

    }

    @Then("Go to Checkout page")
    public void clickCheckoutButton() {

        driver.findElement(By.id("checkout")).click();

    }

    @And("Verify redirect to Shopping Checkout page")
    public void verifyRedirectToCheckout() {

        String checkoutPageUrl          = driver.getCurrentUrl();
        String checkoutPageHeading      = driver.findElement(By.xpath("//*[@id=\"header_container\"]/div[2]/span")).getText();
        String verifyFirstNameField     = driver.findElement(By.id("first-name")).getAttribute("placeholder");
        String verifyLastNameField      = driver.findElement(By.id("last-name")).getAttribute("placeholder");
        String verifyPostalCodeField    = driver.findElement(By.id("postal-code")).getAttribute("placeholder");

        // Verify new page - Checkout Page
        Assert.assertEquals(checkoutPageUrl, "https://www.saucedemo.com/checkout-step-one.html");
        Assert.assertEquals(checkoutPageHeading, "Checkout: Your Information");
        Assert.assertEquals(verifyFirstNameField, "First Name");
        Assert.assertEquals(verifyLastNameField, "Last Name");
        Assert.assertEquals(verifyPostalCodeField, "Zip/Postal Code");

    }

    @Then("Try to submit Empty form")
    public void submitEmptyForm() {

        driver.findElement(By.id("continue")).click();

    }

    @And("Verify error messages form Empty form")
    public void verifyEmptyFormErrorMessages() {

        String errorFirstNameField      = driver.findElement(By.id("first-name")).getAttribute("class");
        String errorLastNameField       = driver.findElement(By.id("last-name")).getAttribute("class");
        String errorPostCodeField       = driver.findElement(By.id("postal-code")).getAttribute("class");
        String errorMessageContainer    = driver.findElement(By.xpath("//*[@id=\"checkout_info_container\"]/div/form/div[1]/div[4]")).getAttribute("class");
        String errorMessage             = driver.findElement(By.xpath("//*[@id=\"checkout_info_container\"]/div/form/div[1]/div[4]/h3")).getText();

        Assert.assertEquals(errorFirstNameField, "input_error form_input error");
        Assert.assertEquals(errorLastNameField, "input_error form_input error");
        Assert.assertEquals(errorPostCodeField, "input_error form_input error");
        Assert.assertEquals(errorMessageContainer, "error-message-container error");
        Assert.assertEquals(errorMessage, "Error: First Name is required");

    }

    @Then("Clear errors from Submit page")
    public void clearSubmitForm() {

        driver.findElement(By.xpath("//*[@id=\"checkout_info_container\"]/div/form/div[1]/div[4]/h3/button")).click();

    }

    @And("Submit correct filled Submit form with {string}, {string} and {string}")
    public void submitCorrectForm(String firstName, String lastName, String postalCode) {

        driver.findElement(By.id("first-name")).sendKeys(firstName);
        driver.findElement(By.id("last-name")).sendKeys(lastName);
        driver.findElement(By.id("postal-code")).sendKeys(postalCode);
        driver.findElement(By.id("continue")).click();

    }

    @Then("Verify redirect to Checkout Overview page")
    public void verifyRedirectToCheckoutOverview() {

        String overviewPageUrl      = driver.getCurrentUrl();
        String overviewPageHeading  = driver.findElement(By.xpath("//*[@id=\"header_container\"]/div[2]/span")).getText();

        //Verify new page - Checkout Overview Page
        Assert.assertEquals(overviewPageUrl, "https://www.saucedemo.com/checkout-step-two.html");
        Assert.assertEquals(overviewPageHeading, "Checkout: Overview");

    }
    @And("Verify correct products, prices and buttons are displayed")
    public void verifyElementsonCheckoutOverviewPage() {

        Float taxMultiplicator = 0.08f;
        Float referentItemsTotalPrice = Float.sum(firstProductPriceValue, secondProductPriceValue);

        // Verify buttons
        driver.findElement(By.id("react-burger-menu-btn"));
        driver.findElement(By.id("shopping_cart_container"));
        driver.findElement(By.id("cancel"));
        driver.findElement(By.id("finish"));

        // Get Elements values
        String verifyFirstProductName       = driver.findElement(By.id("item_0_title_link")).getText();
        String verifySecondProductName      = driver.findElement(By.id("item_1_title_link")).getText();
        String verifyFirstProductQuantity   = driver.findElement(By.xpath("//*[@id=\"checkout_summary_container\"]/div/div[1]/div[3]/div[1]")).getText();
        String verifySecondProductQuantity  = driver.findElement(By.xpath("//*[@id=\"checkout_summary_container\"]/div/div[1]/div[4]/div[1]")).getText();
        Float verifyFirstProuctValue        = Float.valueOf((driver.findElement(By.xpath("//*[@id=\"checkout_summary_container\"]/div/div[1]/div[3]/div[2]/div[2]/div")).getText()).substring(1));
        Float verifySecondProuctValue       = Float.valueOf((driver.findElement(By.xpath("//*[@id=\"checkout_summary_container\"]/div/div[1]/div[4]/div[2]/div[2]/div")).getText()).substring(1));
        String verifyPaymentInformationElement  = driver.findElement(By.xpath("//*[@id=\"checkout_summary_container\"]/div/div[2]/div[1]")).getText();
        String verifyShippingInformationElement = driver.findElement(By.xpath("//*[@id=\"checkout_summary_container\"]/div/div[2]/div[3]")).getText();
        String verifyPriceTotalElement = driver.findElement(By.xpath("//*[@id=\"checkout_summary_container\"]/div/div[2]/div[5]")).getText();
        Float verifyTotalItemsPrice = Float.valueOf((driver.findElement(By.xpath("//*[@id=\"checkout_summary_container\"]/div/div[2]/div[6]")).getText()).substring(13));
        Float verifyTaxValue = Float.valueOf((driver.findElement(By.xpath("//*[@id=\"checkout_summary_container\"]/div/div[2]/div[7]")).getText()).substring(6));
        Float verifyTotalPrice = Float.valueOf((driver.findElement(By.xpath("//*[@id=\"checkout_summary_container\"]/div/div[2]/div[8]")).getText()).substring(8));

        // Verify Elements values equals reference values
        Assert.assertEquals(verifyFirstProductName, firstProductNameString);
        Assert.assertEquals(verifySecondProductName, secondProductNameString);
        Assert.assertEquals(verifyFirstProductQuantity, "1");
        Assert.assertEquals(verifySecondProductQuantity, "1");
        Assert.assertEquals(df.format(verifyFirstProuctValue), df.format(firstProductPriceValue));
        Assert.assertEquals(df.format(verifySecondProuctValue), df.format(secondProductPriceValue));
        Assert.assertEquals(verifyPaymentInformationElement, "Payment Information:");
        Assert.assertEquals(verifyShippingInformationElement, "Shipping Information:");
        Assert.assertEquals(verifyPriceTotalElement, "Price Total");
        Assert.assertEquals(df.format(verifyTotalItemsPrice), df.format(referentItemsTotalPrice));
        Assert.assertEquals(df.format(verifyTaxValue), df.format(referentItemsTotalPrice*taxMultiplicator));
        Assert.assertEquals(df.format(verifyTotalPrice), df.format(Float.sum(referentItemsTotalPrice, (referentItemsTotalPrice*taxMultiplicator))));

    }

    @Then("Complete the Order")
    public void completeTheOrder() {

        driver.findElement(By.id("finish")).click();

    }

    @And("Verify redirect to checkout Complete page")
    public void verifyRedirectToCheckoutCompletePage() {

        String completePageUrl      = driver.getCurrentUrl();
        String completePageHeading  = driver.findElement(By.xpath("//*[@id=\"header_container\"]/div[2]/span")).getText();
        String completePageHeader   = driver.findElement(By.xpath("//*[@id=\"checkout_complete_container\"]/h2")).getText();
        String completePageText     = driver.findElement(By.xpath("//*[@id=\"checkout_complete_container\"]/div")).getText();
        String backHomeButton       = driver.findElement(By.id("back-to-products")).getText();

        // Verify new page - Checkout Overview Page
        Assert.assertEquals(completePageUrl, "https://www.saucedemo.com/checkout-complete.html");
        Assert.assertEquals(completePageHeading, "Checkout: Complete!");
        Assert.assertEquals(completePageHeader, "Thank you for your order!");
        Assert.assertEquals(completePageText, "Your order has been dispatched, and will arrive just as fast as the pony can get there!");
        Assert.assertEquals(backHomeButton, "Back Home");

    }

    @Then("Logout")
    public void logoutFromPage() {

        driver.findElement(By.id("react-burger-menu-btn")).click();
        driver.findElement(By.id("logout_sidebar_link")).click();

    }

    @And("Verify redirect to Login page")
    public void verifyRedirectToLoginPage() {

        String completePageUrl = driver.getCurrentUrl();
        driver.findElement(By.id("user-name"));
        driver.findElement(By.id("password"));
        driver.findElement(By.id("login-button"));

        Assert.assertEquals(completePageUrl, "https://www.saucedemo.com/");

    }

    @After
    public void teardown() {

        driver.quit();
    }

}
