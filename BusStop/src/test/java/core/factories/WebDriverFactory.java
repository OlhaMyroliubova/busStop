package core.factories;

import java.net.MalformedURLException;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import core.Constants;

public class WebDriverFactory {
	private static WebDriver driver;

	public static WebDriver initDriver(String browserName) {
		if (browserName.contentEquals("chrome")) {
			System.setProperty("webdriver.chrome.driver",
					PropertiesFactory.getInstance(Constants.PROP_FILE_NAME)
							.getProperty(Constants.DRIVER_PATH_PROP_NAME));
			driver = new ChromeDriver();
			driver.manage().timeouts()
					.implicitlyWait(Constants.DRIVER_WAIT, TimeUnit.SECONDS);
			driver.manage().window().maximize();
			driver.manage().deleteAllCookies();
			return driver;

		}

		else {
			driver = new FirefoxDriver();
			driver.manage().timeouts()
					.implicitlyWait(Constants.DRIVER_WAIT, TimeUnit.SECONDS);
			driver.manage().window().maximize();
			return driver;
		}
	}

	public static DesiredCapabilities getBrowserProperties(String browserName,
			String... browserVer) throws MalformedURLException {

		if (browserName.equals("chrome")) {
			System.setProperty("webdriver.chrome.driver", "./resources/ChromeDriver/chromedriver.exe");
			DesiredCapabilities capabilities = DesiredCapabilities.chrome();
			return capabilities;
		} else if (browserName.equals("ie")) {
			DesiredCapabilities capabilities = DesiredCapabilities.internetExplorer();
			return capabilities;
		} else {
			DesiredCapabilities capabilities = DesiredCapabilities.firefox();
			if (browserVer[0] != null) {
				System.out.printf("Version browser - [%s]%n", browserVer[0]);
				capabilities.setCapability(CapabilityType.VERSION,
						browserVer[0]);
				capabilities.setCapability(CapabilityType.BROWSER_NAME,
						"firefox");
			}
			return capabilities;
		}

	}

}
