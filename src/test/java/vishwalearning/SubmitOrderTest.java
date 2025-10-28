// java
package vishwalearning;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import vishwalearning.TestComponents.BaseTest;
import vishwalearning.pageobjects.*;

/**
 * SubmitOrderTest
 *
 * This test class contains end-to-end purchase flows for the sample application.
 * Tests are implemented using TestNG and rely on page object classes located in
 * the vishwalearning.pageobjects package. BaseTest provides browser setup and
 * a pre-initialized LandingPage instance.
 *
 * Test responsibilities:
 * - SubmitOrder: optionally register a user, then log in, add product to cart,
 *   complete checkout and assert confirmation message.
 * - orderHistoryTest: verify that a previously ordered product appears in order history.
 *
 * Note:
 * - Test data is supplied via the getData() data provider which reads JSON files
 *   into a List<HashMap\<String,String\>> using BaseTest utilities.
 */
public class SubmitOrderTest extends BaseTest {

    // Product used in the order-history verification test
    String ProductName = "ADIDAS ORIGINAL";

    /**
     * End-to-end purchase test.
     *
     * Data provider supplies a map with keys used below (e.g. "email", "password",
     * "firstName", "lastName", "Product", etc.).
     *
     * Test flow:
     * 1. (Optional) Register the user using RegistrationPage.
     * 2. Log in via LandingPage.loginApplication.
     * 3. Find and add the product to cart.
     * 4. Verify product is present in cart.
     * 5. Checkout, select country and submit the order.
     * 6. Verify confirmation message and sign out.
     *
     * @param input HashMap containing test data for this run
     * @throws IOException when reading additional test resources (not used here)
     */
    @Test(dataProvider = "getData", groups = { "purchase" })
    public void SubmitOrder(HashMap<String, String> input) throws IOException {
        // Country name fragment used by CheckOutPage.selectCountry (assumes partial match)
        String CountryName = "Indi";
        // Expected confirmation message after successful order submission
        String ConfirmationMessage = "Thankyou for the order.";

        // ---------------------------
        // OPTIONAL: User registration
        // ---------------------------
        // Instantiate RegistrationPage using the protected `driver` from BaseTest
        // and complete the registration using test data values. This step is useful
        // when tests should create fresh accounts, but can be skipped if accounts
        // already exist.
        RegistrationPage regPage = new RegistrationPage(driver);
        regPage.completeRegistration(
                input.get("firstName"),
                input.get("lastName"),
                input.get("email"),
                input.get("phone"),
                input.get("occupation"), // occupation displayed as visible text (e.g., "Doctor")
                input.get("gender"),     // "Male" or "Female"
                input.get("password"),
                true                     // agree to terms checkbox
        );

        // ---------------------------
        // LOGIN and PRODUCT FLOW
        // ---------------------------
        // Login returns a ProductCatalog page object on success
        ProductCatalog productCatalog = landingpage.loginApplication(input.get("email"), input.get("password"));

        // Load products and add the requested product (from input) to the cart
        productCatalog.getProductsList();
        productCatalog.addProductToCart(input.get("Product"));

        // Navigate to cart and verify the product is present
        CartPage cartPage = productCatalog.goToCartPage();
        Boolean match = cartPage.getCartProductsList(input.get("Product"));
        Assert.assertTrue(match, "Product should be present in the cart after adding it.");

        // ---------------------------
        // CHECKOUT and CONFIRMATION
        // ---------------------------
        CheckOutPage checkOutPage = cartPage.goToCheckOut();

        // Select country (partial text match) and submit the order
        checkOutPage.selectCountry(CountryName);
        ConfirmationPage confirmationPage = checkOutPage.submitOrder();

        // Verify order confirmation message
        String returnMessage = confirmationPage.VerifyMessage();
        System.out.println("returnMessage: " + returnMessage);
        Assert.assertTrue(returnMessage.equalsIgnoreCase(ConfirmationMessage),
                "Confirmation message should match expected text.");

        // Sign out to leave application in a clean state for other tests
        confirmationPage.signOutApp();
    }

    /**
     * Verify order appears in order history.
     *
     * This test depends on SubmitOrder and logs in using a fixed test credential to
     * navigate to order history and assert the previously ordered product is present.
     */
    @Test(dependsOnMethods = { "SubmitOrder" })
    public void orderHistoryTest() {

        // Login with known account (hard-coded for this test). Consider moving these
        // credentials to a secure config or the test data JSON.
        ProductCatalog productCatalog = landingpage.loginApplication("visuskc@gmail.com", "Visu00))");

        // Navigate to order history and assert that the product name from earlier is present
        OrderHistoryPage orderHistoryPage = productCatalog.goToOrderHistoryPage();
        Assert.assertTrue(orderHistoryPage.verifyProductOnOrderHistory(ProductName),
                "Ordered product should appear in order history.");

        // Clean up: sign out
        orderHistoryPage.signOutApp();
    }

    /**
     * TestNG data provider that loads purchase test data from a JSON file.
     *
     * The JSON file is expected to contain an array of objects; each object maps
     * string keys to string values. BaseTest.getJsonDataToMap handles parsing.
     *
     * Example JSON structure:
     * [
     *   {
     *     "firstName": "John",
     *     "lastName": "Doe",
     *     "email": "john@example.com",
     *     "password": "Secret123",
     *     "Product": "ADIDAS ORIGINAL"
     *   },
     *   ...
     * ]
     *
     * @return two-dimensional Object array compatible with TestNG data providers
     * @throws IOException when JSON file cannot be read
     */
    @DataProvider
    public Object[][] getData() throws IOException {
        // Build path to the test data JSON file relative to the project root
        List<HashMap<String, String>> data = getJsonDataToMap(
                System.getProperty("user.dir") + "\\src\\test\\java\\vishwalearning\\data\\PurchaseOrder.json");

        // Return the first two entries as separate test runs. Expand as needed.
        return new Object[][] { { data.get(0) }, { data.get(1) } };
    }

    /* legacy data providers kept commented */
}
