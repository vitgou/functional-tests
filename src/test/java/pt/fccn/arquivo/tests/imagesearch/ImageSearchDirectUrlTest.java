package pt.fccn.arquivo.tests.imagesearch;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import pt.fccn.saw.selenium.WebDriverTestBaseParalell;

/**
 * 
 * @author ivo.branco@fccn.pt
 *
 */
public class ImageSearchDirectUrlTest extends WebDriverTestBaseParalell {

	public ImageSearchDirectUrlTest(String os, String version, String browser, String deviceName,
			String deviceOrientation) {
		super(os, version, browser, deviceName, deviceOrientation);
	}

	@Test
	public void testImageSearchDirectUrl() throws Exception {

		String url = testURL
				+ "/images.jsp?l=en&size=all&type=&tools=off&safeSearch=on&query=fccn&btnSubmit=Search&dateStart=26%2F06%2F2007&dateEnd=27%2F06%2F2007";
		driver.get(url);

		WebElement resultsUl = driver.findElement(By.id("resultsUl"));
		assertNotNull("Should exists an resultsUl after searching for images", resultsUl);

		appendError(() -> {
			List<WebElement> liList = resultsUl.findElements(By.xpath(".//li"));
			assertEquals("Verify results count", 1, liList.size());
		});

		appendError(() -> assertThat("Check image original origin/domain",
				driver.findElement(By.xpath("//*[@id=\"imageResults0\"]/p[1]")).getText(),
				containsString("sag.fccn.pt")));
	}
}
