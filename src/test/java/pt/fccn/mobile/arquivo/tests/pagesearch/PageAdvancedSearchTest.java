package pt.fccn.mobile.arquivo.tests.pagesearch;

import static org.junit.Assert.assertEquals;

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

public class PageAdvancedSearchTest extends WebDriverTestBaseParalell {

	public PageAdvancedSearchTest(String os, String version, String browser, String deviceName,
			String deviceOrientation) {
		super(os, version, browser, deviceName, deviceOrientation);
	}
	
	@Test
	@Retry
	public void testPageAdvancedSearch() throws Exception {
		run("Search FCCN term", () -> {
			driver.findElement(By.id("txtSearch")).clear();
			driver.findElement(By.id("txtSearch")).sendKeys("fccn");
			driver.findElement(By.xpath("//*[@id=\"buttonSearch\"]/button")).click();
		});
		
		run("Click on advanced search link to navigate to advanced search page",
				() -> waitUntilElementIsVisibleAndGet(By.id("advancedSearchButton")).click());
		
		appendError(() -> {
			assertEquals("Check if search words maintain fccn term", "fccn",
					driver.findElement(By.id("adv_and")).getAttribute("value"));
		});
		
		run("Close words box", () -> driver.findElement(By.xpath("//*[@id=\"words\"]/legend")).click());

		run("Open start date picker", () -> driver.findElement(By.id("dateStart_top")).click());

		run("Insert 31 may 2010 on start date picker", () -> {
			IonicDatePicker.changeTo(driver, LocalDate.of(2006, 5, 31));
		});
		
		run("Open end date picker", () -> driver.findElement(By.id("dateEnd_top")).click());
		
		run("Insert 1 jun 2012 on end date picker", () -> {
			IonicDatePicker.changeTo(driver, LocalDate.of(2018, 6, 1));
		});

		run("Close dates box", () -> driver.findElement(By.xpath("//*[@id=\"date\"]/legend")).click());
		
		appendError("Open format type", () -> driver.findElement(By.id("formatType")).click());

		appendError("Set format type",
				() -> driver.findElement(By.xpath("//ion-action-sheet/div/div/div[1]/button[2]")).click());
		
		run("Close format box", () -> driver
				.findElement(
						By.xpath("//*[@id=\"formatType\"]/ancestor::div[contains(@class, 'expandable-div')]"))
				.click());

		appendError("Set site", () -> driver.findElement(By.id("site")).sendKeys("fccn.pt"));
		
		appendError("Open sites / domains box",
				() -> driver.findElement(By.xpath("//*[@id=\"domains\"]/legend")).click());
		
		appendError("Click on search on arquivo.pt button", () -> driver.findElement(By.id("btnSubmitBottom")).click());
		
		appendError(() -> assertEquals("After advanced search check search term contains",
				"fccn site:fccn.pt type:pdf",
				driver.findElement(By.id("txtSearch")).getAttribute("value").trim()));

		System.out.println("Current url: " + driver.getCurrentUrl());
		
		appendError(() -> assertEquals("Check mime of first result", "[PDF]",
				driver.findElement(By.xpath("//*[@id=\"resultados-lista\"]/ul/li/div[1]/a/h2/span")).getText()));
		
		appendError(() -> assertEquals("Check url of first result", "→ fccn.pt/wp-content/uploads/2017/06/booklet_RCTS2017.pdf",
				driver.findElement(By.xpath("//*[@id=\"resultados-lista\"]/ul/li/div[1]/p")).getText()));
		
		// start date - from
		appendError(() -> assertEquals("After advanced search check day start date contains", "31",
				driver.findElement(By.id("calendarDayStart")).getText()));

		appendError(() -> assertEquals("After advanced search check month start date contains", "mai",
				driver.findElement(By.id("calendarMonthStart")).getText()));

		appendError(() -> assertEquals("After advanced search check year start date contains", "2006",
				driver.findElement(By.id("calendarYearStart")).getText()));

		// until - end date
		appendError(() -> assertEquals("After advanced search check day end date contains", "1",
				driver.findElement(By.id("calendarDayEnd")).getText()));

		appendError(() -> assertEquals("After advanced search check month end date contains", "jun",
				driver.findElement(By.id("calendarMonthEnd")).getText()));

		appendError(() -> assertEquals("After advanced search check year end date contains", "2018",
				driver.findElement(By.id("calendarYearEnd")).getText()));
	}

}
