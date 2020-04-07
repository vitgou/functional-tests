package pt.fccn.mobile.arquivo.utils;

import java.time.LocalDate;
import java.util.Collections;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.openqa.selenium.By;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.Select;

/**
 * Class with utility method to deal with ionic date picker.
 *
 * @author Ivo Branco <ivo.branco@fccn.pt>
 *
 */
public class IonicDatePicker {

	public static void changeTo(WebDriver driver, LocalDate date) {
		
		int day = date.getDayOfMonth(), month = date.getMonthValue(), year = date.getYear();

		Capabilities cap = ((RemoteWebDriver) driver).getCapabilities();
	    String browserName = cap.getBrowserName().toLowerCase();
	    String os = cap.getPlatform().toString();
		
	    if (os.equals("WINDOWS")){ 
	    	selectDateFromJqueryDatepicker(driver.findElement(By.id("ui-datepicker-div")), year, month, day);
	    	driver.findElement(By.xpath("//*[@id=\"uglipop_popbox\"]/div[2]/button")).click();
	    }else{

			ionicSelectTargetPicker(driver, "//ion-picker//ion-picker-column[2]//button", month);
			ionicSelectTargetPicker(driver, "//ion-picker//ion-picker-column[1]//button", day);
			int targetYearIndex = Integer.valueOf(
					driver.findElement(By.xpath("//ion-picker//ion-picker-column[3]//button[text()='" + year + "']"))
							.getAttribute("opt-index"))
					+ 1;
			ionicSelectTargetPicker(driver, "//ion-picker//ion-picker-column[3]//button", targetYearIndex);

			driver.findElement(By.xpath("//ion-picker//button[text()='OK']")).click();	
	    }
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
	
	public static void selectDateFromJqueryDatepicker(WebElement datePickerDiv,  int expYear, int expMonth, int expDay) {

	    // select the given year
	    Select yearSel = new Select(datePickerDiv.findElement(By.xpath("//*[@class=\"ui-datepicker-year\"]")));
	    yearSel.selectByValue(String.valueOf(expYear));

	    // select the given month
	    Select monthSel = new Select(datePickerDiv.findElement(By.xpath("//*[@class=\"ui-datepicker-month\"]")));
	    monthSel.selectByValue(String.valueOf(expMonth - 1)); // value start @ 0 so we need the -1

	    // get the table
	    WebElement calTable = datePickerDiv.findElement(By.xpath("//*[@class=\"ui-datepicker-calendar\"]"));
	    // click on the correct/given cell/date
	    calTable.findElement(By.linkText(String.valueOf(expDay))).click();
	  }
	
	

}
