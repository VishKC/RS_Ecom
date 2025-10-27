package vishwalearning.pageobjects;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import vishwalearning.AbstractComponents.AbstractComponent;

public class OrderHistoryPage extends AbstractComponent {

	WebDriver driver;

	public OrderHistoryPage(WebDriver driver) {
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	@FindBy(css = "tr td:nth-child(3)")
	private List<WebElement> ProductsName;

	
	public Boolean verifyProductOnOrderHistory(String ProductName) {
		System.out.println("ProductName : "+ProductName);
		Boolean match = ProductsName.stream()
				.anyMatch(Product -> Product.getText().equalsIgnoreCase(ProductName));
		return match;
	}

}
