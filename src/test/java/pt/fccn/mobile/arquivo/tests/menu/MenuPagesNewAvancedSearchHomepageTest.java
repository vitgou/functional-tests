package pt.fccn.mobile.arquivo.tests.menu;

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
public class MenuPagesNewAvancedSearchHomepageTest extends MenuTest {

	public MenuPagesNewAvancedSearchHomepageTest(String os, String version, String browser, String deviceName,
			String deviceOrientation) {
		super(os, version, browser, deviceName, deviceOrientation);
	}

	@Test
	@Retry
	public void menuPagesNewAvancedSearchHomepageTest() {
		openMenu();

		run("Open pages sub menu", () -> waitUntilElementIsVisibleAndGet(By.id("pagesMenu")).click());

		run("Click new advanced search button",
				() -> driver.findElement(By.xpath("//*[@id=\"advancedSearch\"]/h4")).click());

		appendError("Check if current url is the advanced search",
				() -> new WebDriverWait(driver, 20).until(ExpectedConditions.urlContains("/advanced.jsp")));
	}

}
