package pt.fccn.mobile.arquivo.tests.imagesearch;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import pt.fccn.arquivo.selenium.Retry;
import pt.fccn.arquivo.selenium.WebDriverTestBaseParalell;

/**
 *
 * @author ivo.branco@fccn.pt
 *
 */
public class ImageSearchDirectUrlTest extends WebDriverTestBaseParalell {

	private static final String IMAGE_SEARCH_DIRECT_URL = "/images.jsp?l=en&size=all&type=&tools=off&safeSearch=on&query=fccn&btnSubmit=Search&dateStart=26%2F06%2F2007&dateEnd=27%2F06%2F2007";

	public ImageSearchDirectUrlTest(String os, String version, String browser, String deviceName,
			String deviceOrientation) {
		super(os, version, browser, deviceName, deviceOrientation);
	}

	@Test
	@Retry
	public void testImageSearchDirectUrlNoLanguage() throws Exception {
		testImageSearchDirectUrl(testURL + IMAGE_SEARCH_DIRECT_URL, Optional.empty());
	}

	@Test
	@Retry
	public void testImageSearchDirectUrlPt() throws Exception {
		testImageSearchDirectUrl(testURL + IMAGE_SEARCH_DIRECT_URL + "&l=pt", Optional.empty());
	}

	@Test
	@Retry
	public void testImageSearchDirectUrlEn() throws Exception {
		testImageSearchDirectUrl(testURL + IMAGE_SEARCH_DIRECT_URL + "&l=en", Optional.of("Images"));
	}

	private void testImageSearchDirectUrl(String url, Optional<String> imageButtonText) {
		driver.get(url);

		WebElement firstImage = driver.findElement(By.id("imageResults0"));
		assertNotNull("Should exist at least one image", firstImage);

		appendError(() -> {
			List<WebElement> photoDivList = driver.findElements(By.xpath("//*[@id=\"photos\"]/div"));
			assertEquals("Verify results count", 1, photoDivList.size());
		});

		appendError(() -> assertThat("Check image original origin/domain",
				driver.findElement(By.xpath("//*[@id=\"imageResults0\"]/p[1]")).getText(),
				containsString("sag.fccn.pt")));

		if (imageButtonText.isPresent()) {
			appendError(() -> assertEquals("Check page language by verifying images button text", imageButtonText.get(),
					driver.findElementById("ImageButton").getText()));
		}
	}

}
