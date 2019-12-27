package pt.fccn.mobile.arquivo.tests.pagesearch;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.openqa.selenium.By;

import pt.fccn.arquivo.selenium.Retry;
import pt.fccn.arquivo.selenium.WebDriverTestBaseParalell;

/**
 * 
 * @author pedro.gomes.fccn@gmail.com
 *
 */

public class PageSearchNotSpamTest extends WebDriverTestBaseParalell {

	public PageSearchNotSpamTest(String os, String version, String browser, String deviceName,
			String deviceOrientation) {
		super(os, version, browser, deviceName, deviceOrientation);

	}

	@Test
	@Retry
	public void pageSearchNotSpamTest() throws Exception {
		run("Search Lisboa", () -> {
			driver.findElement(By.id("txtSearch")).clear();
			driver.findElement(By.id("txtSearch")).sendKeys("Lisboa");
			driver.findElement(By.xpath("//*[@id=\"buttonSearch\"]/button")).click();
		});

		waitUntilElementIsVisibleAndGet(By.id("resultados-lista"));

		int OLXanchorsCount = driver
				.findElementsByXPath("//*[@id=\"resultados-lista\"]//*[@class=\"url\"][contains(text(),'olx')]").size();

		System.out.println("OLX anchors count " + OLXanchorsCount);

		long OLXemsCount = driver.findElementsByXPath("//*[@id=\"resultados-lista\"]") //
				.stream() //
				.filter(em -> em.getText().toLowerCase().contains("olx")) //
				.count();

		System.out.println("OLX word count " + OLXemsCount);

		assertTrue("None of the results should show something related with olx", OLXemsCount + OLXanchorsCount == 0);

	}

}
