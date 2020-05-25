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
		if (this.testURL.toLowerCase().contains("preprod.arquivo.pt")) {
			pageSearch("fccn collection:Roteiro", "59");
		} else {
			pageSearch("fccn", "2.960.097");
		}
	}
	
	public void pageSearch(String query, String numberResults) {
		
		run("Search fccn", () -> {
			driver.findElement(By.id("txtSearch")).clear();
			driver.findElement(By.id("txtSearch")).sendKeys(query);
			driver.findElement(By.xpath("//*[@id=\"buttonSearch\"]/button")).click();
		});
	
		
		waitUntilElementIsVisibleAndGet(By.id("resultados-lista"));
		
		appendError(() -> assertEquals("Verify if the estimated results count message is displayed on page search", numberResults,
				driver.findElement(By.id("estimated-results-value")).getText()));
		
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
