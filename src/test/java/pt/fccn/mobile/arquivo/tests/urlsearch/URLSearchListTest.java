package pt.fccn.mobile.arquivo.tests.urlsearch;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import pt.fccn.arquivo.selenium.Retry;
import pt.fccn.arquivo.selenium.WebDriverTestBaseParalell;

/**
 *
 * @author Ivo Branco <ivo.branco@fccn.pt>
 *
 */
public class URLSearchListTest extends WebDriverTestBaseParalell {

	public URLSearchListTest(String os, String version, String browser, String deviceName, String deviceOrientation) {
		super(os, version, browser, deviceName, deviceOrientation);
	}

	@Test
	@Retry
	public void urlSearchPTTest() {
		urlSearchListTest("pt", "fccn.pt", "Lista", "versão");
	}

	@Test
	@Retry
	public void urlSearchENTest() {
		urlSearchListTest("en", "fccn.pt", "Lista", "version");
	}

	private void urlSearchListTest(String language, String url, String listText, String versionLabel) {
		driver.get(testURL + "?l=" + language);

		run("Search fccn.pt url", () -> {
			driver.findElement(By.id("txtSearch")).clear();
			driver.findElement(By.id("txtSearch")).sendKeys(url);
			driver.findElement(By.xpath("//*[@id=\"buttonSearch\"]/button")).click();
		});

		run("Change to list mode if not in it", () -> {
			WebElement resultsGridCurrentType = driver.findElementByXPath("//*[@id=\"layoutTV\"]/h4");
			if (!resultsGridCurrentType.getText().contains(listText)) {
				driver.findElementByXPath("//*[@id=\"layoutTV\"]/button").click();
			}
		});

		run("Check if first version match", () -> {
			WebElement yearTableHeader = driver.findElementByXPath("//*[@id=\"th_1996\"]");
			assertNotNull("Verify if year table header exist", yearTableHeader);

			WebElement yearWebElement = yearTableHeader.findElement(By.xpath(".//div[1]/h4"));
			assertNotNull("Year web element not found", yearWebElement);
			assertEquals("Verify year text is correct", "1996", yearWebElement.getText().trim());

			yearTableHeader.findElement(By.xpath(".//div[2]/h4"));

			WebElement numberOfVersionsWE = yearTableHeader.findElement(By.xpath(".//div[2]/h4"));
			assertNotNull("Number of versions", numberOfVersionsWE);
			assertEquals("Verify year number of versions", "1 " + versionLabel, numberOfVersionsWE.getText().trim());
		});
	}

}
