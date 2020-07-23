package pt.fccn.mobile.arquivo.tests.pagesearch;

import static org.junit.Assert.assertEquals;

import java.time.LocalDate;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.Select;

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
		

		run("Open start date picker", () -> driver.findElement(By.id("sliderCircleStart")).click());

		run("Insert 31 may 2010 on start date picker", () -> {
			IonicDatePicker.changeTo(driver, LocalDate.of(2006, 5, 31));
		});
		
		run("Open end date picker", () -> driver.findElement(By.id("sliderCircleEnd")).click());
		
		run("Insert 1 jan 2012 on end date picker", () -> {
			IonicDatePicker.changeTo(driver, LocalDate.of(2018, 1, 1));
		});
		
        appendError("Open format type", () -> driver.findElement(By.id("formatType")).click());
		
		Select dropdown_type = new Select(driver.findElement(By.id("formatType")));
		
		appendError("Set format type",
				() -> dropdown_type.selectByValue("pdf"));

		appendError("Set site", () -> driver.findElement(By.id("site")).sendKeys("fccn.pt"));
		
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

		appendError(() -> assertEquals("After advanced search check month start date contains", "Mai",
				driver.findElement(By.id("calendarMonthStart")).getText()));

		appendError(() -> assertEquals("After advanced search check year start date contains", "2006",
				driver.findElement(By.id("calendarYearStart")).getText()));

		// until - end date
		appendError(() -> assertEquals("After advanced search check day end date contains", "1",
				driver.findElement(By.id("calendarDayEnd")).getText()));

		appendError(() -> assertEquals("After advanced search check month end date contains", "Jan",
				driver.findElement(By.id("calendarMonthEnd")).getText()));

		appendError(() -> assertEquals("After advanced search check year end date contains", "2018",
				driver.findElement(By.id("calendarYearEnd")).getText()));
	}

}
