package vishwalearning;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import io.github.bonigarcia.wdm.WebDriverManager;

public class CreateAccount {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		WebDriverManager.chromedriver().setup();
		WebDriver driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
		
		driver.get("https://rahulshettyacademy.com/client");
		driver.manage().window().maximize();
		driver.findElement(By.className("text-reset")).click();
		driver.findElement(By.id("firstName")).sendKeys("Vishwanath");
		driver.findElement(By.cssSelector("input[formcontrolname='lastName']")).sendKeys("Channaveerappa");
		driver.findElement(By.xpath("//input[@placeholder='email@example.com']")).sendKeys("visuskc@gmail.com");
		driver.findElement(By.id("userMobile")).sendKeys("9902544885");
		driver.findElement(By.cssSelector("select[formcontrolname='occupation']")).click();
		driver.findElement(By.xpath("//option[@value='3: Engineer']")).click();
		driver.findElement(By.cssSelector("input[value='Male']")).click();
		driver.findElement(By.xpath("//input[@type='password']")).sendKeys("Visu00))");
		driver.findElement(By.id("confirmPassword")).sendKeys("Visu00))");
		driver.findElement(By.cssSelector("input[type='checkbox']")).click();
		//driver.findElement(By.xpath("input[type='submit']")).click();
		driver.findElement(By.className("login-btn")).click();
		String welcomeMsg = driver.findElement(By.className("login-section-wrapper")).getText();
		System.out.println("welcomeMessge : " + welcomeMsg);
	}

}
