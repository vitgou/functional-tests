package pt.fccn.mobile.arquivo.tests.workflow;

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

public class WorkflowStateBetweenSeachImagesTest extends WebDriverTestBaseParalell {

	public WorkflowStateBetweenSeachImagesTest(String os, String version, String browser, String deviceName,
			String deviceOrientation) {
		super(os, version, browser, deviceName, deviceOrientation);
	}

	@Test
	@Retry
	public void stateBetweenSeachImagesTest() throws Exception {
		run("Search FCCN term", () -> {
			driver.findElement(By.id("txtSearch")).clear();
			driver.findElement(By.id("txtSearch")).sendKeys("fccn");
			driver.findElement(By.xpath("//*[@id=\"buttonSearch\"]/button")).click();
		});

		run("Click Image Button", () -> {
			driver.findElement(By.xpath("//*[@id=\"ImageButton\"]")).click();
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
			waitUntilElementIsVisibleAndGet(By.xpath("//*[@id=\"nextImage\"]/a")).click();
		});
		
		waitUntilElementIsVisibleAndGet(By.xpath("//*[@id=\"photos\"]"));
		
		appendError("Check if fccn is in search box on second page",
				() -> driver.findElement(By.xpath("//*[@value=\"fccn\"]")));
		
		System.out.println("Current url: " + driver.getCurrentUrl());

		appendError("Check if sliderStart is the same on second page", () -> {
			driver.findElement(By.xpath("//*[@id=\"calendarDayStart\"][contains(text(),'20')]"));
			driver.findElement(By.xpath("//*[@id=\"calendarMonthStart\"][contains(text(),'mai')]"));
			driver.findElement(By.xpath("//*[@id=\"calendarYearStart\"][contains(text(),'1997')]"));
		});

		appendError("Check if sliderEnd is the same on second page", () -> {
			driver.findElement(By.xpath("//*[@id=\"calendarDayEnd\"][contains(text(),'1')]"));
			driver.findElement(By.xpath("//*[@id=\"calendarMonthEnd\"][contains(text(),'jan')]"));
			driver.findElement(By.xpath("//*[@id=\"calendarYearEnd\"][contains(text(),'2014')]"));
		});

		run("Go to the preivous page", () -> {
			waitUntilElementIsVisibleAndGet(By.xpath("//*[@id=\"previousImage\"]/a")).click();
		});

		appendError("Check if fccn is in search box on first page",
				() -> driver.findElement(By.xpath("//*[@value=\"fccn\"]")));

		appendError("Check if sliderStart is the same on first page", () -> {
			driver.findElement(By.xpath("//*[@id=\"calendarDayStart\"][contains(text(),'20')]"));
			driver.findElement(By.xpath("//*[@id=\"calendarMonthStart\"][contains(text(),'mai')]"));
			driver.findElement(By.xpath("//*[@id=\"calendarYearStart\"][contains(text(),'1997')]"));
		});

		appendError("Check if sliderEnd is the same on first page", () -> {
			driver.findElement(By.xpath("//*[@id=\"calendarDayEnd\"][contains(text(),'1')]"));
			driver.findElement(By.xpath("//*[@id=\"calendarMonthEnd\"][contains(text(),'jan')]"));
			driver.findElement(By.xpath("//*[@id=\"calendarYearEnd\"][contains(text(),'2014')]"));
		});

	}

}
