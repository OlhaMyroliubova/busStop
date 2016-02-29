package busStop;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.Reporter;

import core.Constants;

public class SchedulePageHelper {
	private WebDriver driver;
	
	public SchedulePageHelper(WebDriver driver) {
		this.driver = driver;
	}
	
	  public void swithToTargetFrame(String value){
		  Reporter.log("Switching to ["+value+"] Frame");

			 driver.switchTo().defaultContent();
			 driver.switchTo().frame(value);
			
	  }
	  
	
	public void chooseStation(SchedulePage schedulePageComp,String station){
		Reporter.log(String.format(Constants.TITLE, "Click on ["+station+"] station"));
		for(WebElement element:schedulePageComp.listOfStations){
			if(element.getText().equals(station)){
				element.click();
				break;
			}
		}
		Reporter.log(String.format(Constants.POSITIVE, "["+station+"] was chosen"));
	}
	

	public WebElement chooseDayAndHour(SchedulePage schedulePageComp,String day,String hour){
		Reporter.log(String.format(Constants.TITLE, "Finding ["+day+"] in dayList"));

		List<WebElement> hours=null;
		String dayIndex = "";
		int i=0;
		switch(day){
		case Constants.WORKDAY:
			dayIndex = "1";
			hours=schedulePageComp.getHoursList("1");
			break;
		case Constants.SATURDAY:
			dayIndex = "2";
			hours=schedulePageComp.getHoursList("2");
			break;
		case Constants.SUNDAY:
			dayIndex = "3";
			hours=schedulePageComp.getHoursList("3");
			break;
		}
		
		Reporter.log(String.format(Constants.POSITIVE, "["+day+"] was found"));
		
		Reporter.log(String.format(Constants.TITLE, "Finding ["+hour+"] hour was found"));

		for(WebElement element:hours){
			i++;
			if(element.getText().equals(hour)){
				break;
			}
		}
		Reporter.log("["+hour+"] hour was found",true);
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return schedulePageComp.getMinutesList(dayIndex,String.valueOf(i));
	}
	
	public void checkTime(String expectedTime,String hour,WebElement minutes){
		Reporter.log("Checking text in schedule equals ["+expectedTime+"]",true);

		String min[]=minutes.getText().split(" ");
		String actualTime="";
		for(int i=0;i<min.length;i++){
			actualTime+=hour+":"+min[i];
			if(i+1!=min.length){
				actualTime+=", ";
			}
		}
		Assert.assertEquals(actualTime, expectedTime,"Time is wrong");
		
		Reporter.log(String.format(Constants.POSITIVE, "text is correct"));

	}
	

}
