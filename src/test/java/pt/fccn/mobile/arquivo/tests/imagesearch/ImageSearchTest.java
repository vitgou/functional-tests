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
		Thread.sleep(25000);
		appendError(() -> {
			assertTrue("First image details should be shown after clicking on it",
					driver.findElement(By.xpath("//*[@id=\"card0\"]/ion-card-content[1]/ion-list/ion-item[1]/h5/a")).isDisplayed());
		});

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
		/*
		 * TODO:: check page attributes in image viewer card (may need scroll to bottom of card
		 * TODO:: click in "View details" - may need scroll to top of card
		 * TODO:: check details attributes
		 * TODO:: close view details and close image card
		 * */
//
//		appendError(() -> {
//			assertEquals("Check page anchor text", "ISCAC o teu futuro passa por aqui!",
//					driver.findElement(By.xpath("//*[@id=\"card1\"]/ion-card-content[2]/ion-list/ion-item[1]/a/h5")).getText());
//		});
//
//		appendError(() -> {
//			assertEquals("Check original page link name", "www.iscac.pt/index.php?m=5_26&lang=PT&curso=911&uc=2183&b...",
//					driver.findElement(By.xpath("//*[@id=\"card1\"]/ion-card-content[2]/ion-list/ion-item[2]/h5")).getText());
//		});
//
//		appendError(() -> {
//			assertEquals("Check page date", "10 Novembro, 2016",
//					driver.findElement(By.xpath("//*[@id=\"card1\"]/ion-card-content[2]/ion-list/ion-item[3]/h5")).getText());
//		});

//		run("Click on show image details button on image modal", () -> {
//			driver.findElement(By.xpath("//*[@id=\"card1\"]/ion-row[1]/ion-col[2]/ion-button")).click();
//		});
//
//		appendError(() -> {
//			assertThat("Check image detail page contains page url",
//					driver.findElement(By.xpath("//*[@id=\"detailsCard1\"]/ion-card-content[1]/ion-list/ion-item[1]/h5/a")).getText(),
//					containsString("http://www.iscac.pt/index.php"));
//		});
//
//		appendError(() -> {
//			assertThat("Check image detail page contains page timestamp",
//					driver.findElement(By.xpath("//*[@id=\"detailsCard1\"]/ion-card-content[1]/ion-list/ion-item[2]/h5")).getText(),
//					containsString("20161110202424"));
//		});
//
//		appendError(() -> {
//			assertThat("Check image detail page contains page title",
//					driver.findElement(By.xpath("//*[@id=\"detailsCard1\"]/ion-card-content[1]/ion-list/ion-item[3]/h5")).getText(),
//					containsString("ISCAC o teu futuro passa por aqui!"));
//		});
//
//		appendError(() -> {
//			assertThat("Check image detail image elements contains original src",
//					driver.findElement(By.xpath("//*[@id=\"detailsCard1\"]/ion-card-content[2]/ion-list/ion-item[1]/h5/a")).getText(),
//					containsString("http://www.iscac.pt/files/footericones/01330444039.png"));
//		});
//
//		appendError(() -> {
//			assertThat("Check image detail image elements contains timestamp",
//					driver.findElement(By.xpath("//*[@id=\"detailsCard1\"]/ion-card-content[2]/ion-list/ion-item[2]/h5")).getText(),
//					containsString("20160817110504"));
//		});
//
//		appendError(() -> {
//			assertThat("Check image detail image elements contains image title",
//					driver.findElement(By.xpath("//*[@id=\"detailsCard1\"]/ion-card-content[2]/ion-list/ion-item[3]/h5")).getText(),
//					containsString("FCCN"));
//		});
//
//		appendError(() -> {
//			assertThat("Check image detail image elements contains resolution value",
//					driver.findElement(By.xpath("//*[@id=\"detailsCard1\"]/ion-card-content[2]/ion-list/ion-item[4]/h5")).getText(),
//					containsString("70 x 60 pixels"));
//		});
//
//		appendError(() -> {
//			assertThat("Check image detail image elements contains mimetype value",
//					driver.findElement(By.xpath("//*[@id=\"detailsCard1\"]/ion-card-content[2]/ion-list/ion-item[5]/h5")).getText(),
//					containsString("png"));
//		});
//
//		appendError(() -> {
//			assertThat("Check image detail image elements contains safe value",
//					driver.findElement(By.xpath("//*[@id=\"detailsCard1\"]/ion-card-content[2]/ion-list/ion-item[6]/h5")).getText(),
//					containsString("0.996"));
//		});
//
//		appendError(() -> {
//			assertThat("Check image detail about collection contains expected collection value",
//					driver.findElement(By.xpath("//*[@id=\"detailsCard1\"]/ion-card-content[3]/ion-list/ion-item/h5")).getText(),
//					containsString("AWP22"));
//		});
//
//		run("Close image details modal", () -> {
//			driver.findElement(By.id("closeCard1")).click();
//		});
//
//		run("Close image first modal", () -> {
//			driver.findElement(By.id("close1")).click();
//		});
	}

}
