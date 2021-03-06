package pt.fccn.mobile.arquivo.tests.replay.options;

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
public class ReplayExpandTest extends WebDriverTestBaseParalell {

	private static final String WAYBACK_SITE = "http://www.fccn.pt/";

	private static final String WAYBACK_PATH = "/wayback/19961013145650/" + WAYBACK_SITE;

	private static final String NOFRAME_EXAMPLE = "/noFrame/replay/19961013145650/" + WAYBACK_SITE;

	public ReplayExpandTest(String os, String version, String browser, String deviceName, String deviceOrientation) {
		super(os, version, browser, deviceName, deviceOrientation);
	}

	@Test
	@Retry
	public void replayExpandTest() throws InterruptedException {
		driver.get(this.testURL + WAYBACK_PATH);

		run("Open replay right menu", () -> waitUntilElementIsVisibleAndGet(By.id("replayMenuButton")).click());

		run("Click expand link", () -> waitUntilElementIsVisibleAndGet(By.id("expandPage")).click());

		// or url contains wayback site (specific for android driver) or the correct
		// full no frame url
		run("Check we go to correct no frame url", () -> new WebDriverWait(driver, 20).until(ExpectedConditions
				.or(ExpectedConditions.urlContains(WAYBACK_SITE), ExpectedConditions.urlContains(NOFRAME_EXAMPLE))));

		run("Check we go to correct no frame text", () -> {
			new WebDriverWait(driver, 20).until(
					ExpectedConditions.textToBePresentInElementLocated(By.xpath("/html/body/center/h2"), "Nacional"));
		});

	}

}
