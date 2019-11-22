package pt.fccn.mobile.arquivo.tests.urlsearch;

import static org.junit.Assert.assertNotNull;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import pt.fccn.arquivo.selenium.Retry;
import pt.fccn.arquivo.selenium.WebDriverTestBaseParalell;
import pt.fccn.mobile.arquivo.utils.LocaleUtils;
import pt.fccn.mobile.arquivo.utils.LocalizedString;

/**
 *
 * @author Ivo Branco <ivo.branco@fccn.pt>
 *
 */
public class URLSearchListNotContainsSomeHttpCodesTest extends WebDriverTestBaseParalell {

	public URLSearchListNotContainsSomeHttpCodesTest(String os, String version, String browser, String deviceName,
			String deviceOrientation) {
		super(os, version, browser, deviceName, deviceOrientation);
	}

	@Test
	@Retry
	public void urlSearchListNotContains406PT() {
		test("http://www.caleida.pt/saramago/", LocaleUtils.PORTUGUESE, Arrays.asList("19980205082901"),
				Arrays.asList("20120131163447"));
	}

	@Test
	@Retry
	public void urlSearchListNotContains404EN() {
		test("http://www.caleida.pt/saramago/", LocaleUtils.ENGLISH, Arrays.asList("20000413142115"),
				Arrays.asList("20160210151550"));
	}

	@Test
	@Retry
	public void urlSearchListNotContains403PT() {
		test("sapo.pt", LocaleUtils.PORTUGUESE, Arrays.asList("19971210144509"), Arrays.asList("20150424043204"));
	}

	@Test
	@Retry
	public void urlSearchListNotContains503EN() {
		test("record.pt", LocaleUtils.ENGLISH, Arrays.asList("19981202152653"), Arrays.asList("20171031183600"));
	}

	private void test(String url, Locale locale, List<String> visibleVersions, List<String> invisibleVersions) {

		LocaleUtils.changeLanguageTo(this, locale);

		run("Search url", () -> {
			driver.findElement(By.id("txtSearch")).clear();
			driver.findElement(By.id("txtSearch")).sendKeys(url);
			driver.findElement(By.xpath("//*[@id=\"buttonSearch\"]/button")).click();
		});

		run("Change to list mode if not in it", () -> {
			WebElement resultsGridCurrentType = driver.findElementByXPath("//*[@id=\"layoutTV\"]/h4");

			LocalizedString listText = new LocalizedString().pt("Lista").en("List");

			if (!resultsGridCurrentType.getText().contains(listText.apply(locale))) {
				driver.findElementByXPath("//*[@id=\"layoutTV\"]/button").click();
			}
		});

		run("Check specific timestamp should exist", () -> {
			visibleVersions.stream().forEach(version -> {
				WebElement e = driver.findElement(By.id(version));
				assertNotNull(String.format("The timestamp %s for %s shoud be presented", version, url), e);
			});
		});

		run("Check specific timestamp should not exist", () -> {
			visibleVersions.stream().forEach(version -> {
				String msg = String.format("The timestamp %s for %s shoud not exist", version, url);

				appendError(msg, () -> new WebDriverWait(driver, 20)
						.until(ExpectedConditions.invisibilityOfElementLocated(By.id(version))));
			});
		});
	}

}
