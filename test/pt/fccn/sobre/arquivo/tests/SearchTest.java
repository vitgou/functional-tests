package pt.fccn.sobre.arquivo.tests;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.IOException;

import org.junit.Test;

import pt.fccn.saw.selenium.Retry;
import pt.fccn.saw.selenium.WebDriverTestBaseParalell;
import pt.fccn.sobre.arquivo.pages.IndexSobrePage;
import pt.fccn.sobre.arquivo.pages.SearchPage;
import pt.fccn.sobre.arquivo.pages.SiteMapPage;

public class SearchTest extends WebDriverTestBaseParalell {

	public SearchTest(String os, String version, String browser, String deviceName, String deviceOrientation) {
		super( os, version, browser, deviceName, deviceOrientation );
	}
	
	@Test
	@Retry
	public void searchingTest( )  {
		System.out.print( "Running SearchTest Test. \n");
		IndexSobrePage index = null;
		try{
			SearchPage search = new SearchPage( driver );
			System.out.println( "Going to the SearchingTest" );
		    
	        assertTrue("Failed The Search Test in Portuguese", search.checkSearch( "PT" ) );
	        //assertTrue("Failed The Search Test in Portuguese", search.checkSearch( "EN" ) );
	        
		} catch( IOException e ) {
			fail("IOException -> SearchingTest");
		}
		
    }

}
