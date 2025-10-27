package vishwalearning.TestComponents;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
//import java.io.FileInputStream;
import java.time.Duration;
//import java.util.Properties;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.github.bonigarcia.wdm.WebDriverManager;
import vishwalearning.pageobjects.LandingPage;

public class BaseTest {

	WebDriver driver;
	public LandingPage landingpage;
	
	public WebDriver initializeBrowser() {
		
		/*Properties prop = new Properties();
		FileInputStream fis = new FileInputStream(System.getProperty("user.dir")+"//src//main//java//vishwalearning//TestComponents//GlobalData.Properties");
		prop.load(fis);
		String BrowserName = prop.getProperty("browser");
		
		if(BrowserName.equalsIgnoreCase("chrome")) {
			WebDriverManager.chromedriver().setup();
			driver = new ChromeDriver();
		}
		else if(BrowserName.equalsIgnoreCase("firefox")) {
			
		}
		else if(BrowserName.equalsIgnoreCase("edge")) {
			
		} */
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		driver.manage().window().maximize();
		return driver;
	}
	
	public List<HashMap<String, String>> getJsonDataToMap(String filePath) throws IOException {
		
	//read Json File as String
		String JsonContent = FileUtils.readFileToString(new File(filePath),StandardCharsets.UTF_8);

		//StandardCharsets.UTF_8
		//String to HashMap
		ObjectMapper mapper = new ObjectMapper();
		List<HashMap<String, String>> data = mapper.readValue(JsonContent, new TypeReference<List<HashMap<String, String>>>(){
		});
		return data;
	}
	
	@BeforeTest(alwaysRun=true)
	public LandingPage launchApplication() {
		driver = initializeBrowser();
		landingpage = new LandingPage(driver);
		landingpage.goTo();
		return landingpage;
	}
	
	@AfterTest(alwaysRun=true)
	public void tearDown() {
		driver.close();
	}
}
