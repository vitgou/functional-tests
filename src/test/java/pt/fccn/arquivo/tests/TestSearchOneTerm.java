package pt.fccn.arquivo.tests;

import static org.junit.Assert.*;

import org.junit.Test;
import pt.fccn.arquivo.pages.*;
import pt.fccn.saw.selenium.WebDriverTestBaseParalell;
import pt.fccn.saw.selenium.Retry;

public class TestSearchOneTerm extends WebDriverTestBaseParalell {
    /**
     * Test the search of one term in the index interface.
     */
    public TestSearchOneTerm(String os, String version, String browser, String deviceName, String deviceOrientation) {
            super(os, version, browser, deviceName, deviceOrientation);
    }    
    @Test
    @Retry
    public void testSearchOneTerm() {
    	
        String term = "fccn";
        String testerSpellChecker="theste";
        IndexPage index = new IndexPage(driver);
        Ispre_prod=index.setPreProd(pre_prod);
        if (Ispre_prod){
        	System.out.print("\nTesting Pre-Prod Environment\n");
        }
        System.out.print("Running testSearchOneTerm. \n");
        SearchPage searchResults = index.search(term);
        titleOfFirstResult=searchResults.getFirstResult();
        System.out.println("testSearchOneTerm 1");
        assertTrue("The search term was not found in results", searchResults.titleIsCorrect(term));
        System.out.println("testSearchOneTerm 2");
        assertTrue("The search did not return results", searchResults.existsInResults(term));
        System.out.println("testSearchOneTerm 3");
        assertTrue("The spellchecker is not working",searchResults.spellcheckerOK(testerSpellChecker));
        System.out.println("testSearchOneTerm Finished");
        /*assertTrue("The replay bar is not working",searchResults.testReplayBar()); Commented this test. Only Works For Desktops, should be in a different test*/
    }
}
