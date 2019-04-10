package pt.fccn.arquivo.tests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import pt.fccn.arquivo.pages.Arcproxyinspection;
import pt.fccn.arquivo.pages.IndexPage;
import pt.fccn.arquivo.selenium.Retry;
import pt.fccn.arquivo.selenium.WebDriverTestBaseParalell;

public class TestArcproxy extends WebDriverTestBaseParalell {
	public TestArcproxy(String os, String version, String browser, String deviceName, String deviceOrientation) {
		super(os, version, browser, deviceName, deviceOrientation);
	}

	@Test
	@Retry
	public void testArcproxy() {
		IndexPage index = new IndexPage(driver);
		Arcproxyinspection arcproxy = index.arcProxy(isPreProd);

		assertTrue("There are problems in the coherence of ArcProxy ", arcproxy.inspectArcproxy(false));
		assertTrue("The date of archived pages are not the same before perfomed ", arcproxy.inspectArcproxy(true));
	}
}
