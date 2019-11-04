package pt.fccn.arquivo.tests.util;

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
	 * Check if textOnReplayPageCheck is visible on replay wayback iframe.
	 *
	 * @param driver
	 * @param textOnReplayPageCheck
	 */
	@Deprecated
	public static void checkTextOnReplayPage(WebDriver driver, String textOnReplayPageCheck) {
		new WebDriverWait(driver, 180).until(ExpectedConditions.frameToBeAvailableAndSwitchToIt("replay_iframe"));
		// driver.switchTo().frame("replay_iframe");
		if (textOnReplayPageCheck != null && textOnReplayPageCheck.length() > 0) {

			try {
				new WebDriverWait(driver, 180).until(
						ExpectedConditions.textToBePresentInElementLocated(By.tagName("body"), textOnReplayPageCheck));
			} catch (WebDriverException e) {
				System.out.println(
						"ReplayUtils.checkTextOnReplayPage error when waiting text to be visible using fall back to check content is visible.");
				driver.switchTo().defaultContent();

//				assertThat(driver.findElement(By.tagName("body")).getText(),
//						containsString(textOnReplayPageCheck));

				new WebDriverWait(driver, 180).until(
						ExpectedConditions.textToBePresentInElementLocated(By.xpath("/html"), textOnReplayPageCheck));
			}
		}
		driver.switchTo().defaultContent();
	}

	public static void checkTextOnReplayPage(WebDriver driver, String waybackTextXPath, String textOnReplayPageCheck) {

		new WebDriverWait(driver, 180).until(ExpectedConditions.frameToBeAvailableAndSwitchToIt("replay_iframe"));

//		assertThat("Replay page source contains specific text", driver.getPageSource(),
//				containsString(textOnReplayPageCheck));

		String xpath = waybackTextXPath != null ? waybackTextXPath : "/html";

		if (textOnReplayPageCheck != null) {
			ExpectedConditions.textToBePresentInElementLocated(By.xpath(xpath), textOnReplayPageCheck);
		}

		driver.switchTo().defaultContent();
	}
}
