package pt.fccn.mobile.arquivo.tests;

import static org.junit.Assert.assertTrue;
import org.junit.Test;

import pt.fccn.arquivo.selenium.Retry;
import pt.fccn.arquivo.selenium.WebDriverTestBaseParalell;
import pt.fccn.mobile.arquivo.pages.IndexMobilePage;

public class FullTextSearchTest extends WebDriverTestBaseParalell {

	public FullTextSearchTest(String os, String version, String browser, String deviceName, String deviceOrientation) {
		super( os, version, browser, deviceName, deviceOrientation );
	}

	@Test
	@Retry
	public void fullTextSearchTest( )  {
		System.out.print( "[Mobile] Running SearchTest Test. \n");
		IndexMobilePage index = null;
		index = new IndexMobilePage( driver );

		/**************************/
		/*** Portuguese version ***/
		/**************************/
        assertTrue("Failed The Search Test in Portuguese", index.checkFullTextSearch( "PT" ) );
        System.out.println( "Success The Search Test in Portuguese" );

        /***********************/
        /*** English version ***/
        /***********************/
        assertTrue("Failed The Search Test in English", index.checkFullTextSearch( "EN" ) );
        System.out.println( "Success The Search Test in English" );
	}

}
