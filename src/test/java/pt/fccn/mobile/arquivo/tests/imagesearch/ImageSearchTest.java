package pt.fccn.mobile.arquivo.tests.imagesearch;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import pt.fccn.arquivo.selenium.WebDriverTestBaseParalell;

/**
 * 
 * @author fernando.melo@fccn.pt  
 * @author ivo.branco@fccn.pt
 *
 */

public class ImageSearchTest extends WebDriverTestBaseParalell {

	/**
	 * Test the search of one term in the index interface.
	 */
	public ImageSearchTest(String os, String version, String browser, String deviceName, String deviceOrientation) {
		super(os, version, browser, deviceName, deviceOrientation);
	}

	@Test
	public void testImageSearchOneTerm() throws Exception {
		
		WebDriverWait wait = new WebDriverWait(driver, 5);
		
		run("Search FCCN term", () -> {
			driver.findElement(By.id("txtSearch")).clear();
			driver.findElement(By.id("txtSearch")).sendKeys("fccn");			
			driver.findElement(By.xpath("//*[@id=\"buttonSearch\"]/button")).click();
		});

		run("Search images instead of text", () -> driver.findElement(By.id("BotaoImagens")).click());
		
		JavascriptExecutor je = (JavascriptExecutor) driver;		 
		je.executeScript("arguments[0].scrollIntoView(true);", driver.findElement(By.id("imageResults1")));		
		
		run("Click/open one image on search results to open modal",
				() -> driver.findElement(By.id("imageResults0")).click());
		
		/*Sleeping for 25 seconds - maybe there is a better way to wait for the image viewer
		 * TODO:: figure out why it is taking so long for image viewer to be opened
		 * TODO:: improve performance? 
		 */
		//Thread.sleep(25000);
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("//*[@id=\\\"card0\\\"]/ion-card-content[1]/ion-list/ion-item[1]/h5/a")));
		
		/*appendError(() -> {
			assertTrue("First image details should be shown after clicking on it",
				driver.findElement(By.xpath("//*[@id=\"card0\"]/ion-card-content[1]/ion-list/ion-item[1]/h5/a")).isDisplayed());
		});*/

		appendError(() -> {
			assertEquals("Check image name on opened modal", "FCCN",
				driver.findElement(By.xpath("//*[@id=\"card0\"]/ion-card-content[1]/ion-list/ion-item[1]/h5/a")).getText());
		});

		appendError(() -> {
			assertEquals("Check image original link on opened modal",
					"wiki.di.uminho.pt/twiki/pub...I/fccn.jpg",
					driver.findElement(By.xpath("//*[@id=\"card0\"]/ion-card-content[1]/ion-list/ion-item[2]/h5")).getText());
		});

		appendError(() -> {
			assertEquals("Check image type and size on opened modal", "jpeg 319 x 69",
					driver.findElement(By.xpath("//*[@id=\"card0\"]/ion-card-content[1]/ion-list/ion-item[3]/h5")).getText());
		});

		appendError(() -> {
			assertEquals("Check image date on opened modal", "7 Julho, 2007",
					driver.findElement(By.xpath("//*[@id=\"card0\"]/ion-card-content[1]/ion-list/ion-item[4]/h5")).getText());
		});

		appendError(() -> {
			assertEquals("Check page attributes in image viewer", "Segundo Ciclo em Informática - Univer...",
					driver.findElement(By.xpath("//*[@id=\"card0\"]/ion-card-content[2]/ion-list/ion-item[1]/a/h5")).getText());
		});
		
		appendError(() -> {
			assertEquals("Check page attributes in image viewer","wiki.di.uminho.pt/twiki/bin/view/Mestrado/TPI",
					driver.findElement(By.xpath("//*[@id=\"card0\"]/ion-card-content[2]/ion-list/ion-item[2]/a/h5")).getText());
		});
		
		appendError(() -> {
			assertEquals("Check page attributes in image viewer", "7 Julho, 2007",
					driver.findElement(By.xpath("//*[@id=\"card0\"]/ion-card-content[2]/ion-list/ion-item[3]/h5")).getText());
		});
		
		appendError(() -> { //Click in Details button
			driver.findElement(By.xpath("//*[@id=\"card0\"]/ion-row[1]/ion-col[2]/ion-button/ion-icon")).click();
		});
		
		/**************************/
		/*** Details attributes ***/
		/**************************/
		
		appendError(() -> { 
			assertEquals("Check image detail page contains page url","http://wiki.di.uminho.pt/twiki/bin/view/Mestrado/TPI",
					driver.findElement(By.xpath("//*[@id=\"detailsCard0\"]/ion-card-content[1]/ion-list/ion-item[1]/h5/a")).getText());
		});
		
		appendError(() -> {
			assertThat("Check image detail page contains page timestamp",
					driver.findElement(By.xpath("//*[@id=\"detailsCard0\"]/ion-card-content[1]/ion-list/ion-item[2]/h5")).getText(),
					containsString("20070707201604"));
		});

		appendError(() -> {
			assertThat("Check image detail page contains page timestamp",
					driver.findElement(By.xpath("//*[@id=\"detailsCard0\"]/ion-card-content[1]/ion-list/ion-item[3]/h5")).getText(),
					containsString("Segundo Ciclo em Informática - Universidade do Minho - TWiki"));
		});
		
		appendError(() -> { 
			assertThat("Check image detail page contains page timestamp",
					driver.findElement(By.xpath("//*[@id=\"detailsCard0\"]/ion-card-content[2]/ion-list/ion-item[1]/h5/a")).getText(),
					containsString("http://wiki.di.uminho.pt/twiki/pub/Mestrado/TPI/fccn.jpg"));
		});
		
		appendError(() -> { 
			assertThat("Check image detail page contains page timestamp",
					driver.findElement(By.xpath("//*[@id=\"detailsCard0\"]/ion-card-content[2]/ion-list/ion-item[2]/h5")).getText(),
					containsString("20070707201644"));
		});
		
		appendError(() -> { 
			assertThat("Check image detail page contains page timestamp",
					driver.findElement(By.xpath("//*[@id=\"detailsCard0\"]/ion-card-content[2]/ion-list/ion-item[3]/h5")).getText(),
					containsString("FCCN"));
		});
		
		appendError(() -> { 
			assertThat("Check image detail page contains page timestamp",
					driver.findElement(By.xpath("//*[@id=\"detailsCard0\"]/ion-card-content[2]/ion-list/ion-item[4]/h5")).getText(),
					containsString("319 x 69 pixels"));
		});

		
		appendError(() -> { 
			assertThat("Check image detail page contains page timestamp",
					driver.findElement(By.xpath("//*[@id=\"detailsCard0\"]/ion-card-content[2]/ion-list/ion-item[5]/h5")).getText(),
					containsString("jpeg"));
		});
		
		appendError(() -> { 
			assertThat("Check image detail page contains page timestamp",
					driver.findElement(By.xpath("//*[@id=\"detailsCard0\"]/ion-card-content[2]/ion-list/ion-item[6]/h5")).getText(),
					containsString("0.999"));
		});
		
		appendError(() -> { 
			assertThat("Check image detail page contains page timestamp",
					driver.findElement(By.xpath("//*[@id=\"detailsCard0\"]/ion-card-content[3]/ion-list/ion-item/h5")).getText(),
					containsString("IA"));
		});
		
		/**************************/
		
		
		run("Close image details modal", () -> {
			driver.findElement(By.id("closeCard0")).click();
		});

		run("Close image first modal", () -> {
			driver.findElement(By.id("close0")).click();
		});
	}

}
