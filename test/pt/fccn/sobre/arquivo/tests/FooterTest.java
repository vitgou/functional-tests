package pt.fccn.sobre.arquivo.tests;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.IOException;

import org.junit.Test;

import pt.fccn.saw.selenium.Retry;
import pt.fccn.saw.selenium.WebDriverTestBaseParalell;
import pt.fccn.sobre.arquivo.pages.ExamplesPage;
import pt.fccn.sobre.arquivo.pages.IndexSobrePage;

public class FooterTest extends WebDriverTestBaseParalell {

	public FooterTest(String os, String version, String browser, String deviceName, String deviceOrientation) {
		super(os, version, browser, deviceName, deviceOrientation);
	}

	@Test
	@Retry
	public void footerTest( )  {
		System.out.print( "Running examples Test. \n");
		IndexSobrePage index = null;
		try{
			 index = new IndexSobrePage( driver );
		} catch( IOException e ) {
			fail("IOException -> IndexSobrePage");
		}
		
        System.out.println( "Going to the FooterTest" );
        
        assertTrue("Failed The Example Page Test in Portuguese", index.checkFooterLinks( "PT" ) );
        
        //TODO english version missing
	   
    }
	
	
	
}
