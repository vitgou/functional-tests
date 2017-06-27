package pt.fccn.sobre.arquivo.pages;

import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ExamplesPage {
	
	WebDriver driver;
	private final int timeout = 50;
	
	public ExamplesPage( WebDriver driver ) {
		this.driver = driver;
		
	}

	/**
	 * Test links Examples
	 * @return
	 */
	public boolean checkLinksExamples( String language ){
		System.out.println( "[checkLinksExamples]" );
    	String xpatha = "//*[@id=\"post-1861\"]/div/div/div/div/div/p[2]/a"; //get link examples
    	try{
    		List< WebElement > results = ( new WebDriverWait( driver, timeout ) )
	                .until( ExpectedConditions
	                        .visibilityOfAllElementsLocatedBy(
	                        		      By.xpath( xpatha )
	                        )
	        );
    		
    		System.out.println( "results size = " + results.size( ) );
    		for( WebElement elem : results ) {
    			String url = elem.getAttribute( "href" );
    			System.out.println( "Test url = " + url );
    			linkExists( url );
    			/*if( !linkExists( url ) )
    				return false;*/
    		}
    		
	    	return true;
    	} catch( Exception e ){
            System.out.println("Error in checkOPSite");
            e.printStackTrace();
            return false;
    	}
	}
	
	/**
	 * Check if link exists
	 * @param URLName
	 * @return
	 */
	private boolean linkExists( String URLName ){
	    try {
	      HttpURLConnection.setFollowRedirects( false );
	      HttpURLConnection con = ( HttpURLConnection ) new URL( URLName ).openConnection( );
	      con.setConnectTimeout( timeout );
	      con.setRequestMethod( "HEAD" );
	      return ( con.getResponseCode( ) == HttpURLConnection.HTTP_OK );
	    } catch ( Exception e ) {
	       e.printStackTrace( );
	       return false;
	    }
	}
	
	
}
