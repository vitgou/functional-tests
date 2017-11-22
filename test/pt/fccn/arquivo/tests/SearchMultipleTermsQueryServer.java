package pt.fccn.arquivo.tests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import pt.fccn.arquivo.pages.IndexPage;
import pt.fccn.saw.selenium.Retry;
import pt.fccn.saw.selenium.WebDriverTestBaseParalell;

public class SearchMultipleTermsQueryServer  extends WebDriverTestBaseParalell{

	public SearchMultipleTermsQueryServer(String os, String version, String browser, String deviceName,
			String deviceOrientation) {
		super(os, version, browser, deviceName, deviceOrientation);
	}
	
    @Test
    @Retry
    public void searchMultipleTermsQueryServer( ) {
    	System.out.println( "Running SearchMultipleTermsQueryServer. \n" );
        IndexPage index = new IndexPage( driver );
        Ispre_prod = index.setPreProd( pre_prod );
        
        assertTrue("Failed The Search Multiple Terms Test in Portuguese", index.searchMultipleTerms( "PT" ) );
        System.out.println( "Success The Search Multiple Terms Test in Portuguese" );
        index.goToIndex( ); 
        assertTrue("Failed The Search Multiple Terms Test in English", index.searchMultipleTerms( "EN" ) );
        System.out.println( "Success The Search Multiple Terms Test in English" );
    }
	

}
