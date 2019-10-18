package pt.fccn.mobile.arquivo.tests.menu;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import pt.fccn.arquivo.selenium.Retry;
import pt.fccn.arquivo.selenium.WebDriverTestBaseParalell;

/**
 *
 * @author Ivo Branco <ivo.branco@fccn.pt>
 *
 */
public class MenuAboutTest extends MenuTest {

	public MenuAboutTest(String os, String version, String browser, String deviceName, String deviceOrientation) {
		super(os, version, browser, deviceName, deviceOrientation);
	}

	@Test
	@Retry
	public void menuAboutPTTest() {
		menuAbout("pt", "https://sobre.arquivo.pt/pt/");

		menuAbout("en", "https://sobre.arquivo.pt/en/");
	}

	private void menuAbout(String language, String expectedUrl) {
		driver.get(testURL + "?l=" + language);

		openMenu();

		run("Click about button", () -> driver.findElementByXPath("//*[@id=\"menuSwiperSlide\"]/a/h4").click());

		appendError("Check if current url is the about page",
				() -> new WebDriverWait(driver, 20).until(ExpectedConditions.urlContains(expectedUrl)));
	}

}
