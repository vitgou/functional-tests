package pt.fccn.mobile.arquivo.tests.replay.menu;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import pt.fccn.arquivo.selenium.Retry;

/**
 *
 * @author Ivo Branco <ivo.branco@fccn.pt>
 *
 */
public class MenuPagesNewSearchWaybackTest extends MenuWaybackTest {

	private static final String WAYBACK_EXAMPLE = "/wayback/19961013145650/http://www.fccn.pt/";

	public MenuPagesNewSearchWaybackTest(String os, String version, String browser, String deviceName,
			String deviceOrientation) {
		super(os, version, browser, deviceName, deviceOrientation);
	}

	@Test
	@Retry
	public void menuPagesNewSearchWaybackTest() {
		driver.get(this.testURL + WAYBACK_EXAMPLE);

		openMenu();

		run("Open pages sub menu", () -> waitUntilElementIsVisibleAndGet(By.id("pagesMenu")).click());

		run("Click new search button",
				() -> waitUntilElementIsVisibleAndGet(By.xpath("//*[@id=\"pageOptions\"]/a/h4")).click());

		appendError("Check if current url is the page search",
				() -> new WebDriverWait(driver, 20).until(ExpectedConditions.urlContains("/page/search?")));
	}

}
