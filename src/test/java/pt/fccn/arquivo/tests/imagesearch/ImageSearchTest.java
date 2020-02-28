package pt.fccn.arquivo.tests.imagesearch;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.openqa.selenium.By;

import pt.fccn.arquivo.selenium.WebDriverTestBaseParalell;

/**
 * 
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
			driver.findElement(By.id("btnSubmit")).click();
		});

		run("Search images instead of text", () -> driver.findElement(By.linkText("Imagens")).click());

		run("Click/open first image on search results to open modal",
				() -> driver.findElement(By.xpath("//*[@id=\"imageResults0\"]/h2/button/img")).click());

		appendError(() -> {
			assertTrue("First image details should be shown after clicking on it",
					driver.findElement(By.xpath("//*[@id=\"imgTitleLabel0\"]/a")).isDisplayed());
		});

		appendError(() -> {
			assertEquals("Check image name on opened modal", "FCCN",
					driver.findElement(By.xpath("//*[@id=\"imgTitleLabel0\"]/a")).getText());
		});

		appendError(() -> {
			assertEquals("Check image original link on opened modal",
					"wiki.di.uminho.pt/twiki/pub/Mestrado/TPI/fccn.jpg",
					driver.findElement(By.xpath("//*[@id=\"testViewer0\"]/div[2]/div/div[1]/h2[2]")).getText());
		});

		appendError(() -> {
			assertEquals("Check image type and size on opened modal", "jpeg 319 x 69",
					driver.findElement(By.xpath("//*[@id=\"testViewer0\"]/div[2]/div/div[1]/h2[3]")).getText());
		});

		appendError(() -> {
			assertEquals("Check image date on opened modal", "7 Julho, 2007",
					driver.findElement(By.xpath("//*[@id=\"testViewer0\"]/div[2]/div/div[1]/h2[4]")).getText());
		});

		appendError(() -> {
			assertEquals("Check page anchor text", "Segundo Ciclo em Informática - Univer...",
					driver.findElement(By.xpath("//*[@id=\"testViewer0\"]/div[2]/div/div[2]/div/h2[1]/a")).getText());
		});

		appendError(() -> {
			assertEquals("Check original page link name", "wiki.di.uminho.pt/twiki/bin/view/Mestrado/TPI",
					driver.findElement(By.xpath("//*[@id=\"testViewer0\"]/div[2]/div/div[2]/div/h2[2]")).getText());
		});

		appendError(() -> {
			assertEquals("Check page date", "7 Julho, 2007",
					driver.findElement(By.xpath("//*[@id=\"testViewer0\"]/div[2]/div/div[2]/div/h2[3]")).getText());
		});

		run("Click on show image details button on image modal", () -> {
			driver.findElement(By.id("showDetails")).click();
		});

		appendError(() -> {
			assertThat("Check image detail page contains page url",
					driver.findElement(By.xpath("//*[@id=\"imageDetailPageElements\"]")).getText(),
					containsString("http://wiki.di.uminho.pt/twiki/bin/view/Mestrado/TPI"));
		});

		appendError(() -> {
			assertThat("Check image detail page contains page timestamp",
					driver.findElement(By.xpath("//*[@id=\"imageDetailPageElements\"]")).getText(),
					containsString("20070707201604"));
		});

		appendError(() -> {
			assertThat("Check image detail page contains page title",
					driver.findElement(By.xpath("//*[@id=\"imageDetailPageElements\"]")).getText(),
					containsString("Segundo Ciclo em Informática - Universidade do Minho - TWiki"));
		});

		appendError(() -> {
			assertThat("Check image detail image elements contains original src",
					driver.findElement(By.xpath("//*[@id=\"imageDetailImageElements\"]")).getText(),
					containsString("http://wiki.di.uminho.pt/twiki/pub/Mestrado/TPI/fccn.jpg"));
		});

		appendError(() -> {
			assertThat("Check image detail image elements contains timestamp",
					driver.findElement(By.xpath("//*[@id=\"imageDetailImageElements\"]")).getText(),
					containsString("20070707201644"));
		});

		appendError(() -> {
			assertThat("Check image detail image elements contains image title",
					driver.findElement(By.xpath("//*[@id=\"imageDetailImageElements\"]")).getText(),
					containsString("FCCN"));
		});

		appendError(() -> {
			assertThat("Check image detail image elements contains resolution value",
					driver.findElement(By.xpath("//*[@id=\"imageDetailImageElements\"]")).getText(),
					containsString("319 x 69 pixels"));
		});

		appendError(() -> {
			assertThat("Check image detail image elements contains mimetype value",
					driver.findElement(By.xpath("//*[@id=\"imageDetailImageElements\"]")).getText(),
					containsString("image/jpeg"));
		});

		appendError(() -> {
			assertThat("Check image detail image elements contains safe value",
					driver.findElement(By.xpath("//*[@id=\"imageDetailImageElements\"]")).getText(),
					containsString("0.999"));
		});

		appendError(() -> {
			assertThat("Check image detail about collection contains expected collection value",
					driver.findElement(By.xpath("//*[@id=\"imageDetailCollectionElements\"]")).getText(),
					containsString("IA"));
		});

		run("Close image details modal", () -> {
			driver.findElement(By.id("detailsDialogClose")).click();
		});

		run("Close image first modal", () -> {
			driver.findElement(By.xpath("//*[@id=\"testViewer0\"]/button[1]")).click();
		});
	}

}