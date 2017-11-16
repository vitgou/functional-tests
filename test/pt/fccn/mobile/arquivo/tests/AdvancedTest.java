package pt.fccn.mobile.arquivo.tests;

import static org.junit.Assert.*;

import org.junit.Test;

import pt.fccn.mobile.arquivo.pages.AdvancedSearchPage;
import pt.fccn.mobile.arquivo.pages.IndexMobilePage;
import pt.fccn.saw.selenium.WebDriverTestBaseParalell;

public class AdvancedTest extends WebDriverTestBaseParalell {

	
	public AdvancedTest( String os, String version, String browser, String deviceName, String deviceOrientation ) {
		super( os, version, browser, deviceName, deviceOrientation );
	}
	
	String term = "Pesquisa Avançada — Arquivo.pt";
    @Test
    public void SearchAdvancedTest( ) {
    
    }
    
}
