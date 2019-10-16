package pt.fccn.arquivo.tests.util;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 *
 * @author Ivo Branco <ivo.branco@fccn.pt>
 *
 */
public class ReplayUtils {

	/**
	 * TODO:: not working in latest Chrome desktop nor in IE 11
	 * Check if textOnReplayPageCheck is visible on replay wayback iframe.
	 *
	 * @param driver
	 * @param textOnReplayPageCheck
	 */
	public static void checkTextOnReplayPage(WebDriver driver, String textOnReplayPageCheck) {
		new WebDriverWait(driver, 180).until(ExpectedConditions.frameToBeAvailableAndSwitchToIt("replay_iframe"));
		// driver.switchTo().frame("replay_iframe");
		if (textOnReplayPageCheck != null && textOnReplayPageCheck.length() > 0) {

			try {
				new WebDriverWait(driver, 180).until(
					ExpectedConditions.textToBePresentInElementLocated(By.tagName("body"), textOnReplayPageCheck));
			} catch (WebDriverException e) {
				System.out.println("ReplayUtils.checkTextOnReplayPage error when waiting text to be visible using fall back to check content is visible.");
				driver.switchTo().defaultContent();

				assertThat(driver.findElement(By.tagName("body")).getText(),
						containsString(textOnReplayPageCheck));
			}
		}
		driver.switchTo().defaultContent();
	}
}
