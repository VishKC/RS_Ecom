package vishwalearning.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import vishwalearning.AbstractComponents.AbstractComponent;

public class CheckOutPage extends AbstractComponent{

	WebDriver driver;
	public CheckOutPage(WebDriver driver) {
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(xpath="//input[@placeholder='Select Country']")
	WebElement country;
	
	@FindBy(xpath="//button[contains(@class,'ta-item')][2]")
	WebElement selectcountry;

	@FindBy(xpath="//a[normalize-space()='Place Order']")
	WebElement submitBtn;
	
	By waitForCountry = By.cssSelector(".ta-item");
	
	public void selectCountry(String CountryName) {
		Actions act = new Actions(driver);
		act.sendKeys(country, CountryName).build().perform();
		waitForElementToAppear(waitForCountry);
		selectcountry.click();
	}
	
	public ConfirmationPage submitOrder() {
		submitBtn.click();
		ConfirmationPage confirmationPage = new ConfirmationPage(driver);
		return confirmationPage;
	}
}
