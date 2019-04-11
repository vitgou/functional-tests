package pt.fccn.arquivo.tests;

import static org.junit.Assert.fail;

import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
//import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;

public class ExampleTest{
	private WebDriver driver;
	private String baseUrl;
	private StringBuffer verificationErrors = new StringBuffer();
	@Before
	public void setUp() throws Exception {
		driver = new FirefoxDriver();
		baseUrl = "http://www.arquivo.pt/";
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	}

	@Test
	public void testExample() throws Exception {
		driver.get(baseUrl + "/index.jsp?l=pt");
		driver.findElement(By.cssSelector("#txtSearch")).clear();
		driver.findElement(By.cssSelector("#txtSearch")).sendKeys("testing selenium");
		driver.findElement(By.cssSelector("#btnSubmit")).click();
	}

	@After
	public void tearDown() throws Exception {
		driver.quit();
		String verificationErrorString = verificationErrors.toString();
		if (!"".equals(verificationErrorString)) {
			fail(verificationErrorString);
		}
	}
}
