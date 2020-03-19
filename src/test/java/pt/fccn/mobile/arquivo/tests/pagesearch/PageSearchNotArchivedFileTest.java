package pt.fccn.mobile.arquivo.tests.pagesearch;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import pt.fccn.arquivo.selenium.Retry;
import pt.fccn.arquivo.selenium.WebDriverTestBaseParalell;

/**
 * 
 * @author pedro.gomes.fccn@gmail.com
 *
 */

public class PageSearchNotArchivedFileTest extends WebDriverTestBaseParalell {

	public PageSearchNotArchivedFileTest(String os, String version, String browser, String deviceName,
			String deviceOrientation) {
		super(os, version, browser, deviceName, deviceOrientation);
	}

	@Test
	@Retry
	public void notArchivedFileTest() throws Exception {
		run("Search fccn", () -> {
			driver.findElement(By.id("txtSearch")).clear();
			driver.findElement(By.id("txtSearch")).sendKeys("fccn");
			driver.findElement(By.xpath("//*[@id=\"buttonSearch\"]/button")).click();
		});

		waitUntilElementIsVisibleAndGet(By.id("resultados-lista"));

		run("click first position", () -> {
			driver.findElement(By.xpath("//*[@id=\"resultados-lista\"]/ul/li[1]/div[1]/a")).click();
		});

		appendError("Check if page is not archived", () -> new WebDriverWait(driver, 20)
				.until(ExpectedConditions.invisibilityOfElementLocated(By.id("pageIsNotArchived"))));

		driver.navigate().back();

		waitUntilElementIsVisibleAndGet(By.id("resultados-lista"));

		run("click second position", () -> {
			driver.findElement(By.xpath("//*[@id=\"resultados-lista\"]/ul/li[2]/div[1]/a")).click();
		});

		appendError("Check if page is not archived", () -> new WebDriverWait(driver, 20)
				.until(ExpectedConditions.invisibilityOfElementLocated(By.id("pageIsNotArchived"))));

		driver.navigate().back();

		waitUntilElementIsVisibleAndGet(By.id("resultados-lista"));

		run("click third position", () -> {
			driver.findElement(By.xpath("//*[@id=\"resultados-lista\"]/ul/li[2]/div[1]/a")).click();
		});

		appendError("Check if page is not archived", () -> new WebDriverWait(driver, 20)
				.until(ExpectedConditions.invisibilityOfElementLocated(By.id("pageIsNotArchived"))));

	}

}
