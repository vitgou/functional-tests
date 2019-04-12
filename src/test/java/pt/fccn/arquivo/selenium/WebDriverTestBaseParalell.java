/*
    Copyright (C) 2011 David Cruz <david.cruz@fccn.pt>
    Copyright (C) 2011 SAW Group - FCCN <saw@asa.fccn.pt>

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package pt.fccn.arquivo.selenium;

import java.net.URL;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.rules.TestName;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriver.Timeouts;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.opera.OperaDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import com.saucelabs.common.SauceOnDemandAuthentication;
import com.saucelabs.common.SauceOnDemandSessionIdProvider;
import com.saucelabs.junit.ConcurrentParameterized;
import com.saucelabs.junit.SauceOnDemandTestWatcher;

import pt.fccn.arquivo.util.AppendableErrorsBaseTest;

//import org.json.*;

/**
 * The base class for tests using WebDriver to test specific browsers. This test
 * read system properties to know which browser to test or, if tests are te be
 * run remotely, it also read login information and the browser, browser version
 * and OS combination to be used.
 *
 * The WebDriver tests provide the more precise results without the restrictions
 * present in selenium due to browers' security models.
 */
@Ignore
@RunWith(ConcurrentParameterized.class)
public class WebDriverTestBaseParalell extends AppendableErrorsBaseTest implements SauceOnDemandSessionIdProvider {
	private String port = System.getProperty("test.remote.access.port");

	public static String seleniumURI;

	public static String buildTag;
	/**
	 * Constructs a {@link SauceOnDemandAuthentication} instance using the supplied
	 * user name/access key. To use the authentication supplied by environment
	 * variables or from an external file, use the no-arg
	 * {@link SauceOnDemandAuthentication} constructor.
	 */
	public SauceOnDemandAuthentication authentication = new SauceOnDemandAuthentication();

	/**
	 * JUnit Rule which will mark the Sauce Job as passed/failed when the test
	 * succeeds or fails.
	 */
	@Rule
	public SauceOnDemandTestWatcher resultReportingTestWatcher = new SauceOnDemandTestWatcher(this, authentication);

	@Rule
	public TestName name = new TestName() {
		public String getMethodName() {
			return String.format("%s", super.getMethodName());
		}
	};

	/**
	 * Test decorated with @Retry will be run X times in case they fail using this
	 * rule.
	 */
	@Rule
	public RetryRule rule = new RetryRule(2);

	/**
	 * Represents the browser to be used as part of the test run.
	 */
	protected String browser;
	/**
	 * Represents the operating system to be used as part of the test run.
	 */
	protected String os;
	/**
	 * Represents the version of the browser to be used as part of the test run.
	 */
	protected String version;
	/**
	 * Represents the deviceName of mobile device
	 */
	protected String deviceName;
	/**
	 * Represents the device-orientation of mobile device
	 */
	protected String deviceOrientation;
	/**
	 * Instance variable which contains the Sauce Job Id.
	 */
	protected String sessionId;

	protected WebDriver driver;
	// protected static ArrayList<WebDriver> drivers;

	protected static String screenResolution;
	protected static String testURL;
	protected static String browserVersion;
	protected static String titleOfFirstResult;

	@Deprecated
	protected static String pre_prod = "preprod";

	protected final boolean isPreProd;

	public WebDriverTestBaseParalell(String os, String version, String browser, String deviceName,
			String deviceOrientation) {
		super();
		this.os = os;
		this.version = version;
		this.browser = browser;
		this.deviceName = deviceName;
		this.deviceOrientation = deviceOrientation;
		testURL = System.getProperty("test.url");
		isPreProd = testURL.contains(pre_prod);
		screenResolution = System.getProperty("test.resolution");

		System.out.println("OS: " + os);
		System.out.println("Version: " + version);
		System.out.println("Browser: " + browser);
		System.out.println("Device: " + deviceName);
		System.out.println("Orientation: " + deviceOrientation);
	}

	/**
	 * @return a LinkedList containing String arrays representing the browser
	 *         combinations the test should be run against. The values in the String
	 *         array are used as part of the invocation of the test constructor
	 */
	@ConcurrentParameterized.Parameters
	public static LinkedList<String[]> browsersStrings() {
		String browsersJSON = System.getenv("SAUCE_ONDEMAND_BROWSERS");
		LinkedList<String[]> browsers = new LinkedList<String[]>();

		System.out.println("JSON: " + browsersJSON);

		JSONObject browsersJSONObject = new JSONObject("{browsers:" + browsersJSON + "}");

		if (browsersJSON == null) {
			System.out.println("You did not specify browsers, testing with latest firefox and chrome...");
			browsers.add(new String[] { "Windows 8.1", "latest", "chrome", null, null });
			browsers.add(new String[] { "Windows 10", "latest", "firefox", null, null });
		} else {
			JSONArray browsersJSONArray = browsersJSONObject.getJSONArray("browsers");
			for (int i = 0; i < browsersJSONArray.length(); i++) {
				// TODO:: find names of extra properties for mobile Devices such as orientation
				// and device name
				JSONObject browserConfigs = browsersJSONArray.getJSONObject(i);
				String browserOS = browserConfigs.getString("os");
//				String browserPlatform = browserConfigs.getString("platform");
				String browserName = browserConfigs.getString("browser");
				String browserVersion = browserConfigs.getString("browser-version");
				String device = null;
				String deviceOrientation = null;
				try {
					device = browserConfigs.getString("device");
					deviceOrientation = browserConfigs.getString("device-orientation");
				} catch (JSONException e) {
					/* Intentionally empty */}
				browsers.add(new String[] { browserOS, browserVersion, browserName, device, deviceOrientation });
			}
		}

		// windows xp, IE 8
		// browsers.add(new String[]{"Windows XP", "8", "internet explorer", null,
		// null});

		// OS X 10.8, Safari 6
		// browsers.add(new String[]{"OSX 10.8", "6", "safari", null, null});

		return browsers;
	}

	/**
	 * Constructs a new {@link RemoteWebDriver} instance which is configured to use
	 * the capabilities defined by the {@link #browser}, {@link #version} and
	 * {@link #os} instance variables, and which is configured to run against
	 * ondemand.saucelabs.com, using the username and access key populated by the
	 * {@link #authentication} instance.
	 *
	 * @throws Exception if an error occurs during the creation of the
	 *                   {@link RemoteWebDriver} instance.
	 */
	@Before
	public void setUp() throws Exception {
		DesiredCapabilities capabilities = new DesiredCapabilities();

		if (browser != null)
			capabilities.setCapability(CapabilityType.BROWSER_NAME, browser);
		if (version != null)
			capabilities.setCapability(CapabilityType.VERSION, version);
		if (deviceName != null)
			capabilities.setCapability("deviceName", deviceName);
		if (deviceOrientation != null)
			capabilities.setCapability("device-orientation", deviceOrientation);

		capabilities.setCapability(CapabilityType.PLATFORM, os);
//		capabilities.setCapability(CapabilityType.PLATFORM, "ANY");

		String methodName = name.getMethodName() + " " + browser + " " + version;
		capabilities.setCapability("name", methodName);

		System.out.println("Screen Resolution: " + screenResolution);
		if (!screenResolution.equals("no")) {
			capabilities.setCapability("screenResolution", screenResolution);
		}
		// Getting the build name.
		// Using the Jenkins ENV var. You can use your own. If it is not set test will
		// run without a build id.
		/*
		 * if (buildTag != null) { capabilities.setCapability("build", buildTag); }
		 */
		capabilities.setCapability("build", System.getenv("JOB_NAME") + "__" + System.getenv("BUILD_NUMBER"));

		SauceHelpers.addSauceConnectTunnelId(capabilities);

		URL url = new URL("http://" + authentication.getUsername() + ":" + authentication.getAccessKey()
				+ /* seleniumURI */ "@127.0.0.1:" + port + "/wd/hub");
		System.out.println(url);

		this.driver = new RemoteWebDriver(url, capabilities);
		this.driver.get(testURL);

		this.sessionId = (((RemoteWebDriver) driver).getSessionId()).toString();

		String message = String.format("SauceOnDemandSessionID=%1$s job-name=%2$s", this.sessionId, methodName);
		System.out.println(message);

		Timeouts timeouts = driver.manage().timeouts();
		// it isn't working on latest firefox
//		timeouts.pageLoadTimeout(25, TimeUnit.SECONDS);
		timeouts.implicitlyWait(5, TimeUnit.SECONDS);
		timeouts.setScriptTimeout(5, TimeUnit.SECONDS);

		System.out.println(String.format("Start running test: %s\n", this.getClass().getSimpleName()));
	}

	/**
	 * Releases the resources used for the tests, i.e., It closes the WebDriver.
	 */
	@After
	public void tearDown() throws Exception {
		driver.quit();
		super.tearDown();
	}

	/**
	 * Creates a Local WebDriver given a string with the web browser name.
	 *
	 * @param browser The browser name for the WebDriver initialization
	 * @return The initialized Local WebDriver
	 */
	private static WebDriver selectLocalBrowser(String browser) throws java.net.MalformedURLException {
		WebDriver driver = null;
		if (browser.contains("firefox")) {
			driver = new FirefoxDriver();
		} else if (browser.contains("iexplorer")) {
			driver = new InternetExplorerDriver();
		} else if (browser.contains("chrome")) {
			// DesiredCapabilities capabilities = DesiredCapabilities.chrome();
			// capabilities.setCapability("chrome.binary",
			// "/usr/lib/chromium-browser/chromium-browser");
			// driver = new ChromeDriver(capabilities);
			driver = new ChromeDriver();
		} else if (browser.contains("opera")) {
			driver = new OperaDriver();
		} else if (browser.contains("remote-chrome")) {
			DesiredCapabilities capabilities = DesiredCapabilities.chrome();
			driver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), capabilities);
		} else if (browser.contains("remote-firefox")) {
			DesiredCapabilities capabilities = DesiredCapabilities.firefox();
			driver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), capabilities);
			driver.get("http://www.google.com");
		} else {
			// OH NOEZ! I DOAN HAZ DAT BROWSR!
			System.err.println("Cannot find suitable browser driver for [" + browser + "]");
		}
		return driver;
	}

	/**
	 * Gets a suitable Platform object given a OS/Platform string..
	 *
	 * @param platformString The given string for the OS/Platform to use
	 * @return The Platform object that represent the requested OS/Platform
	 */
	private static Platform selectPlatform(String platformString) {
		Platform platform = null;

		if (platformString.contains("Windows")) {
			if (platformString.contains("2008")) {
				platform = Platform.VISTA;
			} else {
				platform = Platform.XP;
			}
		} else if (platformString.toLowerCase().equals("linux")) {
			platform = Platform.LINUX;
		} else {
			System.err.println("Cannot find a suitable platform/OS for [" + platformString + "]");
		}
		return platform;
	}

	/**
	 * Miscellaneous cleaning for browser and browser's version strings.
	 *
	 * @param browser        The browser string to clean
	 * @param browserVersion The browser version string to clean
	 */
	private static void parameterCleanupForRemote(String browser, String browserVersion) {
		// Selenium1 likes to prepend a "*" to browser string.
		if (browser.startsWith("*")) {
			browser = browser.substring(1);
		}

		// SauceLabs doesn't use version numbering for Google Chrome due to
		// the fast release schedule of that browser.
		if (browser.contains("googlechrome")) {
			browserVersion = "";
		}
	}

	/**
	 * Utility class to obtain the Class name in a static context.
	 */
	public static class CurrentClassGetter extends SecurityManager {
		public String getClassName() {
			return getClassContext()[1].getName();
		}
	}

	@BeforeClass
	public static void setupClass() {
		// get the uri to send the commands to.
		seleniumURI = SauceHelpers.buildSauceUri();
		// If available add build tag. When running under Jenkins BUILD_TAG is
		// automatically set.
		// You can set this manually on manual runs.
		buildTag = System.getenv("BUILD_TAG");
	}

	/**
	 *
	 * @return the value of the Sauce Job id.
	 */
	@Override
	public String getSessionId() {
		return sessionId;
	}

	/**
	 * Checks if an element is present in the page
	 */
	protected boolean isElementPresent(By by) {
		try {
			driver.findElement(by);
			return true;
		} catch (NoSuchElementException e) {
			return false;
		}
	}

}
