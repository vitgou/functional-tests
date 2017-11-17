package pt.fccn.mobile.arquivo.tests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import pt.fccn.mobile.arquivo.pages.IndexMobilePage;
import pt.fccn.saw.selenium.WebDriverTestBaseParalell;

public class URLSearchTest extends WebDriverTestBaseParalell {

	public URLSearchTest(String os, String version, String browser, String deviceName, String deviceOrientation) {
		super(os, version, browser, deviceName, deviceOrientation);
	}
	
	@Test
	//@Retry
	public void searchingTest( )  {
		System.out.print( "[Mobile] Running URL SearchTest Test. \n");
		IndexMobilePage index = null;
		index = new IndexMobilePage( driver );
		Ispre_prod = index.setPreProd( pre_prod );

		/**************************/
		/*** Portuguese version ***/ 
		/**************************/
        assertTrue("Failed The Search Test in Portuguese", index.checkURLSearch( "PT" ) );
        System.out.println( "Success The Search Test in Portuguese" );
        
        /***********************/
        /*** English version ***/ 
        /***********************/
        assertTrue("Failed The Search Test in English", index.checkURLSearch( "EN" ) );
        System.out.println( "Success The Search Test in English" );
	}
	
}
