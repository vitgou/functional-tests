package pt.fccn.mobile.arquivo.tests.imagesearch;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;

import java.util.Collections;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import pt.fccn.arquivo.selenium.WebDriverTestBaseParalell;

/**
 * Test the search of one term in the index interface.
 *
 * @author ivo.branco@fccn.pt
 *
 */
public class ImageAdvancedSearchTest extends WebDriverTestBaseParalell {

	public ImageAdvancedSearchTest(String os, String version, String browser, String deviceName,
			String deviceOrientation) {
		super(os, version, browser, deviceName, deviceOrientation);
	}

	@Test
	public void testImageAdvancedSearchPage() throws Exception {

		run("Search FCCN term", () -> {
			driver.findElement(By.id("txtSearch")).clear();
			driver.findElement(By.id("txtSearch")).sendKeys("fccn");
			driver.findElement(By.xpath("//*[@id=\"buttonSearch\"]/button")).click();
		});

		run("Search images instead of text", () -> {
			waitUntilElementIsVisibleAndGet(By.id("ImageButton")).click();
		});

		run("Click on advanced search link to navigate to advanced search page",
				() -> waitUntilElementIsVisibleAndGet(By.id("advancedSearchButton")).click());

		appendError(() -> {
			assertEquals("Check if search words maintain fccn term", "fccn",
					driver.findElement(By.id("adv_and")).getAttribute("value"));
		});

		run("Close words box", () -> driver.findElement(By.xpath("//*[@id=\"words\"]/legend")).click());

		run("Open dates box", () -> driver.findElement(By.xpath("//*[@id=\"date\"]/legend")).click());

		run("Open start date picker", () -> driver.findElement(By.id("dateStart_top")).click());

		run("Insert 31 may 2010 on start date picker", () -> {
			selectIonicDatePicker(31, 5, 2010);
		});

		run("Open end date picker", () -> driver.findElement(By.id("dateEnd_top")).click());

		run("Insert 1 jun 2012 on end date picker", () -> {
			selectIonicDatePicker(1, 6, 2012);
		});

		run("Close dates box", () -> driver.findElement(By.xpath("//*[@id=\"date\"]/legend")).click());

		run("Open images box", () -> driver
				.findElement(
						By.xpath("//*[@id=\"formatType\"]/ancestor::div[contains(@class, 'expandable-div')]/legend"))
				.click());

		appendError("Open select size", () -> driver.findElement(By.id("size")).click());

		appendError("Set size",
				() -> waitUntilElementIsVisibleAndGet(By.xpath("//ion-action-sheet/div/div/div[1]/button[2]")).click());

		appendError("Open format type", () -> driver.findElement(By.id("formatType")).click());

		appendError("Set format type",
				() -> driver.findElement(By.xpath("//ion-action-sheet/div/div/div[1]/button[3]")).click());

		run("Close images box", () -> driver
				.findElement(
						By.xpath("//*[@id=\"formatType\"]/ancestor::div[contains(@class, 'expandable-div')]/legend"))
				.click());

		appendError("Open sites / domains box",
				() -> driver.findElement(By.xpath("//*[@id=\"domains\"]/legend")).click());

		appendError("Set site", () -> driver.findElement(By.id("site")).sendKeys("fccn.pt"));

		appendError("Open sites / domains box",
				() -> driver.findElement(By.xpath("//*[@id=\"domains\"]/legend")).click());

		appendError("Click on search on arquivo.pt button", () -> driver.findElement(By.id("btnSubmitBottom")).click());

		System.out.println("Current url: " + driver.getCurrentUrl());

		appendError(() -> assertThat("Check image original origin/domain",
				driver.findElement(By.xpath("//*[@id=\"imageResults0\"]/p[1]")).getText(),
				containsString("arquivo-web.fccn")));

		appendError(() -> assertEquals("Check image date", "5 Agosto, 2010",
				driver.findElement(By.xpath("//*[@id=\"date0\"]")).getText()));

		appendError(() -> assertEquals("Check image thumbnail on base 64",
				"data:image/png;base64,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",
				driver.findElement(By.xpath("//*[@id=\"imageResults0\"]/img")).getAttribute("src")));

		appendError(() -> assertEquals("After advanced search check search term contains",
				"fccn site:fccn.pt type:png size:sm",
				driver.findElement(By.id("txtSearch")).getAttribute("value").trim()));

		// start date - from
		appendError(() -> assertEquals("After advanced search check day start date contains", "31",
				driver.findElement(By.id("calendarDayLeft")).getText()));

		appendError(() -> assertEquals("After advanced search check month start date contains", "mai",
				driver.findElement(By.id("calendarMonthLeft")).getText()));

		appendError(() -> assertEquals("After advanced search check year start date contains", "2010",
				driver.findElement(By.id("calendarYearLeft")).getText()));

		// until - end date
		appendError(() -> assertEquals("After advanced search check day end date contains", "1",
				driver.findElement(By.id("calendarDayRight")).getText()));

		appendError(() -> assertEquals("After advanced search check month end date contains", "jun",
				driver.findElement(By.id("calendarMonthRight")).getText()));

		appendError(() -> assertEquals("After advanced search check year end date contains", "2012",
				driver.findElement(By.id("calendarYearRight")).getText()));
	}

	private void selectIonicDatePicker(int day, int month, int year) {
		ionicSelectTargetPicker("//ion-picker//ion-picker-column[2]//button", month);
		ionicSelectTargetPicker("//ion-picker//ion-picker-column[1]//button", day);
		int targetYearIndex = Integer.valueOf(
				driver.findElement(By.xpath("//ion-picker//ion-picker-column[3]//button[text()='" + year + "']"))
						.getAttribute("opt-index"))
				+ 1;
		ionicSelectTargetPicker("//ion-picker//ion-picker-column[3]//button", targetYearIndex);

		driver.findElement(By.xpath("//ion-picker//button[text()='OK']")).click();
	}

	private void ionicSelectTargetPicker(String buttonXpath, int targetIndex) {
		WebElement currentSelectedElement = driver
				.findElement(By.xpath(buttonXpath + "[contains(@class, 'picker-opt-selected')]"));
		String indexAttribute = currentSelectedElement.getAttribute("opt-index");
		Integer currentIndex = Integer.valueOf(indexAttribute);

//		System.out.println("currentIndex " + currentIndex);
//		System.out.println("targetIndex " + targetIndex);
		Stream<Integer> range;
		if (currentIndex < targetIndex) {
			range = IntStream.range(currentIndex, targetIndex).boxed();
		} else {
			range = IntStream.range(targetIndex - 1, currentIndex).boxed().sorted(Collections.reverseOrder());
		}
		range.forEach(i -> {
//			System.out.println("ionic select target picker " + i);
			driver.findElement(By.xpath(buttonXpath + "[@opt-index='" + i + "']")).click();
		});
	}

}