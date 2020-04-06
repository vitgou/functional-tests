package pt.fccn.mobile.arquivo.tests.workflow;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;

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

public class WorkflowStateBetweenSearchPagesTest extends WebDriverTestBaseParalell {

	public WorkflowStateBetweenSearchPagesTest(String os, String version, String browser, String deviceName,
			String deviceOrientation) {
		super(os, version, browser, deviceName, deviceOrientation);
	}

	@Test
	@Retry
	public void stateBetweenSearchPages() throws Exception {
		run("Search FCCN term", () -> {
			driver.findElement(By.id("txtSearch")).clear();
			driver.findElement(By.id("txtSearch")).sendKeys("fccn");
			driver.findElement(By.xpath("//*[@id=\"buttonSearch\"]/button")).click();
		});

		run("Open from date picker", () -> waitUntilElementIsVisibleAndGet(By.id("sliderCircleStart")).click());
		LocalDate fromDate = LocalDate.of(1997, 5, 20);
		run("Insert " + fromDate.toString() + " on start date picker",
				() -> IonicDatePicker.changeTo(driver, fromDate));

		run("Open until date picker", () -> waitUntilElementIsVisibleAndGet(By.id("sliderCircleEnd")).click());
		LocalDate untilDate = LocalDate.of(2014, 1, 1);
		run("Insert " + untilDate.toString() + " on end date picker",
				() -> IonicDatePicker.changeTo(driver, untilDate));

		run("Click Search Button", () -> {
			driver.findElement(By.xpath("//*[@id=\"buttonSearch\"]/button")).click();
		});
		
		run("Go to the next page", () -> {
			waitUntilElementIsVisibleAndGet(By.xpath("//*[@class=\"next\"]/a")).click();
		});
		
		System.out.println("Current url: " + driver.getCurrentUrl());
		
		appendError("Check if fccn is in search box on second page",
				() -> driver.findElement(By.xpath("//*[@value=\"fccn\"]")));
	
		run("Verify calendarStart", () -> {
			assertThat("Verify Start Day",
					driver.findElement(By.xpath("//*[@id=\"calendarDayStart\"]")).getText(), containsString("20"));
			assertThat("Verify Start Month",
					driver.findElement(By.xpath("//*[@id=\"calendarMonthStart\"]")).getText(), containsString("Mai"));
			assertThat("Verify Start Year",
					driver.findElement(By.xpath("//*[@id=\"calendarYearStart\"]")).getText(), containsString("1997"));
		});
		
		run("Verify calendarEnd", () -> {
			assertThat("Verify End Day",
					driver.findElement(By.xpath("//*[@id=\"calendarDayEnd\"]")).getText(), containsString("1"));
			assertThat("Verify End Month",
					driver.findElement(By.xpath("//*[@id=\"calendarMonthEnd\"]")).getText(), containsString("Jan"));
			assertThat("Verify End Year",
					driver.findElement(By.xpath("//*[@id=\"calendarYearEnd\"]")).getText(), containsString("2014"));
		});

		run("Go to the preivous page", () -> {
			waitUntilElementIsVisibleAndGet(By.xpath("//*[@class=\"previous\"]/a")).click();
		});

		appendError("Check if fccn is in search box on first page",
				() -> driver.findElement(By.xpath("//*[@value=\"fccn\"]")));

		run("Verify calendarStart", () -> {
			assertThat("Verify Start Day",
					driver.findElement(By.xpath("//*[@id=\"calendarDayStart\"]")).getText(), containsString("20"));
			assertThat("Verify Start Month",
					driver.findElement(By.xpath("//*[@id=\"calendarMonthStart\"]")).getText(), containsString("Mai"));
			assertThat("Verify Start Year",
					driver.findElement(By.xpath("//*[@id=\"calendarYearStart\"]")).getText(), containsString("1997"));
		});
		
		run("Verify calendarEnd", () -> {
			assertThat("Verify End Day",
					driver.findElement(By.xpath("//*[@id=\"calendarDayEnd\"]")).getText(), containsString("1"));
			assertThat("Verify End Month",
					driver.findElement(By.xpath("//*[@id=\"calendarMonthEnd\"]")).getText(), containsString("Jan"));
			assertThat("Verify End Year",
					driver.findElement(By.xpath("//*[@id=\"calendarYearEnd\"]")).getText(), containsString("2014"));
		});

	}

}
