/**
 * Copyright (C) 2016 Fernando Melo <fernando.melo@fccn.pt>
 * Copyright (C) 2016 SAW Group - FCCN <saw@asa.fccn.pt>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package pt.fccn.arquivo.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.TreeMap;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import pt.fccn.arquivo.selenium.WebDriverTestBaseParalell;
import pt.fccn.arquivo.tests.util.ReplayUtils;
import pt.fccn.arquivo.tests.util.SwitchLanguage;

/**
 *
 * @author Fernando Melo
 * @author Ivo Branco <ivo.branco@fccn.pt> Major refactor
 *
 */

public class ReplayTest extends WebDriverTestBaseParalell {

	public ReplayTest(String os, String version, String browser, String deviceName, String deviceOrientation) {
		super(os, version, browser, deviceName, deviceOrientation);
	}

	private TreeMap<String, String> testURLs = new TreeMap<String, String>();
	private static final String filenamePreProd = "pre_production_URL_testlist.txt";
	private static final String filenameProd = "production_URL_testlist.txt";
	private static final String activeItem = "active";
	private static final String activeDay = "active-day";
	private String serverName = "";
	private BufferedReader br;
	private String pageUrl = "";
	private String[] tokens;
	private Properties prop;
	private BufferedReader inputPt = null;
	private BufferedReader inputEn = null;
	private String baseScreenshotURL;

	/**
	 * Do the Replay Tests for our set of URLS
	 */
	public boolean inspectURLs(String language) {
		System.out.println("Inspecting URLS language: " + language);
		if (language.equals("EN")) {
			// switch properties to english
			try {
				prop.load(inputEn);
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}

		for (Entry<String, String> entry : testURLs.entrySet()) {
			String currentURL = entry.getKey();
			String textToCheck = entry.getValue();
			String url = serverName + "wayback/" + currentURL;
			driver.get(url);

			run("Verify text on replay page", () -> ReplayUtils.checkTextOnReplayPage(driver, textToCheck));

			SwitchLanguage.switchLanguage(driver, language); // Can be optimized to only change TO PT on the first URL,
																// and all others have
			// to be in PT too
			appendError(() -> replayBarURLsOk(currentURL));
			appendError(() -> screenshotOk(currentURL));
			appendError(() -> printOk(currentURL));
			appendError(() -> facebookOk(currentURL));
			appendError(() -> twitterOk(currentURL));
			appendError(() -> emailOk(currentURL));
			appendError(() -> tableOfVersionsOk(currentURL));
			appendError(() -> logoOk(currentURL));
			appendError(() -> checkLeftMenu(currentURL));

		}
		return true;
	}

	/**
	 * Check if both the expanded URL that appears on the replayBar is correct
	 */
	public void replayBarURLsOk(String currentURL) {
		String urlWithoutDate = truncateURL(currentURL.substring(15));

		String maximzedURL = driver.findElement(By.xpath("//a[@id=\"update1\"]")).getText();
		System.out.println("URL: " + maximzedURL);

		appendError(() -> assertEquals("Check replay bar URLs", urlWithoutDate, maximzedURL));

	}

	/**
	 * Check if the screenshot URL and title are correct TODO:: Update this new
	 * version popup
	 */

	public void screenshotOk(String currentURL) {
		WebElement screenshotElement = driver.findElement(By.xpath("//a[@id=\"a_screenshot\"]"));
//		screenshotElement.click();
//
//		WebElement takeScreenshotModalButton = driver.findElement(By.xpath("//*[@id=\"takeScreenshot\"]"));
//		takeScreenshotModalButton.click();

		String screenshotURL = screenshotElement.getAttribute("href");
		String screenshotTitle = screenshotElement.getAttribute("title");

		String expectedscreenshotURL = baseScreenshotURL
				+ encodeURIComponent(serverName + "noFrame/replay/" + currentURL);
		String expectedscreenshotTitle = prop.getProperty("screenTitle");

		// TODO: need an specific URL on any element instead after element click handler
//		appendError(() -> assertEquals("Check screenshot URL", expectedscreenshotURL, screenshotURL));

		appendError(() -> assertEquals("Check screenshot title", expectedscreenshotTitle, screenshotTitle));

//		try {
//			downloadFileFromURLUsingNIO("./screenshot.png", screenshotURL);
//		} catch (Exception e) {
//			throw new RuntimeException(e);
//		}
//		appendError(() -> assertTrue("Check screenshot file size", checkFileSize("./screenshot.png")));
	}

	/**
	 * Check if the print href and title are correct TODO:: Update this new version
	 * popup
	 */
	public void printOk(String currentURL) {
		String printHref = driver.findElement(By.xpath("//li[@id=\"printLi\"]/a")).getAttribute("href");
		String printTitle = driver.findElement(By.xpath("//li[@id=\"printLi\"]/a")).getAttribute("title");

		String expectedprintHref = "javascript:getImageToPrint(\""
				+ encodeURIComponent(serverName + "noFrame/replay/" + currentURL) + "\")";
		String expectedprintTitle = prop.getProperty("printTitle");

		// TODO need refactor of pywb
//		appendError(() -> assertEquals("Check print href", expectedprintHref, printHref));

		appendError(() -> assertEquals("Check print title", expectedprintTitle, printTitle));
	}

	/**
	 * Check if the Facebook class and title are correct
	 */
	public void facebookOk(String currentURL) {
		// *[@id="facebook_share"]/a
		String faceClass = driver.findElement(By.xpath("//li[@class=\"facebook\"]/a")).getAttribute("class");
		String faceTitle = driver.findElement(By.xpath("//li[@class=\"facebook\"]/a")).getAttribute("title");

		String expectedfaceClass = "addthis_button_facebook";
		String expectedfaceTitle = prop.getProperty("faceTitle");

		appendError(() -> assertTrue("Check facebook class", faceClass.startsWith(expectedfaceClass)));
		appendError(() -> assertEquals("Check facebook title", expectedfaceTitle, faceTitle));
	}

	/**
	 * Check if the Facebook class and title are correct
	 */
	public void twitterOk(String currentURL) {
		String twitterClass = driver.findElement(By.xpath("//li[@class=\"twitter\"]/a")).getAttribute("class");
		String twitterTitle = driver.findElement(By.xpath("//li[@class=\"twitter\"]/a")).getAttribute("title");

		String expectedtwitterClass = "addthis_button_twitter";
		String expectedtwitterTitle = prop.getProperty("twitterTitle");

		appendError(() -> assertTrue("Check twitter class", twitterClass.contains(expectedtwitterClass)));
		appendError(() -> assertEquals("Check twitter title", expectedtwitterTitle, twitterTitle));
	}

	/**
	 * Check Left Menu that contains the list of versions for the URL
	 */
	public void checkLeftMenu(String currentURL) {
		String urlNoDate = currentURL.substring(15);
		String timestamp = currentURL.substring(0, 14); // complete timestamp in the 14 digit format
		String year = currentURL.substring(0, 4);
		String monthstr = currentURL.substring(4, 6);
		int monthInt = Integer.parseInt(monthstr);
		String[] months = prop.getProperty("months").split("#");
		String monthStr = months[(monthInt - 1)];
		String daystr = currentURL.substring(6, 8);
		int day = Integer.parseInt(daystr);
		String hours = currentURL.substring(8, 10);
		String minutes = currentURL.substring(10, 12);

		// Check the year in the left menu and check if it is open
		String yearClass = driver.findElement(By.xpath("//a[@id=\"" + year + "\"]")).getAttribute("class");
		String yearText = driver.findElement(By.xpath("//a[@id=\"" + year + "\"]")).getText();
		// Check the month in the left menu and check if it is open
		String monthClass = driver.findElement(By.xpath("//a[@id=\"" + year + "_" + monthstr + "\"]"))
				.getAttribute("class");
		String monthText = driver.findElement(By.xpath("//a[@id=\"" + year + "_" + monthstr + "\"]")).getText();
		// Check the day in the left menu and check if it is open
		String dayClass = driver.findElement(By.xpath("//a[@id=\"" + year + "_" + monthstr + "_" + day + "\"]"))
				.getAttribute("class");
		String dayText = driver.findElement(By.xpath("//a[@id=\"" + year + "_" + monthstr + "_" + day + "\"]"))
				.getText();
		// Check the time of the day Text and if the link is correct
		String hoursMinutesURL = driver.findElement(By.xpath("//a[@id=\"a_" + timestamp + "\"]"))
				.getAttribute("onclick").replaceAll(",%20", ", ");
		String hoursClass = driver.findElement(By.xpath("//a[@id=\"a_" + timestamp + "\"]")).getAttribute("class");
		String hoursMinutesText = driver.findElement(By.xpath("//a[@id=\"a_" + timestamp + "\"]")).getText();
		String expectedURL = "jumpToVersion('" + urlNoDate + "', '" + timestamp + "')";

		appendError(() -> assertEquals("Check year in the left menu and check if it is open", year, yearText));
		appendError(() -> assertEquals("Check year has the class", activeItem, yearClass));
		appendError(() -> assertEquals("Check month text", monthStr, monthText));
		appendError(() -> assertEquals("Check month class", activeItem, monthClass));
		appendError(() -> assertEquals("Check day text", "" + day, dayText));
		appendError(() -> assertEquals("Check day class", activeItem, dayClass));
		appendError(() -> assertEquals("Check hours and minutes text", hours + ":" + minutes, hoursMinutesText));
		appendError(() -> assertEquals("Check hours class", activeDay, hoursClass));
		appendError(() -> assertTrue("Check version URL", hoursMinutesURL.contains(expectedURL)));

	}

	/**
	 * Check if the Email title, onclick and href are correct
	 */
	public void emailOk(String currentURL) {
		String emailHref = run("Get email href", () -> URLDecoder
				.decode(driver.findElement(By.xpath("//li[@id=\"liEmail\"]/a")).getAttribute("href"), "UTF-8"));

		String emailonClick = driver.findElement(By.xpath("//li[@id=\"liEmail\"]/a")).getAttribute("onclick");
		String emailTitle = driver.findElement(By.xpath("//li[@id=\"liEmail\"]/a")).getAttribute("title");

		String expectedemailHref = "mailto:?subject=" + prop.getProperty("emailMessage") + "[sub]";
		String currentTimestamp = currentURL.substring(0, 14);
		String dateFormatted = getDateFormatted(currentTimestamp);
		System.out.println("Expected Date: " + dateFormatted);
		String expectedemailonClick = "this.href = this.href.replace(\'[sub]\',document.title + \'%0D%0A"
				+ dateFormatted + "%0D%0A %0D%0A' + window.location )";

		String expectedemailTitle = prop.getProperty("mailTitle");

		appendError(() -> assertEquals("Check email href", expectedemailHref.toLowerCase(), emailHref.toLowerCase()));

		appendError(() -> assertTrue("Check email onclick",
				emailonClick.toLowerCase().contains(expectedemailonClick.toLowerCase())));

		appendError(
				() -> assertEquals("Check email title", expectedemailTitle.toLowerCase(), emailTitle.toLowerCase()));
	}

	/**
	 * input: timestamp in the 14 digit format such as 20000823154833 output: 23
	 * Agosto, 2000
	 */
	public String getDateFormatted(String timestamp) {
		String[] months = prop.getProperty("months").split("#");

		String year = timestamp.substring(0, 4);
		String month = timestamp.substring(4, 6);
		int monthInt = Integer.parseInt(month);
		String monthStr = months[(monthInt - 1)];

		String day = timestamp.substring(6, 8);
		if (day.charAt(0) == '0') {
			day = day.substring(1, 2);
		}
		return day + " " + monthStr + ", " + year;
	}

	/**
	 * Check if the Table of Versions title and href are correct
	 */
	public void tableOfVersionsOk(String currentURL) {
		String tableOfVersionsHref = driver.findElement(By.xpath("//a[@id=\"versionsTable\"]")).getAttribute("href");
		String tableOfVersionsTitle = driver.findElement(By.xpath("//a[@id=\"versionsTable\"]")).getAttribute("title");

		String urlNoDate = currentURL.substring(15);

		String expectedtableOfVersionsHref = serverName + "search.jsp?l=" + prop.getProperty("lang") + "&query="
				+ urlNoDate + "&btnSubmit=Pesquisar";
		String expectedtableOfVersionsTitle = prop.getProperty("versionsStoredTable");

		appendError(
				() -> assertEquals("Check table of versions href", expectedtableOfVersionsHref, tableOfVersionsHref));
		appendError(
				() -> assertEquals("Check table of versions href", expectedtableOfVersionsTitle, tableOfVersionsTitle));
	}

	/**
	 * Check if the Logo exists and href is correct
	 */
	public void logoOk(String currentURL) {
		String anchorHref = driver.findElement(By.xpath("//div[@id=\"logodiv\"]/a[2]")).getAttribute("href");
		String logoAlt = driver.findElement(By.xpath("//div[@id=\"logodiv\"]/a[2]/img")).getAttribute("alt");
		String logoSrc = driver.findElement(By.xpath("//div[@id=\"logodiv\"]/a[2]/img")).getAttribute("src");

		String expectedanchorHref = serverName + "?l=" + prop.getProperty("lang");
		String optionalExpectedanchorHref = serverName.substring(0, serverName.length() - 1) + "?l="
				+ prop.getProperty("lang");
		String expectedlogoAlt = "Arquivo.pt logo";
		String expectedlogoSrc = serverName + "wayback/static/resources/img/arquivo100v.png";

		appendError(() -> assertTrue("Check Logo Anchor href",
				expectedanchorHref.equals(anchorHref) || optionalExpectedanchorHref.equals(anchorHref)));

		appendError(() -> assertEquals("Check Logo Alt", expectedlogoAlt, logoAlt));

		appendError(() -> assertEquals("Check Logo Src", expectedlogoSrc, logoSrc));
	}

	public String truncateURL(String url) {
		if (url.startsWith("https://")) {
			url = url.substring(8, url.length());
		} else if (url.startsWith("http://")) {
			url = url.substring(7, url.length());
		}
		if (url.length() > 40) {
			return url.substring(0, 26) + "..." + url.substring((url.length() - 11), url.length());
		} else
			return url;
	}

	private static void downloadFileFromURLUsingNIO(String fileName, String fileUrl) throws IOException {
		System.out.println("Download Starting");
		URL url = new URL(fileUrl);
		ReadableByteChannel rbc = Channels.newChannel(url.openStream());
		FileOutputStream fOutStream = new FileOutputStream(fileName);
		fOutStream.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
		fOutStream.close();
		rbc.close();
		System.out.println("Download Successful");
	}

	private static boolean checkFileSize(String pathToFile) {

		File file = new File(pathToFile);

		if (file.exists()) {

			double bytes = file.length();
			double kilobytes = (bytes / 1024);

			System.out.println("kilobytes : " + kilobytes);
			if (kilobytes > 200)
				return true;
			else {
				System.out.println("File too small to Be Ok Less or Equal to 200Kb");
				return false;
			}

		} else {
			System.out.println("File does not exist!");
			return false;
		}

	}

	/**
	 * Encodes the passed String as UTF-8 using an algorithm that's compatible with
	 * JavaScript's <code>encodeURIComponent</code> function. Returns
	 * <code>null</code> if the String is <code>null</code>.
	 *
	 * @param s The String to be encoded
	 * @return the encoded String
	 */
	public static String encodeURIComponent(String s) {
		String result = null;

		try {
			result = URLEncoder.encode(s, "UTF-8").replaceAll("\\+", "%20").replaceAll("\\%21", "!")
					.replaceAll("\\%27", "'").replaceAll("\\%28", "(").replaceAll("\\%29", ")")
					.replaceAll("\\%7E", "~");
		}

		// This exception should never occur.
		catch (UnsupportedEncodingException e) {
			result = s;
		}

		return result;
	}

	@Test
//	@Retry
	public void replayTestPT() {
		init();
		inspectURLs("PT");
	}

	@Test
//	@Retry
	public void replayTestEN() {
		init();
		inspectURLs("EN");
	}

	private void init() {
		System.out.println("[ReplayPage]");
		// driver.manage().window().setSize(new Dimension(1280, 768));
		br = null;
		pageUrl = driver.getCurrentUrl();
		tokens = pageUrl.split("/");
		serverName = "https://" + tokens[2] + "/";
		baseScreenshotURL = serverName + "screenshot/?url=";
		prop = new Properties();
		System.out.println("[ReplayPage] read properties");
		try {
			inputPt = new BufferedReader(new InputStreamReader(ClassLoader.getSystemResourceAsStream("pt.properties"), "UTF8"));
			inputEn = new BufferedReader(new InputStreamReader(ClassLoader.getSystemResourceAsStream("en.properties"), "UTF8"));
			prop.load(inputPt);
			// start with properties in PT
			String currentLine;
			br = new BufferedReader(new InputStreamReader(ClassLoader.getSystemResourceAsStream(this.isPreProd ? filenamePreProd : filenameProd)));
			System.out.println("[ReplayPage] read testURLs");
			while ((currentLine = br.readLine()) != null) {

				String[] parts = currentLine.split("\t");
				/* parts[0] is the timestamp/url */
				/* parts[1] is the expected title for the parts[0] url */
				testURLs.put(parts[0], parts[1]);
			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null)
					br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
