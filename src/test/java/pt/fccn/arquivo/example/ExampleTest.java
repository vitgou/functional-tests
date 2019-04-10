package pt.fccn.arquivo.example;

import org.openqa.selenium.*;

import pt.fccn.arquivo.selenium.WebDriverTestBase;

import org.junit.*;

import static org.junit.Assert.*;

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
