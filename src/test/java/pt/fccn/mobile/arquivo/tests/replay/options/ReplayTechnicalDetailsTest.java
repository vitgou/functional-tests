package pt.fccn.mobile.arquivo.tests.replay.options;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import pt.fccn.arquivo.selenium.Retry;
import pt.fccn.arquivo.selenium.WebDriverTestBaseParalell;

/**
 *
 * @author Ivo Branco <ivo.branco@fccn.pt>
 *
 */
public class ReplayTechnicalDetailsTest extends WebDriverTestBaseParalell {

	private static final String WAYBACK_EXAMPLE = "/wayback/19961013145650/http://www.fccn.pt/";

	public ReplayTechnicalDetailsTest(String os, String version, String browser, String deviceName,
			String deviceOrientation) {
		super(os, version, browser, deviceName, deviceOrientation);
	}

	public String getBaseServiceUrl() {
		return this.testURL.replace("http://", "https://");
	}

	public String getBaseServiceHost() {
		try {
			return new URL(getBaseServiceUrl()).getHost();
		} catch (MalformedURLException e) {
			throw new RuntimeException("Error getting host of the test url", e);
		}
	}

	@Test
	@Retry
	public void replayTecnicalDetailsTest() {
		driver.get(this.testURL + WAYBACK_EXAMPLE);

		run("Open replay right menu", () -> waitUntilElementIsVisibleAndGet(By.id("replayMenuButton")).click());

		run("Click on tecnical details anchor", () -> waitUntilElementIsVisibleAndGet(By.id("a_moreinfo")).click());

		waitUntilElementIsVisibleAndGet(By.id("uglipop_popbox"));

		Map<String, WebElement> currentTechnicalDetailsMap = //
				driver //
						.findElementsByXPath("//*[@id=\"uglipop_popbox\"]/div/p[@class=\"modalparagraph\"]") //
						.stream() //
						.collect( //
								Collectors.toMap(//
										we -> we.getText().split(":")[0], //
										we -> we));

		List<Entry<String, String>> a = new ArrayList<>();
		//a.add(new SimpleEntry<>("title", "http://www.fccn.pt/"));
		a.add(new SimpleEntry<>("originalURL", "originalURL: http://www.fccn.pt/"));
		a.add(new SimpleEntry<>("linkToArchive", "linkToArchive: "+ getBaseServiceUrl() + "/wayback/19961013145650/http://www.fccn.pt/"));
		a.add(new SimpleEntry<>("tstamp", "tstamp: 19961013145650"));
		a.add(new SimpleEntry<>("contentLength", "contentLength: 3760"));
		//a.add(new SimpleEntry<>("digest", "digest: OWMAVER7CCNJWL2E5ZURDDKGCHWS7JJO")); Different digests between preprod.arquivo.pt and arquivo.pt
		a.add(new SimpleEntry<>("mimeType", "mimeType: text/html"));
		a.add(new SimpleEntry<>("linkToScreenshot", "linkToScreenshot: " + getBaseServiceUrl() + "/screenshot?url=https%3A%2F%2F" + getBaseServiceHost()
				+ "%2FnoFrame%2Freplay%2F19961013145650%2Fhttp%3A%2F%2Fwww.fccn.pt%2F"));
		a.add(new SimpleEntry<>("date", "date: 0845218610"));
		a.add(new SimpleEntry<>("encoding", "encoding: windows-1252"));
		a.add(new SimpleEntry<>("linkToNoFrame", "linkToNoFrame: " + getBaseServiceUrl() + "/noFrame/replay/19961013145650/http://www.fccn.pt/"));
		a.add(new SimpleEntry<>("statusCode", "statusCode: 200"));
		a.add(new SimpleEntry<>("collection", "collection: Roteiro"));
		a.add(new SimpleEntry<>("linkToExtractedText", "linkToExtractedText: " + getBaseServiceUrl() + "/textextracted?m=http%3A%2F%2Fwww.fccn.pt%2F%2F19961013145650"));
		a.add(new SimpleEntry<>("linkToMetadata", "linkToMetadata: " + getBaseServiceUrl() + "/textsearch?metadata=http%3A%2F%2Fwww.fccn.pt%2F%2F19961013145650"));
		a.add(new SimpleEntry<>("fileName", "fileName: AWP-Roteiro-20090510220155-00000"));
		a.add(new SimpleEntry<>("offset", "offset: 45198"));
		a.add(new SimpleEntry<>("linkToOriginalFile", "linkToOriginalFile: " + getBaseServiceUrl() + "/noFrame/replay/19961013145650id_/http://www.fccn.pt/"));
		a.add(new SimpleEntry<>("id", "id: 87"));
		a.add(new SimpleEntry<>("snippet", "snippet: Fundação para a Computação Científica Nacional \" A promoção de infraestruturas no domínio da"));
		
		a.forEach(e -> {
			String technicalField = e.getKey();
			String technicalValue = e.getValue();
			String currentValue = currentTechnicalDetailsMap.get(technicalField).getText();
			appendError(() -> {
				assertThat("Check " + technicalField, currentValue, containsString(technicalValue));
			});

		});

		appendError("Close technical detail modal", () -> driver.findElement(By.id("removeModal")).click());

		appendError("Check that tecnical details modal is closed when clicking on close button",
				() -> new WebDriverWait(driver, 20)
						.until(ExpectedConditions.invisibilityOfElementLocated(By.id("uglipop_popbox"))));
	}

}