package pt.fccn.sobre.arquivo.pages;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


public class IndexSobrePage {
	WebDriver driver;
	
	private final int timeout = 50;
	
    public IndexSobrePage( WebDriver driver ) throws IOException {
        this.driver = driver;
        
        
        Properties prop = new Properties( );
    	InputStream input = null;
    	try {
			input = new FileInputStream( "sobreTestsFiles/props_indexPage.properties" );
			prop.load( new InputStreamReader( input, Charset.forName("UTF-8") ) ); //load a properties file
    	} catch ( FileNotFoundException e ) {
    		throw e;
    	} catch ( IOException e1) {
    		if( input != null) input.close( );
    		throw e1;
		} 
    	
    	try {
          Thread.sleep( 5000 );                 //wait for page to load
        } catch( InterruptedException ex ) {
          Thread.currentThread( ).interrupt( );
        }
    	
        // Check that we're on the right page.
        String pageTitle= driver.getTitle( );
        Charset.forName( "UTF-8" ).encode( pageTitle );
        System.out.println( "Page title = " + pageTitle + " == " + prop.getProperty( "title_pt" ) );
        if ( !( pageTitle.contentEquals( prop.getProperty( "title_pt" ) ) || (pageTitle.contentEquals("title_en") ) ) ){
            throw new IllegalStateException("This is not the index page\n Title of current page: " + pageTitle);
        }
    }
    
    /**
     * Click the link to the footer of the page
     * @throws FileNotFoundException 
     */
    public CommonQuestionsPage goToCommonQuestionsPage( ) throws FileNotFoundException{
        try{
            System.out.println( "Start goToCommonQuestionsPage() method" );
            WebElement cQuestionsLink = ( new WebDriverWait( driver, timeout ) ) /* Wait Up to 50 seconds should throw RunTimeExcpetion*/
            .until(
            		ExpectedConditions.presenceOfElementLocated(
            				By.xpath( "//*[@id=\"wp_editor_widget-8\"]/ul/li[1]/a" )
            				)
            		);            
            cQuestionsLink.click( );
            System.out.println( "Finished goToCommonQuestionsPage() method" );
        }catch( NoSuchElementException e ){
        	System.out.println( "Could not find the link element" );
        	throw e;
        } 
        
        return new CommonQuestionsPage( driver );
    }
    
    /**
     * 
     * @return
     */
    public ExamplesPage goToExamplePage( ) {
    	
        try{
            System.out.println( "Start goToExamplePage() method" );
            WebElement examplePageLink = ( new WebDriverWait( driver, timeout ) ) /* Wait Up to 50 seconds should throw RunTimeExcpetion*/
            .until(
            		ExpectedConditions.presenceOfElementLocated(
            				By.xpath( "//*[@id=\"menu-item-1863\"]/a" )
            				)
            		);            
            examplePageLink.click( );
            System.out.println( "Finished goToExamplePage() method" );
        }catch( NoSuchElementException e ){
        	System.out.println( "Could not find the link element" );
        	throw e;
        } 
        
        return new ExamplesPage( driver );
    
    }
    
    public boolean checkFooterLinks( String language ) {
		System.out.println( "[checkFooterLinks]" );
    	String xpatha = "//*[@id=\"footer-widgets\"]/div/div/div/aside/ul/li/a"; //get footer links
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
    			if( !linkExists( url ) )
    				return false;
    		}
    		
	    	return true;
    	} catch( Exception e ){
            System.out.println( "Error in checkOPSite" );
            e.printStackTrace( );
            return false;
    	}
    	
    
    }
    
    
	/**
	 * Check if link exists (Accepted 200 or 301 or 302)
	 * @param URLName
	 * @return
	 */
	private boolean linkExists( String URLName ){
	    boolean redirect = false;
		try {
	    	System.out.println( "[Footer] url[" + URLName + "]" );
	    	HttpURLConnection.setFollowRedirects( false );
	    	HttpURLConnection con = ( HttpURLConnection ) new URL( URLName ).openConnection( );
	    	con.setConnectTimeout( timeout );
	    	con.setRequestMethod( "HEAD" );
	    	con.addRequestProperty( "Accept-Language", "en-US,en;q=0.8" );
	    	con.addRequestProperty( "User-Agent", "Mozilla" );
	    	con.addRequestProperty( "Referer", "google.com" );

	    	
	    	// normally, 3xx is redirect
	    	int status = con.getResponseCode();
	    	if (status != HttpURLConnection.HTTP_OK) {
	    		if (status == HttpURLConnection.HTTP_MOVED_TEMP
	    			|| status == HttpURLConnection.HTTP_MOVED_PERM
	    				|| status == HttpURLConnection.HTTP_SEE_OTHER)
	    		redirect = true;
	    	}
	    	
	    	System.out.println( "[Footer] url[" + URLName + "] Status-code = " + con.getResponseCode( ) );
	    	
	    	if( redirect ) {
	    		// get redirect url from "location" header field
	    		String newUrl = con.getHeaderField( "Location" );
	    		
	    		// get the cookie if need, for login
	    		String cookies = con.getHeaderField( "Set-Cookie" );
	    		
	    		// open the new connection again
	    		con = ( HttpURLConnection ) new URL( newUrl ).openConnection( );
	    		con.setConnectTimeout( timeout );
	    		con.setRequestProperty( "Cookie", cookies );
	    		con.addRequestProperty( "Accept-Language", "en-US,en;q=0.8" );
	    		con.addRequestProperty( "User-Agent", "Mozilla" );
	    		con.addRequestProperty( "Referer", "google.com" );
	    		status = con.getResponseCode( );
	    	}
	    	
	    	return ( status == HttpURLConnection.HTTP_OK );
	    	
	    } catch ( Exception e ) {
	    	e.printStackTrace( );
	    	return false;
	    }
	}

    
}

