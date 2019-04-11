package pt.fccn.arquivo.example;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.openqa.selenium.By;

import pt.fccn.arquivo.selenium.WebDriverTestBase;

public class ExampleTest extends WebDriverTestBase {
	@Test
	public void testSimpleSearch() {
		driver.get(testURL + "/");
		driver.findElement(By.cssSelector("#lga")).click();
		driver.findElement(By.cssSelector("#gbqfq")).clear();
		driver.findElement(By.cssSelector("#gbqfq")).sendKeys("teste");
		assertEquals("teste - Google Search", driver.getTitle());
	}
}
