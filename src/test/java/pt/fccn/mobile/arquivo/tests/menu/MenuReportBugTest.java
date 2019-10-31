package pt.fccn.mobile.arquivo.tests.menu;

import org.junit.Test;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import pt.fccn.arquivo.selenium.Retry;
import pt.fccn.mobile.arquivo.utils.LocaleUtils;

/**
 *
 * @author Ivo Branco <ivo.branco@fccn.pt>
 *
 */
public class MenuReportBugTest extends MenuTest {

	private static final String WAYBACK_EXAMPLE = "/wayback/19961013145650/http://www.fccn.pt/";

	public MenuReportBugTest(String os, String version, String browser, String deviceName, String deviceOrientation) {
		super(os, version, browser, deviceName, deviceOrientation);
	}

	@Test
	@Retry
	public void menuReportBugHomepagePTTest() {
		LocaleUtils.changeLanguageToPT(this);
		menuReportBug("https://sobre.arquivo.pt/pt/contacto/");
	}

	@Test
	@Retry
	public void menuReportBugWaybackPTTest() {
		LocaleUtils.changeLanguageToPT(this);
		driver.get(this.testURL + WAYBACK_EXAMPLE);
		menuReportBug("https://sobre.arquivo.pt/pt/contacto/");
	}

	@Test
	@Retry
	public void menuReportBugHomepageENTest() {
		LocaleUtils.changeLanguageToEN(this);
		menuReportBug("https://sobre.arquivo.pt/en/contact/");
	}

	@Test
	@Retry
	public void menuReportBugWaybackENTest() {
		LocaleUtils.changeLanguageToEN(this);
		driver.get(this.testURL + WAYBACK_EXAMPLE);
		menuReportBug("https://sobre.arquivo.pt/en/contact/");
	}

	private void menuReportBug(String expectedUrl) {
		openMenu();

		run("Click report bug button", () -> driver.findElementById("reportBug").click());

		appendError("Check if current url is the report bug page",
				() -> new WebDriverWait(driver, 20).until(ExpectedConditions.urlContains(expectedUrl)));
	}

}
