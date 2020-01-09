package pt.fccn.arquivo.tests.imagesearch;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import pt.fccn.arquivo.selenium.WebDriverTestBaseParalell;

/**
 * 
 * @author ivo.branco@fccn.pt
 *
 */
public class ImageAdvancedSearchTest extends WebDriverTestBaseParalell {

	/**
	 * Test the search of one term in the index interface.
	 */
	public ImageAdvancedSearchTest(String os, String version, String browser, String deviceName,
			String deviceOrientation) {
		super(os, version, browser, deviceName, deviceOrientation);
	}

	@Test
	public void testImageAdvancedSearchPage() throws Exception {

		run("Search FCCN term", () -> {
			driver.findElement(By.id("txtSearch")).clear();
			driver.findElement(By.id("txtSearch")).sendKeys("fccn");
			driver.findElement(By.id("btnSubmit")).click();
		});

		run("Search images instead of text", () -> driver.findElement(By.linkText("Imagens")).click());

		run("Click on advanced search link to navigate to advanced search page",
				() -> driver.findElement(By.id("pesquisa-avancada")).click());

		appendError(() -> {
			assertEquals("Check", "fccn", driver.findElement(By.xpath("//*[@id=\"adv_and\"]")).getAttribute("value"));
		});

		run("Set date start on advanced images form", () -> {
			driver.findElement(By.xpath("//*[@id=\"dateStart_top\"]/following-sibling::node()")).click();
			selectDateFromJqueryDatepicker(driver.findElement(By.id("ui-datepicker-div")), 2010, 5, 31);
		});

		run("Set date end on advanced images form", () -> {
			driver.findElement(By.xpath("//*[@id=\"dateEnd_top\"]/following-sibling::node()")).click();
			selectDateFromJqueryDatepicker(driver.findElement(By.id("ui-datepicker-div")), 2012, 6, 1);
		});
		
		run("Set size on advanced images form",
				() -> new Select(driver.findElement(By.xpath("//*[@id=\"label-format-1\"]/*[@id=\"size\"]"))).selectByValue("sm"));
		
		run("Set type on advanced images form",
				() -> new Select(driver.findElement(By.xpath("//*[@id=\"type\"]"))).selectByValue("png"));

		run("Set site on advanced images form", () -> driver.findElement(By.id("site")).sendKeys("fccn.pt"));

		run("Click on search on arquivo.pt button", () -> driver.findElement(By.id("btnSubmitBottom")).click());

		System.out.println("Current url: " + driver.getCurrentUrl());

		appendError(() -> assertThat("Check image original origin/domain",
				driver.findElement(By.xpath("//*[@id=\"imageResults0\"]/p[1]")).getText(),
				containsString("arquivo-web.fccn")));

		appendError(() -> assertEquals("Check image date", "5 Agosto, 2010",
				driver.findElement(By.xpath("//*[@id=\"date0\"]")).getText()));

		appendError(() -> assertEquals("Check image thumbnail on base 64",
				"data:image/png;base64,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",
				driver.findElement(By.xpath("//*[@id=\"imageResults0\"]/h2/button/img")).getAttribute("src")));

		appendError(() -> assertEquals("Check search term contains expected value after advanced search",
				"fccn site:fccn.pt ", driver.findElement(By.id("txtSearch")).getAttribute("value")));

		appendError(() -> assertEquals("Check date start contains expected value after advanced search", "31/05/2010",
				driver.findElement(By.id("dateStart_top")).getAttribute("value")));

		appendError(() -> assertEquals("Check date end contains expected value after advanced search", "01/06/2012",
				driver.findElement(By.id("dateEnd_top")).getAttribute("value")));

		appendError(() -> assertEquals("Check size contains expected value after advanced search", "sm",
				driver.findElement(By.id("sizeFormInput")).getAttribute("value")));

		appendError(() -> assertEquals("Check type contains expected value after advanced search", "png",
				driver.findElement(By.id("typeFormInput")).getAttribute("value")));
	}

	public static void selectDateFromJqueryDatepicker(WebElement datePickerDiv, int expYear, int expMonth, int expDay) {
		// handle 2 digit years
		if (expYear < 100)
			expYear += 2000;

		// select the given year
		Select yearSel = new Select(datePickerDiv.findElement(By.className("ui-datepicker-year")));
		yearSel.selectByValue(String.valueOf(expYear));

		// select the given month
		Select monthSel = new Select(datePickerDiv.findElement(By.className("ui-datepicker-month")));
		monthSel.selectByValue(String.valueOf(expMonth - 1)); // value start @ 0 so we need the -1

		// get the table
		WebElement calTable = datePickerDiv.findElement(By.className("ui-datepicker-calendar"));
		// click on the correct/given cell/date
		calTable.findElement(By.linkText(String.valueOf(expDay))).click();
	}
}
