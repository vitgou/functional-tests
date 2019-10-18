package pt.fccn.mobile.arquivo.tests.menu;

import org.junit.Test;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import pt.fccn.arquivo.selenium.Retry;

/**
 *
 * @author Ivo Branco <ivo.branco@fccn.pt>
 *
 */
public class MenuReportBugTest extends MenuTest {

	public MenuReportBugTest(String os, String version, String browser, String deviceName, String deviceOrientation) {
		super(os, version, browser, deviceName, deviceOrientation);
	}

	@Test
	@Retry
	public void menuReportBugTest() {
		menuReportBug("pt", "https://sobre.arquivo.pt/pt/contacto/");
		menuReportBug("en", "https://sobre.arquivo.pt/en/contact/");
	}

	private void menuReportBug(String language, String expectedUrl) {
		driver.get(testURL + "?l=" + language);

		openMenu();

		run("Click report bug button", () -> driver.findElementById("reportBug").click());

		appendError("Check if current url is the report bug page",
				() -> new WebDriverWait(driver, 20).until(ExpectedConditions.urlContains(expectedUrl)));
	}

}
