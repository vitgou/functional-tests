package pt.fccn.mobile.arquivo.tests.replay.options;

import static org.hamcrest.CoreMatchers.endsWith;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import pt.fccn.arquivo.selenium.Retry;
import pt.fccn.arquivo.selenium.WebDriverTestBaseParalell;

/**
 *
 * @author Ivo Branco <ivo.branco@fccn.pt>
 *
 */
public class ReplayListVersionsTest extends WebDriverTestBaseParalell {

	private static final String WAYBACK_EXAMPLE = "/wayback/19961013145650/http://www.fccn.pt/";

	public ReplayListVersionsTest(String os, String version, String browser, String deviceName,
			String deviceOrientation) {
		super(os, version, browser, deviceName, deviceOrientation);
	}

	@Test
	@Retry
	public void replayListVersionsTest() {
		driver.get(this.testURL + WAYBACK_EXAMPLE);

		run("Open replay right menu", () -> waitUntilElementIsVisibleAndGet(By.id("replayMenuButton")).click());

		WebElement anchor = run("Get list versions button",
				() -> waitUntilElementIsVisibleAndGet(By.xpath("//*[@id=\"swiperWrapper\"]/div[3]/a[1]")));

		String expectedUrlSuffix = "/search.jsp?l=pt&query=http%3A%2F%2Fwww.fccn.pt%2F";

		run("Check list version button to correct page",
				() -> assertThat(anchor.getAttribute("href"), endsWith(expectedUrlSuffix)));

		run("Click list versions anchor", () -> anchor.click());

		run("Check url is on list versions",
				() -> new WebDriverWait(driver, 20).until(ExpectedConditions.urlContains(expectedUrlSuffix)));
	}

}
