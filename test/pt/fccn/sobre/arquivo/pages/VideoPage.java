package pt.fccn.sobre.arquivo.pages;

import java.io.FileNotFoundException;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import pt.fccn.arquivo.tests.util.AnalyzeURLs;

public class VideoPage {
	
	WebDriver driver;
	private final int timeout = 50;
	
	public VideoPage( WebDriver driver ) throws FileNotFoundException{
		this.driver = driver;
		
	}
	
	public boolean checkVideoLinks( String language ) {
		System.out.println( "[checkVideoLinks]" );
		String xpathVideos 			= "//*[@id=\"post-2816\"]/div/div/div/ul/li/a";
		String xpathDissemination 	= "//*[@id=\"post-2816\"]/div/div/ul[1]/li/a";
    	String xpathTechnical 		= "//*[@id=\"post-2816\"]/div/div/ul[2]/li/a";
    	
		if( !extractLinks( xpathVideos ) )
			return false;
		if( !extractLinks( xpathDissemination ) )
			return false;
		if( !extractLinks( xpathTechnical ) )
			return false;
		
		return true;
	}
	
	
	public boolean extractLinks( String xpath ) {
		try{
    		List< WebElement > results = ( new WebDriverWait( driver, timeout ) )
	                .until( ExpectedConditions
	                        .visibilityOfAllElementsLocatedBy(
	                        		      By.xpath( xpath )
	                        )
	        );
    		
    		System.out.println( "[Video Links] results size = " + results.size( ) );
    		for( WebElement elem : results ) {
    			String url = elem.getAttribute( "href" );
    			int statusCode = AnalyzeURLs.linkExists( url );
    			if( !AnalyzeURLs.checkOk( statusCode ) ) {
    				System.out.println( "Failed: Url[" + url + "] status-code[" + statusCode + "]" );
    				return false;
    			}
    		}
    		
	    	return true;
    	} catch( NoSuchElementException e ){
            System.out.println( "Error in checkVideoLinks" );
            e.printStackTrace( );
            return false;
    	}
	}
	
}
