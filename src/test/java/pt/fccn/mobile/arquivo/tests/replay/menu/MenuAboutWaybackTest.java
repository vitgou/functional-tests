package pt.fccn.mobile.arquivo.tests.replay.menu;

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
public class MenuAboutWaybackTest extends MenuWaybackTest {

	public MenuAboutWaybackTest(String os, String version, String browser, String deviceName,
			String deviceOrientation) {
		super(os, version, browser, deviceName, deviceOrientation);
	}

	private static final String WAYBACK_EXAMPLE = "/wayback/19961013145650/http://www.fccn.pt/";

	@Test
	@Retry
	public void menuAboutWaybackPTTest() {
		LocaleUtils.changeLanguageToPT(this);
		driver.get(this.testURL + WAYBACK_EXAMPLE);
		menuAbout("https://sobre.arquivo.pt/pt/");
	}

	@Test
	@Retry
	public void menuAboutWaybackENTest() {
		LocaleUtils.changeLanguageToEN(this);
		driver.get(this.testURL + WAYBACK_EXAMPLE);
		menuAbout("https://sobre.arquivo.pt/en/");
	}

	private void menuAbout(String expectedUrl) {
		openMenu();

		run("Click about button", () -> driver.findElementByXPath("//*[@id=\"menuSwiperSlide\"]/a/h4").click());

		appendError("Check if current url is the about page",
				() -> new WebDriverWait(driver, 20).until(ExpectedConditions.urlContains(expectedUrl)));

	}

}
