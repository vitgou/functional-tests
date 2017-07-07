package pt.fccn.sobre.arquivo.tests;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.IOException;

import org.junit.Test;

import pt.fccn.saw.selenium.Retry;
import pt.fccn.saw.selenium.WebDriverTestBaseParalell;
import pt.fccn.sobre.arquivo.pages.IndexSobrePage;
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
			index = new IndexSobrePage( driver );
			SiteMapPage siteMap = index.goToSiteMapPage( );
			System.out.println( "Going to the FooterTest" );
		    
	        assertTrue("Failed The Example Page Test in Portuguese", siteMap.checkSiteMap( "PT" ) );

	        //*[@id="resInfo-0"]
	        
	        
	        //TODO english version missing
	        
		} catch( IOException e ) {
			fail("IOException -> IndexSobrePage");
		}
		
    }

}
