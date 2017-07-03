package pt.fccn.sobre.arquivo.pages;

import java.io.FileNotFoundException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import pt.fccn.arquivo.tests.util.AnalyzeURLs;

public class NewsPage {

	
	WebDriver driver;
	private final int timeout = 50;
	
	public NewsPage( WebDriver driver ) throws FileNotFoundException{
		this.driver = driver;
		
	
	}
	
	
	/**
	 * 
	 * @param language
	 * @return
	 */
	public boolean checkNewsLinks( String language ) {
		System.out.println( "[checkLinks]" );
		String xpatha = "//*[@id=\"post-1857\"]/div/div/aside/div/ul/li/a"; //get news links
    	
		try{
    		List< WebElement > results = ( new WebDriverWait( driver, timeout ) )
	                .until( ExpectedConditions
	                        .visibilityOfAllElementsLocatedBy(
	                        		      By.xpath( xpatha )
	                        )
	        );
    		
    		System.out.println( "[footer] results size = " + results.size( ) );
    		for( WebElement elem : results ) {
    			String url = elem.getAttribute( "href" );
    			int statusCode = AnalyzeURLs.linkExists( url );
    			if( !AnalyzeURLs.checkOk( statusCode ) ) {
    				System.out.println( "Failed: Url[" + url + "] status-code[" + statusCode + "]" );
    				return false;
    			}
    		}
    		
    		String xpathRss = "//*[@id=\"post-1857\"]/div/div/h4/a";
    		
    	    WebElement rssLink = ( new WebDriverWait( driver, timeout ) ) /* Wait Up to 50 seconds should throw RunTimeExcpetion*/
	             .until( ExpectedConditions.
	            		 presenceOfElementLocated(
	             				By.xpath( xpathRss )
	             				)
	             		); 
    	    
    	    String urlRss = rssLink.getAttribute( "href" );
    	    int statusCode = AnalyzeURLs.linkExists( urlRss );
    	    if( !AnalyzeURLs.checkOk( statusCode ) &&  urlRss.equals( "http://sobre.arquivo.pt/pt/feed/" )){
    	    	System.out.println( "Failed: Url[" + urlRss + "] status-code[" + statusCode + "]" );
    	    	return false;
    	    }
    	    
	    	return true;
    	} catch( NoSuchElementException e ){
            System.out.println( "Error in checkOPSite" );
            e.printStackTrace( );
            return false;
    	}
		
	}
	
}
