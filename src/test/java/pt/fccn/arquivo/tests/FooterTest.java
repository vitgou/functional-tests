package pt.fccn.arquivo.tests;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import pt.fccn.arquivo.selenium.Retry;
import pt.fccn.arquivo.selenium.WebDriverTestBaseParalell;
import pt.fccn.arquivo.tests.util.AnalyzeURLs;
import pt.fccn.arquivo.tests.util.SwitchLanguage;

/**
 *
 * @author jnobre
 * @author Ivo Branco - major changes
 *
 */
public class FooterTest extends WebDriverTestBaseParalell {

	private static final int footerElementsCount = 24;
	private final int timeout = 90;

	public FooterTest(String os, String version, String browser, String deviceName, String deviceOrientation) {
		super(os, version, browser, deviceName, deviceOrientation);
	}

	@Test
	@Retry
	public void footerTest() {
		checkFooterLinks("PT");

		SwitchLanguage.switchEnglishLanguage(driver);
		checkFooterLinks("EN");
	}

	/**
	 *
	 * @param language
	 * @return
	 */
	public void checkFooterLinks(String language) {
		System.out.println("[checkFooterLinks]");

		List<WebElement> results = run("get footer links", () -> {

			return new WebDriverWait(driver, timeout)

					.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(
							By.xpath("//*[@id=\"footer-widgets\"]/div/div/div/aside/ul/li/a")));

		});

		System.out.println("[footer] results size = " + results.size());
		assertTrue(String.format("[footer] results size at least %d elements", footerElementsCount),
				results.size() >= footerElementsCount);

		for (WebElement elem : results) {
			String url = elem.getAttribute("href");
			if (!url.startsWith("http://www.facebook.com/") && !url.startsWith("https://www.facebook.com/")
					&& !url.startsWith("https://github.com/")) {

				System.out.println("Check footer link: " + url);

				appendError(() -> assertTrue(String.format("Check footer link in %s: %s", language, url),
						AnalyzeURLs.checkLink(url)));
			}
		}

	}
}
