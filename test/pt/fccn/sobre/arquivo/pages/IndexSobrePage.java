package pt.fccn.sobre.arquivo.pages;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
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
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import pt.fccn.arquivo.tests.util.AnalyzeURLs;




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
     * @throws FileNotFoundException 
     */
	public SiteMapPage goToSiteMapPage() throws FileNotFoundException {
		
        try{
            System.out.println( "Start goToSiteMapPage() method" );
            
            Actions actions = new Actions( driver ); 
            WebElement menuHoverLink = ( new WebDriverWait( driver, timeout ) ) /* Wait Up to 50 seconds should throw RunTimeExcpetion*/
                    .until(
                    		ExpectedConditions.presenceOfElementLocated(
                    				By.xpath( "//*[@id=\"menu-item-2068\"]/a" )
                    				)
                    		);            
            actions.moveToElement( menuHoverLink ).perform( ); //click in menu ajuda
            WebElement menuClickLink = ( new WebDriverWait( driver, timeout ) ) /* Wait Up to 50 seconds should throw RunTimeExcpetion*/
                    .until(
                    		ExpectedConditions.presenceOfElementLocated(
                    				By.xpath( "//*[@id=\"menu-item-3864\"]/a" )
                    				)
                    		);
            menuClickLink.click( );
            System.out.println( "Finished goToSiteMapPage() method" );
        }catch( NoSuchElementException e ){
        	System.out.println( "Could not find the link element" );
        	throw e;
        } 
        
        return new SiteMapPage( driver );
	}
	
	
    /**
     * 
     * @return
     * @throws FileNotFoundException 
     */
	public PublicationsPage goToPublicationsPage( ) throws FileNotFoundException {
		
        try{
            System.out.println( "Start goToPublicationsPage() method" );
            
            Actions actions = new Actions( driver ); 
            WebElement menuHoverLink = ( new WebDriverWait( driver, timeout ) ) /* Wait Up to 50 seconds should throw RunTimeExcpetion*/
                    .until(
                    		ExpectedConditions.presenceOfElementLocated(
                    				By.xpath( "//*[@id=\"menu-item-1869\"]/a" )
                    				)
                    		);            
            actions.moveToElement( menuHoverLink ).perform( ); //click in menu ajuda
            WebElement menuClickLink = ( new WebDriverWait( driver, timeout ) ) /* Wait Up to 50 seconds should throw RunTimeExcpetion*/
                    .until(
                    		ExpectedConditions.presenceOfElementLocated(
                    				By.xpath( "//*[@id=\"menu-item-3769\"]/a" )
                    				)
                    		);
            menuClickLink.click( );
            System.out.println( "Finished goToPublicationsPage() method" );
        }catch( NoSuchElementException e ){
        	System.out.println( "Could not find the link element" );
        	throw e;
        } 
        
        return new PublicationsPage( driver );
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
    
    /**
     * 
     * @throws FileNotFoundException 
     */
    public NewsPage goToNewsPage( ) throws FileNotFoundException{
        try{
            System.out.println( "Start goToNewsPage( ) method" );
            WebElement newsLink = ( new WebDriverWait( driver, timeout ) ) /* Wait Up to 50 seconds should throw RunTimeExcpetion*/
            .until(
            		ExpectedConditions.presenceOfElementLocated(
            				By.xpath( "//*[@id=\"menu-item-1858\"]/a" )
            				)
            		);            
            newsLink.click( );
            System.out.println( "Finished goToNewsPage( ) method" );
        }catch( NoSuchElementException e ){
        	System.out.println( "Could not find the link element" );
        	throw e;
        } 
        
        return new NewsPage( driver );
    }
    
    /**
     * 
     * @param language
     * @return
     */
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
    			if( url.startsWith( "http://www.facebook.com/" ) ){
    				if( !textTolink.get( text ).equals( url ) )
    					return false;
    			} else {
    				if( !AnalyzeURLs.linkExists( url , text , textTolink ) )
    					return false;
	    			
    			}
    			//Debug System.out.println( "Return = " + linkExists( url , text ) );
    		
    		}
    		
	    	return true;
    	} catch( NoSuchElementException e ){
            System.out.println( "Error in checkOPSite" );
            e.printStackTrace( );
            return false;
    	}
    	
    
    }
    
    /**
     * 
     * @param filename
     * @return
     */
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
	
}

