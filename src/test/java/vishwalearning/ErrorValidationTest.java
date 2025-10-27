package vishwalearning;

import org.testng.Assert;
import org.testng.annotations.Test;

import vishwalearning.TestComponents.BaseTest;
import vishwalearning.pageobjects.CartPage;
import vishwalearning.pageobjects.ProductCatalog;

public class ErrorValidationTest extends BaseTest {


	@Test(groups="ErrorHandlingTests")
	public void loginErrorValidation() {

		String errorText = "Incorrect email or password.";
		ProductCatalog productCatalog =	landingpage.loginApplication("visuskc@gmail.com", "Visu))");
		Assert.assertEquals(errorText, landingpage.errorValidation());
	}

	@Test
	public void ProductErrorValidation() {
		String ProductName = "ADIDAS ORIGINAL";

		ProductCatalog productCatalog = landingpage.loginApplication("visuskc@gmail.com", "Visu00))");
		productCatalog.getProductsList();
		productCatalog.addProductToCart(ProductName);
		CartPage cartPage = productCatalog.goToCartPage();
		Boolean match = cartPage.getCartProductsList("ADIDAS");
		Assert.assertFalse(match);
		cartPage.signOutApp();
		//Assert.assertTrue(match);
	}
	
}
