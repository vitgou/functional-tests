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

public class NewsOnMediaPage {

	WebDriver driver;
	private final int timeout = 50;
	
	public NewsOnMediaPage( WebDriver driver ) throws FileNotFoundException{
		this.driver = driver;
		
	}
	
	public boolean checkNewsLinks( String language ) {
		System.out.println( "[checkNewsLinks]" );
		String xpatha = "";
		if( language.equals( "EN" ) )
			xpatha = "//*[@id=\"post-2812\"]/div/div/ul/li/a"; //get links
		else
			xpatha = "//*[@id=\"post-2635\"]/div/div/ul/li/a"; //get links
		
		try{
    		List< WebElement > results = ( new WebDriverWait( driver, timeout ) )
	                .until( ExpectedConditions
	                        .visibilityOfAllElementsLocatedBy(
	                        		      By.xpath( xpatha )
	                        )
	        );
    		
    		System.out.println( "[News Links] results size = " + results.size( ) );
    		for( WebElement elem : results ) {
    			String url = elem.getAttribute( "href" );
    			int statusCode = AnalyzeURLs.linkExists( url );
    			if( !AnalyzeURLs.checkOk( statusCode ) ) {
    				System.out.println( "Failed: text[" + Charset.forName( "UTF-8" ).encode( elem.getText( ) ) + "] link[" + url + "] status-code[" + statusCode + "]" );
    				return false;
    			}
    		}
    		
	    	return true;
    	} catch( NoSuchElementException e ){
            System.out.println( "Error in checkNewsLinks" );
            e.printStackTrace( );
            return false;
    	}
		
	}
	
}
