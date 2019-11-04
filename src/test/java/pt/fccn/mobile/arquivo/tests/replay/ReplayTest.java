package pt.fccn.mobile.arquivo.tests.replay;

import java.io.InputStreamReader;
import java.util.Arrays;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.junit.Test;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import pt.fccn.arquivo.selenium.Retry;
import pt.fccn.arquivo.selenium.WebDriverTestBaseParalell;
import pt.fccn.arquivo.tests.util.ReplayUtils;

/**
 *
 * @author Ivo Branco <ivo.branco@fccn.pt>
 *
 */
public class ReplayTest extends WebDriverTestBaseParalell {

	private static final String WAYBACK_PATH = "/wayback";

	public ReplayTest(String os, String version, String browser, String deviceName, String deviceOrientation) {
		super(os, version, browser, deviceName, deviceOrientation);
	}

	@Test
	@Retry
	public void replayTest() throws Exception {

		ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
		ReplayTestConfigYAMLFile configsy = mapper.readValue(
				new InputStreamReader(ClassLoader.getSystemResourceAsStream("replay_test_config.yml")),
				ReplayTestConfigYAMLFile.class);

		Arrays.asList(configsy.getConfigs()).forEach(config -> {

			String url = config.getWaybackUrl();
			System.out.println(String.format("Begin checking url: %s with full configuration as: %s", url,
					ReflectionToStringBuilder.toString(config, ToStringStyle.MULTI_LINE_STYLE)));

			// go to replay url
			driver.get(this.testURL + config.getWaybackUrl());

			appendError("Check wayback page url",
					() -> new WebDriverWait(driver, 20).until(ExpectedConditions.urlContains(config.getWaybackUrl())));

			run("Check some text on wayback page",
					() -> ReplayUtils.checkTextOnReplayPage(driver, config.getXpath(), config.getText()));

			System.out.println(String.format("End checking url: %s", url));

		});
	}

	public static class ReplayTestConfigYAMLFile {
		ReplayTestConfig[] configs;

		public ReplayTestConfig[] getConfigs() {
			return configs;
		}

		public void setConfigs(ReplayTestConfig[] configs) {
			this.configs = configs;
		}

	}

	public static class ReplayTestConfig {
		private String timestamp;
		private String url;
		private String xpath;
		private String text;

		public String getTimestamp() {
			return timestamp;
		}

		public void setTimestamp(String timestamp) {
			this.timestamp = timestamp;
		}

		public String getUrl() {
			return url;
		}

		public void setUrl(String url) {
			this.url = url;
		}

		public String getXpath() {
			return xpath;
		}

		public void setXpath(String xpath) {
			this.xpath = xpath;
		}

		public String getText() {
			return text;
		}

		public void setText(String text) {
			this.text = text;
		}

		public String getWaybackUrl() {
			return WAYBACK_PATH + "/" + getTimestamp() + "/" + getUrl();
		}
	}
}
