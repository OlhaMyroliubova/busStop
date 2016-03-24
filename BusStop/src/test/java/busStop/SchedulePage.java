package busStop;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;


public class SchedulePage {
	private WebDriver driver;
	
	private final String STATIONS_LIST="//tr[not(@bgcolor='#EFF7FF')]//li//a[@target='R']";
	private final String HOURS_LIST="//td[@class='cellhour'][%s]";
	private final String MINUTES_LIST="(//td[@class='cellmin'][%s]/font)[%s]";

//vew line
	//line for second commit 

	@FindBy(xpath=STATIONS_LIST)
	public List<WebElement> listOfStations;

	public List<WebElement> getHoursList(String index){
		String id=String.format(HOURS_LIST, index);
		return driver.findElements(By.xpath(id));
	}
	
	public WebElement getMinutesList(String indexDay,String indexHour){
		return driver.findElement(By.xpath(String.format(MINUTES_LIST, indexDay,indexHour)));
	}
	
	public SchedulePage(WebDriver driver){
		this.driver=driver;
		///fact
		PageFactory.initElements(driver, this);
		}

}
