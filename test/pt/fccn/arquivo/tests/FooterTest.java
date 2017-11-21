package pt.fccn.arquivo.tests;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;

import pt.fccn.arquivo.pages.AdvancedPage;
import pt.fccn.arquivo.pages.IndexPage;
import pt.fccn.saw.selenium.Retry;
import pt.fccn.saw.selenium.WebDriverTestBaseParalell;

/**
 * 
 * @author jnobre
 *
 */
public class FooterTest extends WebDriverTestBaseParalell{

	public FooterTest(String os, String version, String browser, String deviceName, String deviceOrientation) {
		super(os, version, browser, deviceName, deviceOrientation);
	}

    /**
     * Tests Advanced search page
     */
    @Test
    @Retry
    public void footerTest( ) {
    	System.out.print( "Running AdvancedTest. \n" );
        IndexPage index = new IndexPage( driver );
        Ispre_prod = index.setPreProd( pre_prod );
        
        assertTrue("Failed The Footer Links Test in Portuguese", index.checkFooterLinks( "PT" ) );
        System.out.println( "Success The Footer Links Test in Portuguese" );
        
        assertTrue("Failed The Footer Links Test in English", index.checkFooterLinks( "EN" ) );
        System.out.println( "Success The Footer Links Test in English" ); 
    }
	
}
