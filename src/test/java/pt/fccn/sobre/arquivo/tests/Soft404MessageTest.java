package pt.fccn.sobre.arquivo.tests;

import static org.junit.Assert.assertEquals;

import java.util.List;
import java.util.Locale;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import pt.fccn.arquivo.selenium.Retry;
import pt.fccn.arquivo.selenium.WebDriverTestBaseParalell;
import pt.fccn.mobile.arquivo.utils.LocaleUtils;
import pt.fccn.mobile.arquivo.utils.LocalizedString;
import pt.fccn.sobre.arquivo.pages.IndexSobrePage;


/**
 * 
 * @author pedro.gomes.fccn@gmail.com
 *
 */

public class Soft404MessageTest extends WebDriverTestBaseParalell {

	private static final String WAYBACK_404_PAGE_EXAMPLE = "/about-the-archive/publications-1/documents/peopleware-rarc-article-in-jpg/image_preview";
	
	public Soft404MessageTest(String os, String version, String browser, String deviceName, String deviceOrientation) {
		super(os, version, browser, deviceName, deviceOrientation);
		
	}
	
	@Test
	@Retry
	public void soft404MessageTestPT()  {
		soft404MessageTest(LocaleUtils.PORTUGUESE);
	}
	
	@Test
	@Retry
	public void soft404MessageTestEN() {
		soft404MessageTest(LocaleUtils.ENGLISH);
		switchLanguage( );
	}
	

	public void soft404MessageTest(Locale locale) {
		
		driver.get(this.testURL + WAYBACK_404_PAGE_EXAMPLE);
		
		waitUntilElementIsVisibleAndGet(By.id("post-9818"));
		
		LocalizedString listText = new LocalizedString().pt("Visite uma versão anterior desta página em 2017-02-27 no Arquivo.pt").en("Browse a previous version of this page in 2017-02-28 on Arquivo.pt");
		
		appendError(() -> assertEquals("Verify text from Arquivo.pt link", listText.apply(locale),
				driver.findElement(By.id("onArquivo")).getText()));
		
		List<WebElement> wes = driver.findElementsByXPath("//*[@id=\"onArquivo\"]");
		WebElement we = wes.get(0);
		String href = we.getAttribute("href");
		assertEquals("Check link to wayback link", href, "https://arquivo.pt/wayback/20170227184149/https://sobre.arquivo.pt" + WAYBACK_404_PAGE_EXAMPLE);
	}
	
	private void switchLanguage( ){
    	String xpathEnglishVersion = "//*[@id=\"menu-item-4424-en\"]/a";
      	if( driver.findElement( By.xpath( xpathEnglishVersion ) ).getText( ).equals( "English" ) ) {
      		System.out.println( "Change language to English" );
      		driver.findElement( By.xpath( xpathEnglishVersion ) ).click( );
      		IndexSobrePage.sleepThread( );
      	}
    }

}
