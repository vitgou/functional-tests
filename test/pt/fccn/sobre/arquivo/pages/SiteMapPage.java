package pt.fccn.sobre.arquivo.pages;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SiteMapPage {

	WebDriver driver;
	private final String dir = "sobreTestsFiles";
	List< String > topicsPT;
	List< String > topicsEN;
	private final int timeout = 50;
	
	public SiteMapPage( WebDriver driver ) throws FileNotFoundException{
		this.driver = driver;
		
		if( !loadTopics( "SiteMapTopicsPT.txt" , "pt" ) ) 
			throw new FileNotFoundException( );
		
		if( !loadTopics( "SiteMapTopicsEN.txt" , "en" ) )
			throw new FileNotFoundException( );
		
	}
	
	public boolean checkSiteMap( String language ) {
		System.out.println( "[checkSiteMap]" );
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
    			String text = elem.getText( );
    			Charset.forName( "UTF-8" ).encode( text );
    		
    		}
    		
	    	return true;
    	} catch( NoSuchElementException e ){
            System.out.println( "Error in checkOPSite" );
            e.printStackTrace( );
            return false;
    	}
    	
	}
	
	private boolean loadTopics( String filename , String language ) {
		try {
			String line;
			topicsPT = new ArrayList< String >( );
			topicsEN = new ArrayList< String >( );
		    InputStream fis = new FileInputStream( dir.concat( File.separator ).concat( filename ) );
		    InputStreamReader isr = new InputStreamReader( fis, Charset.forName( "UTF-8" ) );
		    BufferedReader br = new BufferedReader(isr);
		    while ( ( line = br.readLine( ) ) != null ) {
				if( language.equals( "pt" ) )
					topicsPT.add( line );
				else
					topicsEN.add( line );
			}
			br.close( );
			isr.close( );
			fis.close( );
			
			//printQuestions( ); //Debug
			
			return true;
		} catch ( FileNotFoundException exFile ) {
			exFile.printStackTrace( );
			return false;
		} catch ( IOException exIo ) {
			exIo.printStackTrace( );
			return false;
		}
		
	}
	
}
