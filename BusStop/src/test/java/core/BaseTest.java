package core;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.openqa.selenium.WebDriver;
import org.testng.Reporter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import core.factories.PropertiesFactory;
import core.factories.WebDriverFactory;
import busStop.SchedulePage;
import busStop.SchedulePageHelper;

public class BaseTest {
	WebDriver driver;

	protected SchedulePage SchedulePageComp;
	protected SchedulePageHelper schedulePageHelper;
	
//	private String browserVer;
//	private String browserName;

	@Parameters({ "browserName","browserVer" })
	@BeforeMethod
	public void SetUp(String browserName,@Optional String browserVer) throws MalformedURLException {
		//this.browserVer = browserVer;
		driver = new RemoteWebDriver(new URL("http://localhost:4446/wd/hub"),WebDriverFactory.getBrowserProperties(browserName,browserVer));
		SchedulePageComp = new SchedulePage(driver);
		schedulePageHelper = new SchedulePageHelper(driver);
		driver.get(PropertiesFactory.getInstance("testing.properties").getProperty(Constants.URL_PROP_NAME));
	}


	@AfterMethod
	public void TearDown() {
		driver.close();
		driver.quit();
	}

	@DataProvider(name = "testData")
	public Object[][] getTestData(Method testMethod) {
		Object[][] testData = null;
		String a = testMethod.getName();
		String b = testMethod.getDeclaringClass().getSimpleName();
		int numberOfParameters = testMethod.getParameterTypes().length;
		String path = "./" + b + ".xlsx";

		try {
			FileInputStream file = new FileInputStream(path);
			XSSFWorkbook workbook = new XSSFWorkbook(file);
			XSSFSheet sheet = workbook.getSheet(a);
			testData = fillData(sheet, 1, numberOfParameters);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return testData;
	}

	private static String[][] fillData(Sheet sheet, int startColumn,
			int endColumn) {
		if (startColumn == 0)
			throw new RuntimeException("startColumn value should begin from 1");
		if (endColumn < startColumn)
			throw new RuntimeException(
					"endColumn number cannot be less than startColumn numbere");
		int rows = sheet.getPhysicalNumberOfRows();
		int numberOfParameters = endColumn - startColumn + 1;
		String[][] returnValue = new String[rows - 1][numberOfParameters];
		for (int i = 1; i < rows; i++) {
			Row row = sheet.getRow(i);
			int jj = 0;
			for (int j = startColumn - 1; j < endColumn; j++) {
				int cellType;
				try {
					cellType = row.getCell(j).getCellType();
				} catch (NullPointerException ex) {
					returnValue[i - 1][jj] = "";
					continue;
				}
				String str;
				switch (cellType) {
				case Cell.CELL_TYPE_NUMERIC:
					if (DateUtil.isCellDateFormatted(row.getCell(j))) {
						returnValue[i - 1][jj] = String.valueOf(row.getCell(j)
								.getDateCellValue());
					} else {
						returnValue[i - 1][jj] = String.valueOf(row.getCell(j)
								.getNumericCellValue());
						returnValue[i - 1][jj] = returnValue[i - 1][jj]
								.replaceAll(".0", "");
					}
					break;

				case Cell.CELL_TYPE_STRING:
					str = row.getCell(j).getStringCellValue().trim();
					returnValue[i - 1][jj] = str;
					break;

				case Cell.CELL_TYPE_BOOLEAN:
					returnValue[i - 1][jj] = Boolean.toString(row.getCell(j)
							.getBooleanCellValue());
					break;

				default:
					returnValue[i - 1][jj] = "";
				}
				jj++;
			}
		}
		return returnValue;
	}
}
