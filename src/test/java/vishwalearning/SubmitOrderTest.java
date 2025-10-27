package vishwalearning;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import org.junit.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import vishwalearning.TestComponents.BaseTest;
import vishwalearning.pageobjects.CartPage;
import vishwalearning.pageobjects.CheckOutPage;
import vishwalearning.pageobjects.ConfirmationPage;
import vishwalearning.pageobjects.OrderHistoryPage;
import vishwalearning.pageobjects.ProductCatalog;

public class SubmitOrderTest extends BaseTest{
	String ProductName = "ADIDAS ORIGINAL";

	@Test(dataProvider="getData", groups={"purchase"})
	public void SubmitOrder(HashMap<String, String> input) throws IOException {
		// TODO Auto-generated method stub
		String CountryName = "Indi";
		String ConformationMessage = "Thankyou for the order.";
		
		ProductCatalog productCatalog = landingpage.loginApplication(input.get("email"), input.get("password"));

		productCatalog.getProductsList();
		productCatalog.addProductToCart(input.get("Product"));
		CartPage cartPage = productCatalog.goToCartPage();
		
		Boolean match = cartPage.getCartProductsList(input.get("Product"));
		Assert.assertTrue(match);
		CheckOutPage checkOutPage = cartPage.goToCheckOut();
		checkOutPage.selectCountry(CountryName);
		ConfirmationPage confirmationPage = checkOutPage.submitOrder();
		String returnMessage = confirmationPage.VerifyMessage();
		System.out.println("returnMessage: "+returnMessage);
		Assert.assertTrue(returnMessage.equalsIgnoreCase(ConformationMessage));
		confirmationPage.signOutApp();
	}

	@Test(dependsOnMethods= {"SubmitOrder"})
	public void orderHistoryTest() {
		ProductCatalog productCatalog = landingpage.loginApplication("visuskc@gmail.com", "Visu00))");
		OrderHistoryPage orderHistoryPage = productCatalog.goToOrderHistoryPage();
		Assert.assertTrue(orderHistoryPage.verifyProductOnOrderHistory(ProductName));
		orderHistoryPage.signOutApp();
	}

	@DataProvider
	public Object[][] getData() throws IOException{
		List<HashMap<String, String>> data = getJsonDataToMap(System.getProperty("user.dir")+"\\src\\test\\java\\vishwalearning\\data\\PurchaseOrder.json");
		return new Object[][] {{data.get(0)},{data.get(1)}};
	}
	
/*	@DataProvider
	public Object[][] getData(){
		HashMap <String, String> map1 = new HashMap<String, String>();
		map1.put("email", "visuskc@gmail.com");
		map1.put("password", "Visu00))");
		map1.put("Product", "ADIDAS ORIGINAL");
		
		HashMap <String, String> map2 = new HashMap<String, String>();
		map2.put("email", "visuskc@gmail.com");
		map2.put("password", "Visu00))");
		map2.put("Product", "ZARA COAT 3");
		return new Object[][] {{map1},{map2}};
	}
	*/

/*	@DataProvider
	public Object[][] getData(){
		return new Object[][] {{"visuskc@gmail.com","Visu00))","ZARA COAT 3"},{"visuskc@gmail.com","Visu00))","ADIDAS ORIGINAL"}};
	}
	*/
}