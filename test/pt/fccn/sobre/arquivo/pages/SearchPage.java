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

public class SearchPage {
	WebDriver driver;
	private final String dir = "sobreTestsFiles";
	List< String > topicsPT;
	List< String > topicsEN;
	private final int timeout = 50;
	
	public SearchPage( WebDriver driver ) throws FileNotFoundException{
		this.driver = driver;
		
		if( !loadTopics( "SearchLinksPT.txt" , "pt" ) ) 
			throw new FileNotFoundException( );
		
		if( !loadTopics( "SearchLinksEN.txt" , "en" ) )
			throw new FileNotFoundException( );
		
	}
	
	public boolean checkSearch( String language ) {
		System.out.println( "[checkSearch]" );
		String xpathResults = "//*[@id=\"search-4\"]/form/label/input"; //get search links
        String xpathButton = "//*[@id=\"wp_editor_widget-17\"]/div/div[2]/div/span/a";

		try{
			
			if( language.equals( "EN" ) )
				searchEN( xpathResults , xpathButton );
			else 
				searchPT( xpathResults , xpathButton );

    		return true;
    	} catch( NoSuchElementException e ){
            System.out.println( "Error in checkOPSite" );
            e.printStackTrace( );
            return false;
    	}
    	
	}
	
	private boolean checkResults( ) {
		System.out.println( "[checkResults]" );
		String xpathResults = "//*[@id=\"___gcse_0\"]/div"; //get search links
		try{
    		List< WebElement > results = ( new WebDriverWait( driver, timeout ) )
	                .until( ExpectedConditions
	                        .visibilityOfAllElementsLocatedBy(
	                        		      By.xpath( xpathResults )
	                        )
	        );
    		
    		System.out.println( "[checkSearch] results size = " + results.size( ) );
    		
    		for( WebElement elem : results ) {
				String text = elem.getText( );
				Charset.forName( "UTF-8" ).encode( text );
				System.out.println( "Text = " + text );
    		}
    		
	    	return true;
    	} catch( NoSuchElementException e ){
            System.out.println( "Error in checkOPSite" );
            e.printStackTrace( );
            return false;
    	}
	}
	
	private void searchEN( String xpathResults , String xpathButton ) {
		System.out.println( "[searchEN]" );
        for( String topic : topicsEN ) {
			WebElement emailElement = ( new WebDriverWait( driver, timeout ) ) /* Wait Up to 50 seconds should throw RunTimeExcpetion*/
	                .until(
	                		ExpectedConditions.presenceOfElementLocated( 
	                				By.xpath( xpathResults ) ) );
	        emailElement.clear( );
	        emailElement.sendKeys( topic );
	        
	     
	        IndexSobrePage.sleepThread( );
	        
	        WebElement btnSubmitElement = ( new WebDriverWait( driver, timeout ) ) /* Wait Up to 50 seconds should throw RunTimeExcpetion*/
	            .until(
	            		ExpectedConditions.presenceOfElementLocated(
	            				By.xpath( xpathButton ) ) );
	        btnSubmitElement.click( );
	        
	        IndexSobrePage.sleepThread( );
	        
	        checkResults( );
		}
	}
	
	private void searchPT( String xpathResults , String xpathSendButton ) {
		System.out.println( "[searchPT]" );
        for( String topic : topicsPT ) {
    		WebElement emailElement = ( new WebDriverWait( driver, timeout ) ) /* Wait Up to 50 seconds should throw RunTimeExcpetion*/
                    .until(
                    		ExpectedConditions.presenceOfElementLocated( 
                    				By.xpath( xpathResults ) ) );
            emailElement.clear( );
            emailElement.sendKeys( topic );
            
            IndexSobrePage.sleepThread( );
            
            WebElement btnSubmitElement = ( new WebDriverWait( driver, timeout ) ) /* Wait Up to 50 seconds should throw RunTimeExcpetion*/
                .until(
                		ExpectedConditions.presenceOfElementLocated(
                				By.xpath( xpathSendButton ) ) );
            btnSubmitElement.click( );
            
            IndexSobrePage.sleepThread( );
            
            checkResults( );
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
