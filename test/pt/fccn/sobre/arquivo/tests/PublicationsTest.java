package pt.fccn.sobre.arquivo.tests;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.IOException;

import org.junit.Test;

import pt.fccn.saw.selenium.Retry;
import pt.fccn.saw.selenium.WebDriverTestBaseParalell;
import pt.fccn.sobre.arquivo.pages.IndexSobrePage;
import pt.fccn.sobre.arquivo.pages.PublicationsPage;
import pt.fccn.sobre.arquivo.pages.SiteMapPage;

public class PublicationsTest extends WebDriverTestBaseParalell {

	public PublicationsTest(String os, String version, String browser, String deviceName, String deviceOrientation) {
		super( os, version, browser, deviceName, deviceOrientation );
	}

	@Test
	@Retry
	public void footerTest( )  {
		System.out.print( "Running examples Test. \n");
		IndexSobrePage index = null;
		try{
			index = new IndexSobrePage( driver );
			PublicationsPage publications = index.goToPublicationsPage( );
			System.out.println( "Going to the FooterTest" );
		    
	        assertTrue("Failed The Publications Page Test in Portuguese", publications.checkPubicationsLinks( "PT" ) );
	        
	        //TODO english version missing
	        
		} catch( IOException e ) {
			fail("IOException -> footerTest");
		}
		
    }
}
