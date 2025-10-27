package vishwalearning.pageobjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import vishwalearning.AbstractComponents.AbstractComponent;

public class LandingPage extends AbstractComponent{

	WebDriver driver;

	public LandingPage(WebDriver driver) {
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	@FindBy(id = "userEmail")
	WebElement userEmail;

	@FindBy(id = "userPassword")
	WebElement passwordElement;

	@FindBy(id = "login")
	WebElement loginBtn;

	@FindBy(css="[class*='toast-message']")
	WebElement errorToast;
	
	public void goTo() {
		driver.get("https://rahulshettyacademy.com/client");
	}

	public ProductCatalog loginApplication(String email, String password) {
		userEmail.clear();
		userEmail.sendKeys(email);
		passwordElement.sendKeys(password);
		loginBtn.click();
		ProductCatalog productCatalog = new ProductCatalog(driver);
		return productCatalog;
	}
	
	public String errorValidation() {
		waitForWebElementToAppear(errorToast);
		return errorToast.getText(); 
	}
}
