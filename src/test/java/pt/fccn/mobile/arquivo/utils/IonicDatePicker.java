package pt.fccn.mobile.arquivo.utils;

import java.time.LocalDate;
import java.util.Collections;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;

/**
 * Class with utility method to deal with ionic date picker.
 *
 * @author Ivo Branco <ivo.branco@fccn.pt>
 *
 */
public class IonicDatePicker {

	public static void changeTo(WebDriver driver, LocalDate date) {
		int day = date.getDayOfMonth(), month = date.getMonthValue(), year = date.getYear();

		ionicSelectTargetPicker(driver, "//ion-picker//ion-picker-column[2]//button", month);
		ionicSelectTargetPicker(driver, "//ion-picker//ion-picker-column[1]//button", day);
		int targetYearIndex = Integer.valueOf(
				driver.findElement(By.xpath("//ion-picker//ion-picker-column[3]//button[text()='" + year + "']"))
						.getAttribute("opt-index"))
				+ 1;
		ionicSelectTargetPicker(driver, "//ion-picker//ion-picker-column[3]//button", targetYearIndex);

		driver.findElement(By.xpath("//ion-picker//button[text()='OK']")).click();
	}

	private static void ionicSelectTargetPicker(WebDriver driver, String buttonXpath, int targetIndex) {
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
