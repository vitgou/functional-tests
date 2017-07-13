package pt.fccn.sobre.arquivo.pages;

import java.io.FileNotFoundException;
import java.nio.charset.Charset;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import pt.fccn.arquivo.tests.util.AnalyzeURLs;

public class PublicationsPage {
	
	
	WebDriver driver;
	private final int timeout = 50;
	
	public PublicationsPage( WebDriver driver ) throws FileNotFoundException{
		this.driver = driver;
		
	}
	
	
	public boolean checkPubicationsLinks( String language ) {
		System.out.println( "[checkPubicationsLinks]" );
		String idDiv = "";
		
		if( language.equals( "PT" ) )
			idDiv = "post-2225";
		else {
			switchLanguage( );
			idDiv = "post-2814";
		}
		
		String xpatha = "//*[@id=\"" + idDiv + "\"]/div/div/div/ul/li/a"; //get publications links	
		try{
    		List< WebElement > results = ( new WebDriverWait( driver, timeout ) )
	                .until( ExpectedConditions
	                        .visibilityOfAllElementsLocatedBy(
	                        		      By.xpath( xpatha )
	                        )
	        );
    		
    		System.out.println( "[Publications] results size = " + results.size( ) );
    		for( WebElement elem : results ) {
    			String url = elem.getAttribute( "href" );
    			int statusCode = AnalyzeURLs.linkExists( url );
    			String text = elem.getText( );
    			Charset.forName( "UTF-8" ).encode( text );
    			System.out.println( "Failed: text[" + text + "] link[" + url + "] status-code[" + statusCode + "]" );
    			/*if( !AnalyzeURLs.checkOk( statusCode ) ) {
    				System.out.println( "Failed: text[" + text + "] link[" + url + "] status-code[" + statusCode + "]" );
    				return false;
    			}*/
    		}
    		
	    	return true;
    	} catch( NoSuchElementException e ){
            System.out.println( "Error in checkOPSite" );
            e.printStackTrace( );
            return false;
    	}
		
	}
	
    /**
    * Change to the English version
    */
    private void switchLanguage( ){
    	String xpathEnglishVersion = "//*[@id=\"menu-item-3862-en\"]/a";
    	//TODO //*[@id=\"menu-item-3862-en\"]/a -> new template 
      	if( driver.findElement( By.xpath( xpathEnglishVersion ) ).getText( ).equals( "English" ) ) {
      		System.out.println( "Change language to English" );
      		driver.findElement( By.xpath( xpathEnglishVersion ) ).click( );
      		IndexSobrePage.sleepThread( );
      	}
    }
	
	
}
