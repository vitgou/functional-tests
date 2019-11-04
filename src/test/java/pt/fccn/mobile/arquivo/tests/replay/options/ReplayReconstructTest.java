package pt.fccn.mobile.arquivo.tests.replay.options;

import static org.openqa.selenium.support.ui.ExpectedConditions.or;
import static org.openqa.selenium.support.ui.ExpectedConditions.urlToBe;
import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated;

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
public class ReplayReconstructTest extends WebDriverTestBaseParalell {

	private static final String WAYBACK_EXAMPLE = "/wayback/19961013145650/http://www.fccn.pt/";
	private static final String MEMENTO_TIMETRAVEL = "https://timetravel.mementoweb.org/reconstruct/http://www.fccn.pt/";

	public ReplayReconstructTest(String os, String version, String browser, String deviceName,
			String deviceOrientation) {
		super(os, version, browser, deviceName, deviceOrientation);
	}

	@Test
	@Retry
	public void replayReconstructTest() {
		driver.get(this.testURL + WAYBACK_EXAMPLE);

		run("Open replay right menu", () -> waitUntilElementIsVisibleAndGet(By.id("replayMenuButton")).click());

		run("Click reconstruct link", () -> waitUntilElementIsVisibleAndGet(By.id("a_reconstruct")).click());

		run("Cancel reconstruct", () -> waitUntilElementIsVisibleAndGet(By.id("cancelPopup")).click());

		new WebDriverWait(driver, 20).until(ExpectedConditions.invisibilityOfElementLocated(By.id("cancelPopup")));

		appendError("Check if cancel button really closes confirm reconstruct modal box",
				() -> new WebDriverWait(driver, 20)
						.until(ExpectedConditions.invisibilityOfElementLocated(By.id("uglipop_popbox"))));

		run("Click again on reconstruct link", () -> waitUntilElementIsVisibleAndGet(By.id("a_reconstruct")).click());

		run("Click reconstruct", () -> waitUntilElementIsVisibleAndGet(By.id("completePage")).click());

		run("Check on memento timetravel page", () -> new WebDriverWait(driver, 200)
				.until(or(visibilityOfElementLocated(By.id("scatterinfo")), urlToBe(MEMENTO_TIMETRAVEL))));
	}

}
