package pt.fccn.arquivo.tests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import pt.fccn.arquivo.pages.IndexPage;
import pt.fccn.arquivo.pages.OpenSearchPage;
import pt.fccn.arquivo.pages.SearchPage;
import pt.fccn.saw.selenium.WebDriverTestBaseParalell;
import pt.fccn.saw.selenium.Retry;


public class TestSearchOneTermOpenSearch extends WebDriverTestBaseParalell{
    public TestSearchOneTermOpenSearch(String os, String version, String browser, String deviceName, String deviceOrientation) {
            super(os, version, browser, deviceName, deviceOrientation);
    }     	
	@Test
	@Retry
	 public void testSearchOneTermOpenSearch() {
	 System.out.print("Running TestSearchOneTermOpenSearch. \n");
	 String term = "fccn";
	 
     IndexPage index = new IndexPage(driver);
     Ispre_prod=index.setPreProd(pre_prod);
     OpenSearchPage searchResults = index.opensearch(term,Ispre_prod);
     
	 System.out.println("Getting title of first result");
	 titleOfFirstResult = searchResults.setFirstResult(term);
	 System.out.print("\n\nFirstResult: "+ titleOfFirstResult);
	
     try {
		assertTrue("The search did not return results",searchResults.existsResults());
		
		assertTrue("The search result from opensearch and search are not coherent",searchResults.inspectCoherence(titleOfFirstResult));
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	 }
}


