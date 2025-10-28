package vishwalearning.pageobjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;
import vishwalearning.AbstractComponents.AbstractComponent;

public class RegistrationPage extends AbstractComponent {

    WebDriver driver;

    public RegistrationPage(WebDriver driver) {
        super(driver);
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(id = "firstName")
    private WebElement firstNameField;

    @FindBy(id = "lastName")
    private WebElement lastNameField;

    @FindBy(id = "userEmail")
    private WebElement userEmailField;

    @FindBy(id = "userMobile")
    private WebElement userMobileField;

    @FindBy(css = "select[formcontrolname='occupation']")
    private WebElement occupationSelect;

    @FindBy(css = "input[type='radio'][value='Male']")
    private WebElement genderMaleRadio;

    @FindBy(css = "input[type='radio'][value='Female']")
    private WebElement genderFemaleRadio;

    @FindBy(id = "userPassword")
    private WebElement userPasswordField;

    @FindBy(id = "confirmPassword")
    private WebElement confirmPasswordField;

    @FindBy(css = "input[formcontrolname='required'][type='checkbox']")
    private WebElement requiredCheckbox;

    @FindBy(id = "login")
    private WebElement registerButton;

    @FindBy(css = "[class*='toast-message']")
    private WebElement toastMessage;

    public LandingPage completeRegistration(String firstName, String lastName, String email, String phone,
                                            String occupationVisibleText, String gender, String password,
                                            boolean agreeRequiredCheckbox) {
        firstNameField.clear();
        firstNameField.sendKeys(firstName);

        lastNameField.clear();
        lastNameField.sendKeys(lastName);

        userEmailField.clear();
        userEmailField.sendKeys(email);

        userMobileField.clear();
        userMobileField.sendKeys(phone);

        if (occupationVisibleText != null && !occupationVisibleText.isEmpty()) {
            try {
                Select occ = new Select(occupationSelect);
                occ.selectByVisibleText(occupationVisibleText);
            } catch (Exception ignored) {
            }
        }

        if ("male".equalsIgnoreCase(gender)) {
            if (!genderMaleRadio.isSelected()) {
                genderMaleRadio.click();
            }
        } else if ("female".equalsIgnoreCase(gender)) {
            if (!genderFemaleRadio.isSelected()) {
                genderFemaleRadio.click();
            }
        }

        userPasswordField.clear();
        userPasswordField.sendKeys(password);

        confirmPasswordField.clear();
        confirmPasswordField.sendKeys(password);

        if (requiredCheckbox != null) {
            boolean isSelected = requiredCheckbox.isSelected();
            if (agreeRequiredCheckbox != isSelected) {
                requiredCheckbox.click();
            }
        }

        registerButton.click();
        waitForWebElementToAppear(toastMessage);
        return new LandingPage(driver);
    }

    public String getToastMessage() {
        waitForWebElementToAppear(toastMessage);
        return toastMessage.getText();
    }
}
