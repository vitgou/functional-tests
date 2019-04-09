package pt.fccn.arquivo.tests.util;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 *
 * @author Ivo Branco <ivo.branco@fccn.pt>
 *
 */
public class ReplayUtils {

	public static void checkTextOnReplayPage(WebDriver driver, String textOnReplayPageCheck) {
		// enter inner replay frame
//		new WebDriverWait(driver, 180).until(ExpectedConditions.frameToBeAvailableAndSwitchToIt("replay_iframe"));
		driver.switchTo().frame("replay_iframe");

		new WebDriverWait(driver, 180)
				.until(ExpectedConditions.textToBePresentInElementLocated(By.xpath("/html"), textOnReplayPageCheck));
		driver.switchTo().defaultContent();
	}
}
