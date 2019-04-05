package pt.fccn.arquivo.tests.util;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Moved from IndexPage.java
 *
 * @author ibranco
 *
 */
public class SwitchLanguage {

	private static final int waitingPeriod = 10000; // Time to load the Web page in miliseconds

	/**
	 * Changes the Language of the Page, to the value given in lang string (PT or
	 * EN)
	 */
	public static void switchLanguage(WebDriver driver, String lang) {
		System.out.println("Switch language function");
		if (driver.findElement(By.xpath("//a[@id=\"changeLanguage\"]")).getText().equals(lang)) {
			System.out.println("Switching language");
			driver.findElement(By.id("changeLanguage")).click(); // change the language
			try {
				Thread.sleep(waitingPeriod); // wait for page to load
			} catch (InterruptedException ex) {
				Thread.currentThread().interrupt();
			}
		} // else You are already in the desired language
	}

	/**
	 * Change to the English version
	 */
	public static void switchEnglishLanguage(WebDriver driver) { // *[@id="changeLanguage"]
		String xpathEnglishVersion = "//*[@id=\"changeLanguage\"]";
		if (driver.findElement(By.xpath(xpathEnglishVersion)).getText().equals("English")) {
			System.out.println("Change language to English");
			driver.findElement(By.xpath(xpathEnglishVersion)).click();
			sleepThread();
		}
	}

	private static void sleepThread() {
		try {
			Thread.sleep(6000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
