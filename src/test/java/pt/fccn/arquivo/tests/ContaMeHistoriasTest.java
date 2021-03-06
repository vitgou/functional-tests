package pt.fccn.arquivo.tests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.openqa.selenium.By;

import pt.fccn.arquivo.selenium.WebDriverTestBaseParalell;

/**
 *
 * @author Ivo Branco <ivo.branco@fccn.pt>
 *
 */
public class ContaMeHistoriasTest extends WebDriverTestBaseParalell {

	public ContaMeHistoriasTest(String os, String version, String browser, String deviceName,
			String deviceOrientation) {
		super(os, version, browser, deviceName, deviceOrientation);
	}

	@Test
	public void testImageSearchOneTerm() {
		driver.get("http://contamehistorias.pt");

		appendError(() -> {
			assertTrue("Check element contains arquivo.pt reference",
					driver.findElement(By.xpath("/html/body/footer/div/div/div[1]/div[1]/div")).getText()
							.contains("Arquivo.pt"));
		});

	}
}
