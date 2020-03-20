package pt.fccn.mobile.arquivo.tests.pagesearch;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import pt.fccn.arquivo.selenium.Retry;
import pt.fccn.arquivo.selenium.WebDriverTestBaseParalell;

/**
 * 
 * @author pedro.gomes.fccn@gmail.com
 *
 */

public class PageAdvancedSearchNegationOptionTest extends WebDriverTestBaseParalell {

	public PageAdvancedSearchNegationOptionTest(String os, String version, String browser, String deviceName,
			String deviceOrientation) {
		super(os, version, browser, deviceName, deviceOrientation);

	}
	
	@Test
	@Retry
	public void testPageAdvancedSearchNegationOption() throws Exception {
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
		
		appendError("Insert the negation option on form field", () -> driver.findElement(By.id("adv_not")).sendKeys("Fundação"));
		
		appendError("Click on search on arquivo.pt button", () -> driver.findElement(By.id("btnSubmitBottom")).click());
		 
		appendError(() -> assertEquals("Verify if the - operator is on text box",
				"fccn -Fundação",
				driver.findElement(By.id("txtSearch")).getAttribute("value").trim()));
		
		assertThat("Verify if the term fccn is displayed on any search result",
				driver.findElement(By.xpath("//*[@id=\"resultados-lista\"]/ul/li[3]/div[2]/span")).getText(), containsString("fccn"));
		
		appendError("Verify if any of the search results contains the visible text Fundação", () -> new WebDriverWait(driver, 20)
				.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//*[@id=\"resultados-lista\"]/ul/li[3]/div[2]/span[contains(text(),'Fundação')]"))));
	}

}
