package pt.fccn.mobile.arquivo.tests.replay.menu;

import org.openqa.selenium.By;

import pt.fccn.arquivo.selenium.WebDriverTestBaseParalell;

public abstract class MenuWaybackTest extends WebDriverTestBaseParalell {

	public MenuWaybackTest(String os, String version, String browser, String deviceName, String deviceOrientation) {
		super(os, version, browser, deviceName, deviceOrientation);
	}

	protected void openMenu() {
		run("Open left menu", () -> {
			waitUntilElementIsVisibleAndGet(By.id("menuButton")).click();
			waitUntilElementIsVisibleAndGet(By.xpath("//*[@id=\"swiperWrapper\"]/div[1]"));
		});
	}

}
