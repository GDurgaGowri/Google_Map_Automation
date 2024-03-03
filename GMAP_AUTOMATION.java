package googlemapautomation;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class MainAutomation { 

	public static final By
	SEARCH_BOX_INPUT = By.id("searchboxinput"),
	SEARCH_BOX_SEARCH_BUTTON = By.id("searchbox-searchbutton"),
	DIRECTIONS_BUTTON = By.cssSelector("[data-value='Directions']"),
	FROM_LOCATION_SEARCH_BOX = By.cssSelector("#sb_ifc50 .tactile-searchbox-input"),
	FROM_LOCATION_SEARCH_BUTTON = By.xpath("/html/body/div[1]/div[3]/div[8]/div[3]/div[1]/div[2]/div/div[3]/div[1]/div[1]/div[2]/button[1]"),
	DIRECTION_RESULT_CONTAINER = By.cssSelector(".UgZKXd.clearfix.yYG3jf"),
	RESULT_DISTANCE = By.cssSelector(".ivN21e.tUEI8e.fontBodyMedium"),
	RESULT_DURATION = By.cssSelector(".Fk3sm.fontHeadlineSmall"),
	VEHICLE_TYPE = By.cssSelector(".Os0QJc.google-symbols")
	;
	
	
	private static void searchPlace(WebDriver driver, String search_place) throws InterruptedException 
	{
        WebElement place = waitTillDisplayed(driver, SEARCH_BOX_INPUT);
        place.sendKeys(search_place);
        clickElement(driver, SEARCH_BOX_SEARCH_BUTTON);
	}
	 
	private static void clickDirections(WebDriver driver) throws InterruptedException 
	{
        clickElement(driver, DIRECTIONS_BUTTON);
    }
	
	private static void findDirections(WebDriver driver, String from_location) throws InterruptedException 
	{
        WebElement find = waitTillDisplayed(driver, FROM_LOCATION_SEARCH_BOX);
        find.sendKeys(from_location);
        clickElement(driver, FROM_LOCATION_SEARCH_BUTTON);
    }
	
	private static void printResults(WebDriver driver) throws InterruptedException 
	{
		waitTillDisplayed(driver, DIRECTION_RESULT_CONTAINER);
        List<WebElement> container_elements = driver.findElements(DIRECTION_RESULT_CONTAINER);
        
        for (WebElement element : container_elements)
        {
        	if(!(element.findElement(RESULT_DURATION).getText().equals("") || element.findElement(RESULT_DURATION).getText().equals("")))
        	{
        		System.out.println("Vehicle Type : " + element.findElement(VEHICLE_TYPE).getAttribute("aria-label"));

			  if(isElementPresent(element, RESULT_DISTANCE)) 
			  {
				  System.out.println(element.findElement(RESULT_DISTANCE).getText()); 
			  }
			  
			  if(isElementPresent(element, RESULT_DURATION)) 
			  {
				  System.out.println(element.findElement(RESULT_DURATION).getText()); 
			  }
        	}
        }
    }
	
	private static void clickElement(WebDriver driver, By locator) throws InterruptedException 
	{
		WebElement click_element = waitTillDisplayed(driver, locator);
        click_element.click();
    }
	
	private static WebElement waitTillDisplayed(WebDriver driver, By locator) throws InterruptedException 
	{
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }
	
	private static boolean isElementPresent(WebElement element, By locator) 
	{
        try
        {
            WebElement check_element = element.findElement(locator);
            return true;
        } 
        catch(Exception e) 
        {
            return false;
        }
    }
	
	public static void main(String[] args) throws InterruptedException 
	{
		System.setProperty("webdriver.chrome.driver", "C:\\Automation project\\chromedriver.exe");
        WebDriver driver = new ChromeDriver();
        driver.get("https://www.google.com/maps");
        driver.manage().window().maximize();
        searchPlace(driver, "chennai");
        clickDirections(driver);
        findDirections(driver,"tiruvallur");
        printResults(driver);
	}

}
