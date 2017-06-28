package pt.fccn.sobre.arquivo.pages;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.net.ssl.HttpsURLConnection;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


public class IndexSobrePage {
	WebDriver driver;
	
	private final int timeout = 50;
	Map< String, String > textTolink;
	private final String dir = "sobreTestsFiles";
	
	
	/**
	 * 
	 * @param driver
	 * @throws IOException
	 */
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
    	
    	readFromFile( "FooterLinks.txt" );
    	
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
    			String text = elem.getText( );
    			Charset.forName( "UTF-8" ).encode( text );
    		
    			if( url.startsWith( "https://" ) )
    				System.out.println( "Return = " + linkExistsSSL( url , text ) );
    			else
    				System.out.println( "Return = " + linkExists( url , text ) );
    			/*if( !linkExists( url , text ) )
    				return false;*/
    		}
    		
	    	return true;
    	} catch( NoSuchElementException e ){
            System.out.println( "Error in checkOPSite" );
            e.printStackTrace( );
            return false;
    	}
    	
    
    }
    
    
	/**
	 * Check if link exists
	 * @param URLName
	 * @return
	 */
	private boolean linkExists( String URLName , String text  ) {
	    boolean redirect = false;
		try {
	    	System.out.println( "[Footer] url[" + URLName + "]" );
	    	
	    	HttpURLConnection con = ( HttpURLConnection ) new URL( URLName ).openConnection( );
	    	con.setConnectTimeout( 5000 );
	    	con.setRequestMethod( "HEAD" );
	    	con.addRequestProperty( "Accept-Language", "en-US,en;q=0.8" );
	    	con.addRequestProperty( "User-Agent", "Mozilla" );
	    	con.addRequestProperty( "Referer", "google.com" );
	    	
	    	// normally, 3xx is redirect
	    	int status = con.getResponseCode( );
	    	System.out.println( "Status-code = " + status );
	    	if (status != HttpURLConnection.HTTP_OK) {
	    		if (status == HttpURLConnection.HTTP_MOVED_TEMP
	    			|| status == HttpURLConnection.HTTP_MOVED_PERM
	    				|| status == HttpURLConnection.HTTP_SEE_OTHER)
	    		redirect = true;
	    	}
	    	
	    	redirect = checkRedirect( status );
	    	
	    	System.out.println( "[Footer] url[" + URLName + "] Status-code = " + con.getResponseCode( ) );
	    	
	    	while( redirect ) {
	    		// get redirect url from "location" header field
	    		String newUrl = con.getHeaderField( "Location" );
	    		System.out.println( "Redirect: true url["+URLName+"] newurl["+newUrl+"]" );
	    		// get the cookie if need, for login
	    		String cookies = con.getHeaderField( "Set-Cookie" );
	    		
	    		// open the new connection again
				con = ( HttpURLConnection ) new URL( newUrl ).openConnection( );
				con.setConnectTimeout( 5000 );
				con.setRequestMethod( "HEAD" );
	    		con.setRequestProperty( "Cookie", cookies );
	    		con.addRequestProperty( "Accept-Language", "en-US,en;q=0.8" );
	    		con.addRequestProperty( "User-Agent", "Mozilla" );
	    		con.addRequestProperty( "Referer", "google.com" );
	    		status = con.getResponseCode( );

	    		URLName = newUrl;
	    		System.out.println( "Novo redirect status = " + status + " message = " + con.getResponseMessage( ) );
	    		redirect = checkRedirect( status );
	    	}
	    	
	    	
	    	System.out.println( "Compare textTolink.get( "+text+" ) = " + textTolink.get( text ) + " URLName = " + URLName + " Status-code = " + status );
	    	
	    	if( status == HttpURLConnection.HTTP_OK &&  textTolink.get( text ).equals( URLName ) )
	    		return true;
	    	else
	    		return false;
	
	    } catch ( MalformedURLException e ) {
	    	System.out.println( "MalformedURLException e = " + e.getMessage( ) );
			return false;
		} catch ( IOException e ) {
			System.out.println( "IOException e = " + e.getMessage( ) );
			return false;
		}
	    
	}
	
	/**
	 * Check if link exists
	 * @param URLName
	 * @return
	 */
	private boolean linkExistsSSL( String URLName , String text  ) {
	    boolean redirect = false;
		try {
	    	System.out.println( "[Footer SSL] url[" + URLName + "]" );
	    	
	    	HttpsURLConnection con = ( HttpsURLConnection ) new URL( URLName ).openConnection( );
	    	con.setConnectTimeout( 5000 );
	    	con.setRequestMethod( "HEAD" );
	    	con.addRequestProperty( "Accept-Language", "en-US,en;q=0.8" );
	    	con.addRequestProperty( "User-Agent", "Mozilla" );
	    	con.addRequestProperty( "Referer", "google.com" );
	    	
	    	System.out.println("Debug 1");
	    	
	    	int status = con.getResponseCode( );
	    	System.out.println( "Status-code = " + status );
	    	if (status != HttpsURLConnection.HTTP_OK) {
	    		if (status == HttpsURLConnection.HTTP_MOVED_TEMP
	    			|| status == HttpsURLConnection.HTTP_MOVED_PERM
	    				|| status == HttpsURLConnection.HTTP_SEE_OTHER)
	    		redirect = true;
	    	}
	    	
	    	redirect = checkRedirect( status );
	    	
	    	System.out.println( "[Footer] url[" + URLName + "] Status-code = " + con.getResponseCode( ) );
	    	
	    	while( redirect ) {
	    		// get redirect url from "location" header field
	    		String newUrl = con.getHeaderField( "Location" );
	    		System.out.println( "Redirect: true url["+URLName+"] newurl["+newUrl+"]" );
	    		// get the cookie if need, for login
	    		String cookies = con.getHeaderField( "Set-Cookie" );
	    		
	    		// open the new connection again
				con = ( HttpsURLConnection ) new URL( newUrl ).openConnection( );
				con.setConnectTimeout( 5000 );
				con.setRequestMethod( "HEAD" );
	    		con.setRequestProperty( "Cookie", cookies );
	    		con.addRequestProperty( "Accept-Language", "en-US,en;q=0.8" );
	    		con.addRequestProperty( "User-Agent", "Mozilla" );
	    		con.addRequestProperty( "Referer", "google.com" );
	    		status = con.getResponseCode( );

	    		URLName = newUrl;
	    		System.out.println( "Novo redirect status = " + status + " message = " + con.getResponseMessage( ) );
	    		redirect = checkRedirect( status );
	    	}
	    	System.out.println( "Compare textTolink.get( "+text+" ) = " + textTolink.get( text ) + " URLName = " + URLName + " Status-code = " + status );
	    	
	    	if( status == HttpsURLConnection.HTTP_OK &&  textTolink.get( text ).equals( URLName ) )
	    		return true;
	    	else
	    		return false;
	
	    } catch ( MalformedURLException e ) {
	    	System.out.println( "MalformedURLException e = " + e.getMessage( ) );
			return false;
		} catch ( IOException e ) {
			System.out.println( "IOException e = " + e.getMessage( ) );
			return false;
		}
	    
	}

	
	private boolean readFromFile( String filename ) {
		textTolink = new HashMap< String , String >( );
		try {
			BufferedReader in = new BufferedReader( 
											new InputStreamReader( 
													new FileInputStream (dir.concat( File.separator ).concat( filename ) ) , "UTF8" ) );
			String line = "";
			while( ( line = in.readLine( ) ) != null ) {
				String parts[ ] = line.split( "," );
				textTolink.put( parts[ 0 ], parts[ 1 ] );
			}
			in.close( );
			System.out.println( "Map => " + textTolink.toString( ) );
			return true;
		} catch ( FileNotFoundException e ) {
			e.printStackTrace( );
			return false;
		} catch (IOException e) {
			e.printStackTrace( );
			return false;
		}
	}
    
	
	private boolean checkRedirect( int status ){
		if (status != HttpURLConnection.HTTP_OK) {
    		if (status == HttpURLConnection.HTTP_MOVED_TEMP
    			|| status == HttpURLConnection.HTTP_MOVED_PERM
    				|| status == HttpURLConnection.HTTP_SEE_OTHER)
    		return true;
    	}
		return false;
	}
	
}

