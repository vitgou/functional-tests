package pt.fccn.mobile.arquivo.tests.pagesearch;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.openqa.selenium.By;

import pt.fccn.arquivo.selenium.Retry;
import pt.fccn.arquivo.selenium.WebDriverTestBaseParalell; 

/**
 *
 * @author Ivo Branco <ivo.branco@fccn.pt>
 *
 */
public class PageSearchTest extends WebDriverTestBaseParalell {

	public PageSearchTest(String os, String version, String browser, String deviceName, String deviceOrientation) {
		super(os, version, browser, deviceName, deviceOrientation);
	}

	@Test
	@Retry
	public void pageSearchTest() {
		run("Search fccn", () -> {
			driver.findElement(By.id("txtSearch")).clear();
			driver.findElement(By.id("txtSearch")).sendKeys("fccn");
			driver.findElement(By.xpath("//*[@id=\"buttonSearch\"]/button")).click();
		});

		
		waitUntilElementIsVisibleAndGet(By.id("resultados-lista"));
		
		if (this.testURL.toLowerCase().contains("preprod.sobre.arquivo.pt")) {
			//Last number was 548.022
			appendError(() -> assertEquals("Verify if the estimated results count message is displayed on page search", "483.515",
					driver.findElement(By.id("estimated-results-value")).getText()));
		} else {
			appendError(() -> assertEquals("Verify if the estimated results count message is displayed on page search", "2.960.097",
					driver.findElement(By.id("estimated-results-value")).getText()));
		}
		int anchorsCount = driver.findElementsByXPath("//*[@id=\"resultados-lista\"]//*[@class=\"url\"][contains(text(),'fccn')]")
				.size();

		System.out.println("anchorsCount " + anchorsCount);
		
		long emsCount = driver.findElementsByXPath("//*[@id=\"resultados-lista\"]//em") //
				.stream() //
				.filter(em -> em.getText().toLowerCase().contains("fccn")) //
				.count();

		System.out.println("emsCount " + emsCount);

		assertTrue("At least 80 percent of results should show something related to search criteria",
				emsCount + anchorsCount >= 10);
	}

}
