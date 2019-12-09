package pt.fccn.mobile.arquivo.tests.pagesearch;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.time.LocalDate;
import java.util.List;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import pt.fccn.arquivo.selenium.Retry;
import pt.fccn.arquivo.selenium.WebDriverTestBaseParalell;
import pt.fccn.mobile.arquivo.utils.IonicDatePicker;

/**
 *
 * @author Ivo Branco <ivo.branco@fccn.pt>
 *
 */
public class PageSearchLimitedDatesFromHomepageTest extends WebDriverTestBaseParalell {

	public PageSearchLimitedDatesFromHomepageTest(String os, String version, String browser, String deviceName,
			String deviceOrientation) {
		super(os, version, browser, deviceName, deviceOrientation);
	}

	@Test
	@Retry
	public void pageSearchLimitedDatesFromHomepageTest() {
		run("Put fccn on search box", () -> {
			driver.findElement(By.id("txtSearch")).clear();
			driver.findElement(By.id("txtSearch")).sendKeys("fccn");
		});

		run("Open from date picker", () -> waitUntilElementIsVisibleAndGet(By.id("sliderCircleLeft")).click());
		LocalDate fromDate = LocalDate.of(1996, 10, 12);
		run("Insert " + fromDate.toString() + " on start date picker",
				() -> IonicDatePicker.changeTo(driver, fromDate));

		run("Open until date picker", () -> waitUntilElementIsVisibleAndGet(By.id("sliderCircleRight")).click());
		LocalDate untilDate = LocalDate.of(1996, 10, 14);
		run("Insert " + untilDate.toString() + " on end date picker",
				() -> IonicDatePicker.changeTo(driver, untilDate));

		run("Click on search button", () -> driver.findElement(By.xpath("//*[@id=\"buttonSearch\"]/button")).click());

		run("Wait until search results are shown", () -> waitUntilElementIsVisibleAndGet(By.id("resultados-lista")));

		appendError("Check first result url", () -> {
			List<WebElement> wes = driver.findElementsByXPath("//*[@id=\"resultados-lista\"]//*[@class=\"url\"]");
			assertTrue("Mininium of urls should be 1", wes.size() > 0);

			WebElement we = wes.get(0);
			assertThat("Check first result url", we.getText(), containsString("fccn.pt"));

			wes = driver.findElementsByXPath("//*[@id=\"resultados-lista\"]//*[@class=\"urlBlock\"]//a");
			we = wes.get(0);
			String href = we.getAttribute("href");
			assertThat("Check link to wayback timestamp", href, containsString("/19961013145650/"));

			assertThat("Check link to wayback url", href, containsString("http://www.fccn.pt/"));
		});

		appendError("Check first result title", () -> {
			List<WebElement> wes = driver.findElementsByXPath("//*[@id=\"resultados-lista\"]//*[@class=\"urlBlock\"]//h2");
			assertTrue("Mininium of title should be 1", wes.size() > 0);

			WebElement we = wes.get(0);
			assertEquals("Check first result title", "http://www.fccn.pt/", we.getText());
		});

		appendError("Check first result version", () -> {
			WebElement we = driver
					.findElementByXPath("//*[@id=\"resultados-lista\"]/ul/li[1]//div[@class=\"list-versions-div\"]");

			assertThat("Check first result version", we.getText(), containsString("13 Outubro, 1996"));
		});

		appendError("Check first result summary", () -> {
			WebElement we = driver
					.findElementByXPath("//*[@id=\"resultados-lista\"]/ul/li[1]//span[@class=\"resumo\"]");

			assertThat("Check first result version", we.getText(), containsString("Av. Brasil"));
		});
	}

}
