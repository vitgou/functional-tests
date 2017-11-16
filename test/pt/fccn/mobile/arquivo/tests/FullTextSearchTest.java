package pt.fccn.mobile.arquivo.tests;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.IOException;

import org.junit.Test;

import pt.fccn.mobile.arquivo.pages.IndexPage;
import pt.fccn.saw.selenium.Retry;
import pt.fccn.saw.selenium.WebDriverTestBaseParalell;

public class FullTextSearchTest extends WebDriverTestBaseParalell {
		
	public FullTextSearchTest(String os, String version, String browser, String deviceName, String deviceOrientation) {
		super( os, version, browser, deviceName, deviceOrientation );
	}
	
	@Test
	@Retry
	public void searchingTest( )  {
		System.out.print( "[Mobile] Running SearchTest Test. \n");
		IndexPage index = null;
		/**************************/
		/*** Portuguese version ***/ 
		/**************************/
        assertTrue("Failed The Search Test in Portuguese", index.checkSearch( "PT" ) );
        System.out.println( "Success The Search Test in Portuguese" );
        
        /***********************/
        /*** English version ***/ 
        /***********************/
        //assertTrue("Failed The Search Test in English", search.checkSearch( "EN" ) );
        //System.out.println( "Success The Search Test in English" );
 
		
    }
	
}
