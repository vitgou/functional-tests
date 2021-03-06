package pt.fccn.sobre.arquivo.tests;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.IOException;

import org.junit.Test;

import pt.fccn.arquivo.selenium.Retry;
import pt.fccn.arquivo.selenium.WebDriverTestBaseParalell;
import pt.fccn.sobre.arquivo.pages.IndexSobrePage;

public class FooterTest extends WebDriverTestBaseParalell {

	public FooterTest(String os, String version, String browser, String deviceName, String deviceOrientation) {
		super(os, version, browser, deviceName, deviceOrientation);
	}

	@Test
	@Retry
	public void footerTest( ) {
		System.out.print( "Running examples Test. \n");
		IndexSobrePage index = null;
		try{
			 index = new IndexSobrePage( driver );
		} catch( IOException e ) {
			fail("IOException -> footerTest");
		}
		
        System.out.println( "Going to the FooterTest" );
        /**************************/
        /*** Portuguese version ***/
        /**************************/
        assertTrue("Failed The Footer Test in Portuguese", index.checkFooterURLs( "PT" ) );
        System.out.println( "Success The Footer Test in Portuguese" );
        
        /***********************/
        /*** English version ***/ 
        /***********************/
        assertTrue("Failed The Footer Test in English", index.checkFooterURLs( "EN" ) );
        System.out.println( "Success The Footer Test in English" );
	   
    }
	
	
	
}
