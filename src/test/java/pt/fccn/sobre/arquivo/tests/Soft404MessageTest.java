package pt.fccn.sobre.arquivo.tests;

import static org.junit.Assert.assertEquals;

import java.util.List;
import java.util.Locale;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import pt.fccn.arquivo.selenium.Retry;
import pt.fccn.arquivo.selenium.WebDriverTestBaseParalell;
import pt.fccn.mobile.arquivo.utils.LocaleUtils;
import pt.fccn.mobile.arquivo.utils.LocalizedString;
import pt.fccn.sobre.arquivo.pages.IndexSobrePage;


/**
 * 
 * @author pedro.gomes.fccn@gmail.com
 *
 */

public class Soft404MessageTest extends WebDriverTestBaseParalell {

	private static final String WAYBACK_404_PAGE_EXAMPLE = "/about-the-archive/publications-1/documents/peopleware-rarc-article-in-jpg/image_preview";
	
	public Soft404MessageTest(String os, String version, String browser, String deviceName, String deviceOrientation) {
		super(os, version, browser, deviceName, deviceOrientation);
		
	}
	
	@Test
	@Retry
	public void soft404MessageTest() {
		
		//The test does not work in preprod.sobre.arquivo.pt. Since the Arquivo.pt only collect the sobre.arquivo.pt.
		if (!this.testURL.toLowerCase().contains("preprod.sobre.arquivo.pt")) {
			driver.get(this.testURL + WAYBACK_404_PAGE_EXAMPLE);
			
			waitUntilElementIsVisibleAndGet(By.id("post-9818"));
			 
			//The language of the page is not based on the "English or Portugues" button, but depends on the language of the search engine. 
			appendError(() -> assertEquals("Verify text from Arquivo.pt link", "Browse a previous version of this page in 2017-02-28 on Arquivo.pt",
					driver.findElement(By.id("onArquivo")).getText()));
			
			List<WebElement> wes = driver.findElementsByXPath("//*[@id=\"onArquivo\"]");
			WebElement we = wes.get(0);
			String href = we.getAttribute("href");
			assertEquals("Check link to wayback", href, "https://arquivo.pt/wayback/20170227184149/https://sobre.arquivo.pt" + WAYBACK_404_PAGE_EXAMPLE);
			
			//Test search
			appendError(() -> assertEquals("Verify text from search", "Maybe try searching?",
					driver.findElement(By.id("messageSearch")).getText()));
			
			appendError("Check if page is not archived", () -> new WebDriverWait(driver, 20)
					.until(ExpectedConditions.visibilityOfElementLocated(By.id("___gcse_2"))));

		}
	}

}
