package pt.fccn.sobre.arquivo.pages;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CollaboratePage {
	
	WebDriver driver;
	private final int timeout = 50;
	
	/**
	 * 
	 * @param driver
	 * @throws FileNotFoundException
	 */
	public CollaboratePage( WebDriver driver ) throws FileNotFoundException{
		this.driver = driver;
	}
	
	
	/**
	 * 
	 * @return
	 * @throws FileNotFoundException
	 */
	public SuggestionPage goToSuggestionSitePage( ) throws FileNotFoundException{
		
        try{
            System.out.println( "Start goToSuggestionSitePage() method" );
            WebElement suggestionSiteLink = ( new WebDriverWait( driver, timeout ) ) /* Wait Up to 50 seconds should throw RunTimeExcpetion*/
            .until(
            		ExpectedConditions.presenceOfElementLocated(
            				By.xpath( "//*[@id=\"parent-fieldname-text\"]/ul/li[2]/a" )
            				)
            		);            
            suggestionSiteLink.click( );
            System.out.println( "Finished goToSuggestionSitePage() method" );
        }catch( NoSuchElementException e ){
        	System.out.println( "Could not find the link element" );
        	throw e;
        }
		
		return new SuggestionPage( driver );
	}
	
	
}
