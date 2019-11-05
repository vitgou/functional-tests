package pt.fccn.mobile.arquivo.tests.pagesearch;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.Test;
import org.openqa.selenium.By;

import pt.fccn.arquivo.selenium.Retry;
import pt.fccn.arquivo.selenium.WebDriverTestBaseParalell;

/**
 *
 * @author Ivo Branco <ivo.branco@fccn.pt>
 *
 */
public class PageSearchQuerySuggestionTest extends WebDriverTestBaseParalell {

	public PageSearchQuerySuggestionTest(String os, String version, String browser, String deviceName,
			String deviceOrientation) {
		super(os, version, browser, deviceName, deviceOrientation);
	}

	@Test
	@Retry
	public void pageSearchQuerySuggestionTest() {
		run("Search with testre", () -> {
			driver.findElement(By.id("txtSearch")).clear();
			driver.findElement(By.id("txtSearch")).sendKeys("testre");
			driver.findElement(By.xpath("//*[@id=\"buttonSearch\"]/button")).click();
		});

		assertThat("Verify if a suggestion is presented",
				driver.findElement(By.xpath("//*[@class=\"suggestion\"]/a")).getText(), containsString("teste"));
	}

}
