package pt.fccn.mobile.arquivo.pages;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class AdvancedSearchPage {
	private final WebDriver driver;
	private final String topicsToSearch = "expo 98";
	private final String negTerm = "concerto";
	private final String expressionTerm = "\"pavilhao da utopia\"";
	private final String[ ] terms = new String[ ]{ "pavilhao da utopia" , "expo" , "98" };
	private final int timeout = 50;
	
    public AdvancedSearchPage( WebDriver driver ) {
        this.driver = driver;
        try {
          Thread.sleep( 5000 );                 //wait for page to load
        } catch( InterruptedException ex ) {
          Thread.currentThread( ).interrupt( );
        }  
        
        
        // Check that we're on the right page.
        String pageTitle= driver.getTitle( );
        System.out.println( "pageTile = " + pageTitle );
        
    }
    
    public boolean checkAdvancedSearch( String language ) {
    	
    	System.out.println(  "[checkAdvancedSearch]" );
    	String inputSearch = "//*[@id=\"adv_and\"]";
    	String inputExpression = "//*[@id=\"adv_phr\"]";
    	String inputNeg = "//*[@id=\"adv_not\"]";
    	String buttonSearch = "//*[@id=\"btnSubmitBottom\"]";
    	
		try{
			if( language.equals( "EN" ) ) {
				switchLanguage( );
				if( searchFullTextEN( inputSearch , inputExpression , inputNeg , buttonSearch ) )
					return true;
				else
					return false;
			}
			else 
				if( searchFullTextPT( inputSearch , inputExpression , inputNeg , buttonSearch ) )
					return true;
				else 
					return false;
			
    	} catch( NoSuchElementException e ) {
            System.out.println( "Error in checkOPSite" );
            e.printStackTrace( );
            return false;
    	}
    	
    }
    
    
	private boolean searchFullTextPT( String inputSearch , String inputExpression , String inputNeg , String buttonSearch ) {
		System.out.println( "[searchFullTextPT]" );
         
    	System.out.println( "Search for " + topicsToSearch );
		WebElement elementSearch = ( new WebDriverWait( driver, timeout ) ) /* Wait Up to 50 seconds should throw RunTimeExcpetion*/
                .until(
                		ExpectedConditions.presenceOfElementLocated( 
                				By.xpath( inputSearch ) ) );
		elementSearch.clear( );
		elementSearch.sendKeys( topicsToSearch );
        
        WebElement elementExpression = ( new WebDriverWait( driver, timeout ) ) /* Wait Up to 50 seconds should throw RunTimeExcpetion*/
                .until(
                		ExpectedConditions.presenceOfElementLocated( 
                				By.xpath( inputExpression ) ) );
        elementExpression.clear( );
        elementExpression.sendKeys( expressionTerm );
        
        WebElement elementNeg = ( new WebDriverWait( driver, timeout ) ) /* Wait Up to 50 seconds should throw RunTimeExcpetion*/
                .until(
                		ExpectedConditions.presenceOfElementLocated( 
                				By.xpath( inputNeg ) ) );
        elementNeg.clear( );
        elementNeg.sendKeys( negTerm );
        
        
        sleepThread( );
        
        WebElement btnSubmitElement = ( new WebDriverWait( driver, timeout ) ) /* Wait Up to 50 seconds should throw RunTimeExcpetion*/
            .until(
            		ExpectedConditions.presenceOfElementLocated(
            				By.xpath( buttonSearch ) ) );
        btnSubmitElement.click( );
        
        sleepThread( );
       
        if( !checkResults( terms ) )
        	return false;

        return true;
	}
	
	private boolean searchFullTextEN( String inputSearch , String inputExpression , String inputNeg , String buttonSearch ) {
		System.out.println( "[searchEN]" );
        
    	System.out.println( "Search for " + topicsToSearch );
		WebElement elementSearch = ( new WebDriverWait( driver, timeout ) ) /* Wait Up to 50 seconds should throw RunTimeExcpetion*/
                .until(
                		ExpectedConditions.presenceOfElementLocated( 
                				By.xpath( inputSearch ) ) );
		elementSearch.clear( );
		elementSearch.sendKeys( topicsToSearch );
        
        WebElement elementExpression = ( new WebDriverWait( driver, timeout ) ) /* Wait Up to 50 seconds should throw RunTimeExcpetion*/
                .until(
                		ExpectedConditions.presenceOfElementLocated( 
                				By.xpath( inputExpression ) ) );
        elementExpression.clear( );
        elementExpression.sendKeys( expressionTerm );
        
        WebElement elementNeg = ( new WebDriverWait( driver, timeout ) ) /* Wait Up to 50 seconds should throw RunTimeExcpetion*/
                .until(
                		ExpectedConditions.presenceOfElementLocated( 
                				By.xpath( inputNeg ) ) );
        elementNeg.clear( );
        elementNeg.sendKeys( negTerm );
        
        
        sleepThread( );
        
        WebElement btnSubmitElement = ( new WebDriverWait( driver, timeout ) ) /* Wait Up to 50 seconds should throw RunTimeExcpetion*/
            .until(
            		ExpectedConditions.presenceOfElementLocated(
            				By.xpath( buttonSearch ) ) );
        btnSubmitElement.click( );
        
        sleepThread( );
       
        if( !checkResults( terms ) )
        	return false;

        return true;
	}
	
	private boolean checkResults( String[ ] terms ) {
		System.out.println( "[checkResults]" );
		String getResumeResults = "//*[@id=\"resultados-lista\"]/ul/li/span[1]/em";
		boolean checkTerm = false;
	    try{
    		List< WebElement > results = ( new WebDriverWait( driver, timeout ) )
	                .until( ExpectedConditions
	                        .visibilityOfAllElementsLocatedBy(
	                        		      By.xpath( getResumeResults )
	                        )
	        );
    		
    		System.out.println( "results size = " + results.size( ) );
    		for( WebElement elem : results ) {
    			String boldText = elem.getText( ).toLowerCase( ).trim( );
    			for( String term : terms ){
    				if( term.toLowerCase( ).trim( ).equals( boldText ) ) 
    					checkTerm = true;
    			} 			
    		}
	    	
    		if( !checkTerm )
    			return false;
    		else 
    			return true;
    		
	    } catch( NoSuchElementException e ){
            System.out.println( "Error in checkOPSite" );
            e.printStackTrace( );
            return false;
    	}
	}

    /**
    * Change to the English version
    */
    private void switchLanguage( ){
    	System.out.println( "[switchLanguage]" );
    	String xpathEnglishVersion = "//*[@id=\"languageSelection\"]";
      	if( driver.findElement( By.xpath( xpathEnglishVersion ) ).getText( ).equals( "EN" ) ) {
      		System.out.println( "Change language to English" );
      		driver.findElement( By.xpath( xpathEnglishVersion ) ).click( );
      		sleepThread( );
      	}
    }
    
    private void sleepThread( ) {
 		try {
 			Thread.sleep( 4000 );
 		} catch ( InterruptedException e ) {
 			e.printStackTrace( );
 		}
 	}
    
}
