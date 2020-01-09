package pt.fccn.mobile.arquivo.tests.pagesearch;

import static org.junit.Assert.assertTrue;

import java.time.LocalDate;

import org.junit.Test;
import org.openqa.selenium.By;

import pt.fccn.arquivo.selenium.Retry;
import pt.fccn.arquivo.selenium.WebDriverTestBaseParalell;
import pt.fccn.mobile.arquivo.utils.IonicDatePicker;

/**
 * 
 * @author pedro.gomes.fccn@gmail.com
 *
 */

public class PageSearchOverlapDatesTest extends WebDriverTestBaseParalell {

	public PageSearchOverlapDatesTest(String os, String version, String browser, String deviceName,
			String deviceOrientation) {
		super(os, version, browser, deviceName, deviceOrientation);
	}

	@Test
	@Retry
	public void pageSearchOverlapDatesTest() throws Exception {
		run("Search FCCN term", () -> {
			driver.findElement(By.id("txtSearch")).clear();
			driver.findElement(By.id("txtSearch")).sendKeys("fccn");
			driver.findElement(By.xpath("//*[@id=\"buttonSearch\"]/button")).click();
		});

		run("Open from date picker", () -> waitUntilElementIsVisibleAndGet(By.id("sliderCircleLeft")).click());
		LocalDate fromDate = LocalDate.of(1997, 5, 20);
		run("Insert " + fromDate.toString() + " on start date picker",
				() -> IonicDatePicker.changeTo(driver, fromDate));

		run("Open until date picker", () -> waitUntilElementIsVisibleAndGet(By.id("sliderCircleRight")).click());
		LocalDate untilDate = LocalDate.of(1996, 8, 22);

		appendError(() -> {
			assertTrue("Check if it is possible to do date overlap: ", checkDatePicker(untilDate));
		});

	}

	private boolean checkDatePicker(LocalDate untilDate) {
		try {
			IonicDatePicker.changeTo(driver, untilDate);
			return false;
		} catch (Exception e) {
			return true;
		}
	}

}
