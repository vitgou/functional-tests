package pt.fccn.arquivo.tests;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;

import java.io.File;
import java.io.InputStreamReader;
import java.util.Arrays;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.junit.Test;
import org.openqa.selenium.By;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import pt.fccn.arquivo.selenium.WebDriverTestBaseParalell;
import pt.fccn.arquivo.tests.util.ReplayUtils;

/**
 * Regression memorial sites test.
 *
 * <p>
 * 1. Go to memorial site URL
 * </p>
 *
 * <p>
 * 2. Check some visible text on redirect page should contain some text to
 * inform user before redirect to Arquivo.pt
 * </p>
 *
 * <p>
 * 3. Click on button to redirect to Arquivo.pt
 * </p>
 * <p>
 * 4. Check some text on wayback page
 * </p>
 *
 * <p>
 * 5. Check wayback page url
 * </p>
 *
 * </ul>
 *
 * @author Ivo Branco <ivo.branco@fccn.pt>
 *
 */
public class MemorialTest extends WebDriverTestBaseParalell {

	public MemorialTest(String os, String version, String browser, String deviceName, String deviceOrientation) {
		super(os, version, browser, deviceName, deviceOrientation);
	}

	@Test
	public void testMemorialSites() throws Exception {
		ObjectMapper mapper = new ObjectMapper(new YAMLFactory());

		MemorialTestConfigYAMLFile configsy = mapper.readValue(
				new InputStreamReader(ClassLoader.getSystemResourceAsStream("memorial_config.yaml")),
				MemorialTestConfigYAMLFile.class);

		Arrays.asList(configsy.getConfigs()).forEach(config -> {

			String url = config.getUrl();
			System.out.println(String.format("Begin checking url: %s with full configuration as: %s", url,
					ReflectionToStringBuilder.toString(config, ToStringStyle.MULTI_LINE_STYLE)));

			// go to memorial site URL
			driver.get(url);

			appendError(() -> {
				assertThat(
						"Check some visible text on redirect page should contain some text to inform user before redirect to Arquivo.pt",
						driver.findElement(By.xpath("/html")).getText(), containsString(config.getRedirectPageText()));
			});

			run("Click on button to redirect to Arquivo.pt",
					() -> driver.findElement(By.xpath(config.getButtonXpath())).click());

			run("Check some text on wayback page",
					() -> ReplayUtils.checkTextOnReplayPage(driver, config.getWaybackText()));

			appendError(() -> assertThat("Check wayback page url", driver.getCurrentUrl(),
					containsString(config.getWaybackUrl())));

			System.out.println(String.format("End checking url: %s", url));

		});
	}

	public static class MemorialTestConfigYAMLFile {
		MemorialTestConfig[] configs;

		public MemorialTestConfig[] getConfigs() {
			return configs;
		}

		public void setConfigs(MemorialTestConfig[] configs) {
			this.configs = configs;
		}

	}

	public static class MemorialTestConfig {
		private String url;
		private String redirectPageText;
		private String buttonXpath;
		private String waybackUrl;
		private String waybackText;

		public String getUrl() {
			return url;
		}

		public void setUrl(String url) {
			this.url = url;
		}

		public String getRedirectPageText() {
			return redirectPageText;
		}

		public void setRedirectPageText(String redirectPageText) {
			this.redirectPageText = redirectPageText;
		}

		public String getButtonXpath() {
			// //a[contains(@class,'myButton')]
			return buttonXpath != null ? buttonXpath : "html/body/div/div/a";
		}

		public void setButtonXpath(String buttonXpath) {
			this.buttonXpath = buttonXpath;
		}

		public String getWaybackUrl() {
			return waybackUrl;
		}

		public void setWaybackUrl(String waybackUrl) {
			this.waybackUrl = waybackUrl;
		}

		public String getWaybackText() {
			return waybackText;
		}

		public void setWaybackText(String waybackText) {
			this.waybackText = waybackText;
		}

	}
}
