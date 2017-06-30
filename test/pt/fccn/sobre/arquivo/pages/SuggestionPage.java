package pt.fccn.sobre.arquivo.pages;

import java.io.FileNotFoundException;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SuggestionPage {

	WebDriver driver;
	private final int timeout = 25;
	
	public SuggestionPage( WebDriver driver ) throws FileNotFoundException{
		this.driver = driver;
	}
	
	
	public boolean sendSuggestion( String language ) {
		System.out.println( "[sendSuggestion]" );
		try{
			String xpathSites = "//*[@id=\"wpcf7-f2384-p2063-o1\"]/form/div[2]/span/textarea";
			String xpathDescription = "//*[@id=\"wpcf7-f2384-p2063-o1\"]/form/div[3]/span/input";
			String xpathEmail = "//*[@id=\"wpcf7-f2384-p2063-o1\"]/form/div[4]/span/input";
			String xpathSendButton = "//*[@id=\"wpcf7-f2384-p2063-o1\"]/form/p/input";
			
	      	WebElement UrlsElement = ( new WebDriverWait( driver, timeout ) ) /* Wait Up to 25 seconds should throw RunTimeExcpetion*/
	            .until( 
	            		ExpectedConditions.presenceOfElementLocated( 
	            				By.xpath( xpathSites ) ) );             
	      	UrlsElement.clear( );
	      	UrlsElement.sendKeys( "[Test Selenium] Url field." );
	        
	        WebElement descriptionElement = ( new WebDriverWait( driver, timeout ) ) /* Wait Up to 25 seconds should throw RunTimeExcpetion*/
	            .until(
	            		ExpectedConditions.presenceOfElementLocated( 
	            				By.xpath( xpathDescription ) ) );
	        descriptionElement.clear( );
	        descriptionElement.sendKeys( "[Test Selenium] Description field." );
	
	        WebElement emailElement = ( new WebDriverWait( driver, timeout ) ) /* Wait Up to 25 seconds should throw RunTimeExcpetion*/
	                .until(
	                		ExpectedConditions.presenceOfElementLocated( 
	                				By.xpath( xpathEmail ) ) );
	        emailElement.clear( );
	        emailElement.sendKeys( "[Test Selenium] Email field." );
	        
	        driver.findElement( By.xpath( "div[class=recaptcha-checkbox-checkmark]" ) ).click( );
	        
	      	try {
	            Thread.sleep( 4000 );                 //wait for page to load
	      	} catch( InterruptedException ex ) {
	            Thread.currentThread( ).interrupt( );
	        }
	        
	        WebElement btnSubmitElement = ( new WebDriverWait( driver, timeout ) ) /* Wait Up to 25 seconds should throw RunTimeExcpetion*/
	            .until(
	            		ExpectedConditions.presenceOfElementLocated(
	            				By.id( xpathSendButton ) ) );
	        btnSubmitElement.click( );
	            
			return true;
			
		}catch( NoSuchElementException e ){
	    	System.out.println( "Could not find the link element" );
	    	throw e;
	    }
	
	}
	
	
	
	
}
