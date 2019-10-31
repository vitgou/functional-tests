package pt.fccn.mobile.arquivo.tests.replay.options;

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
public class ReplayExpandTest extends WebDriverTestBaseParalell {

	private static final String WAYBACK_EXAMPLE = "/wayback/19961013145650/http://www.fccn.pt/";

	private static final String NOFRAME_EXAMPLE = "/noFrame/replay/19961013145650/http://www.fccn.pt/";

	public ReplayExpandTest(String os, String version, String browser, String deviceName, String deviceOrientation) {
		super(os, version, browser, deviceName, deviceOrientation);
	}

	@Test
	@Retry
	public void replayExpandTest() throws InterruptedException {
		driver.get(this.testURL + WAYBACK_EXAMPLE);

		run("Open replay right menu", () -> waitUntilElementIsVisibleAndGet(By.id("replayMenuButton")).click());

		run("Click screenshot link", () -> waitUntilElementIsVisibleAndGet(By.id("expandPage")).click());

//		Thread.sleep(2000);
//
//		String currentUrl = driver.getCurrentUrl();
//		System.out.println("currentUrl " + currentUrl);
//
//		run("Check we go to correct page on no frame",
//				() -> new WebDriverWait(driver, 20).until(ExpectedConditions.urlContains(NOFRAME_EXAMPLE)));

		// don't check url because the following method returns incorrect value on
		// android.
		// driver.getCurrentUrl();

		run("Check we go to correct page on no frame", () -> {
//			WebElement e = driver.findElement(By.xpath("/html/body/center/h2"));
			new WebDriverWait(driver, 20).until(
					ExpectedConditions.textToBePresentInElementLocated(By.xpath("/html/body/center/h2"), "Nacional"));
		});

	}

}
