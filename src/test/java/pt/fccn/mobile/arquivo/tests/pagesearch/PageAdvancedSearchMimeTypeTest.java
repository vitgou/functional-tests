package pt.fccn.mobile.arquivo.tests.pagesearch;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.Select;

import pt.fccn.arquivo.selenium.Retry;
import pt.fccn.arquivo.selenium.WebDriverTestBaseParalell;

/**
 * 
 * @author pedro.gomes.fccn@gmail.com
 *
 */

public class PageAdvancedSearchMimeTypeTest extends WebDriverTestBaseParalell {

	public PageAdvancedSearchMimeTypeTest(String os, String version, String browser, String deviceName,
			String deviceOrientation) {
		super(os, version, browser, deviceName, deviceOrientation);
	}

	@Test
	@Retry
	public void testPageAdvancedSearchMimeType() throws Exception {
		run("Search FCCN term", () -> {
			driver.findElement(By.id("txtSearch")).clear();
			driver.findElement(By.id("txtSearch")).sendKeys("fccn");
			driver.findElement(By.xpath("//*[@id=\"buttonSearch\"]/button")).click();
		});
		
		run("Click on advanced search link to navigate to advanced search page",
				() -> waitUntilElementIsVisibleAndGet(By.id("advancedSearchButton")).click());
		
		appendError(() -> {
			assertEquals("Check if search words maintain fccn term", "fccn",
					driver.findElement(By.id("adv_and")).getAttribute("value"));
		});
		
		appendError("Open format type", () -> driver.findElement(By.id("formatType")).click());

		Select dropdown = new Select(driver.findElement(By.id("formatType")));
		
		appendError("Set format type",
				() -> dropdown.selectByValue("pdf"));

		run("Close format box", () -> driver
				.findElement(
						By.xpath("//*[@id=\"formatType\"]/ancestor::div[contains(@class, 'expandable-div')]"))
				.click());
		
		appendError("Click on search on arquivo.pt button", () -> driver.findElement(By.id("btnSubmitBottom")).click());
		
		appendError(() -> assertEquals("Verify if the - operator is on text box",
				"fccn type:pdf",
				driver.findElement(By.id("txtSearch")).getAttribute("value").trim()));
		
		assertThat("Verify if the term fccn is displayed on any search result",
				driver.findElement(By.xpath("//*[@id=\"resultados-lista\"]/ul/li[1]/div[2]/span")).getText(), containsString("fccn"));
		
		appendError(() -> assertEquals("Check mime of first result", "[PDF]",
				driver.findElement(By.xpath("//*[@id=\"resultados-lista\"]/ul/li/div[1]/a/h2/span")).getText()));
	}
}
