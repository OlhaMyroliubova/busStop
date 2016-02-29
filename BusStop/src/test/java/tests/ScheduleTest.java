package tests;

import org.testng.Reporter;
import org.testng.annotations.Test;

import core.BaseTest;
import core.Constants;

public class ScheduleTest extends BaseTest {

	@Test(dataProvider = "testData")
	public void checkTime(String station, String day, String time,
			String expectedTime) throws InterruptedException {

		Thread.sleep(15000);

		Reporter.log(String.format(Constants.HEADER,
				"Check time for random station"));

		Reporter.log(String.format(Constants.STEP,
				"Choose station,day and time"));

		schedulePageHelper.swithToTargetFrame(Constants.STATIONS_LIST_FRAME);
		schedulePageHelper.chooseStation(SchedulePageComp, station);

		Reporter.log(String.format(Constants.STEP, "Check time in schedule"));

		schedulePageHelper.swithToTargetFrame(Constants.BUS_SCHEDULE_FRAME);

		schedulePageHelper.checkTime(expectedTime, time, schedulePageHelper
				.chooseDayAndHour(SchedulePageComp, day, time));

		Thread.sleep(15000);

	}
}
