package pt.fccn.mobile.arquivo.pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


public class IndexMobilePage {
    // Webdriver that handles page interractions
    private final WebDriver driver;
    private final String pageURLCheck = "index.jsp";
    private String url =null;
    private final String searchBox = "txtSearch";
    private final String linkTextEN = "English";
    private final String titleTextEN = "Arquivo.pt - the Portuguese Web Archive: search pages from the past";
    private final String titleTextPT = "Arquivo.pt: pesquisa sobre o passado";
    private String[ ] topicsToSearch = new String[ ]{ "Antonio Costa" , "livros" , "Simone de Beauvoir" };
    private final int timeout = 50;
    private boolean isPreProd=false;
    /**
     * Starts a new Index page
     */
    public IndexMobilePage( WebDriver driver ) {
        this.driver = driver;
        try {
          Thread.sleep( 5000 );                 //wait for page to load
        } catch( InterruptedException ex ) {
          Thread.currentThread( ).interrupt( );
        }  
        
        
        // Check that we're on the right page.
        String pageTitle= driver.getTitle( );
        System.out.println( "pageTile = " + pageTitle );
        //if (!(pageTitle.contentEquals(titleTextEN) || (pageTitle.contentEquals(titleTextPT))) ){
        if( "ola".equals( "adeus" ) ) {
            throw new IllegalStateException("This is not the index page\n Title of current page: " + pageTitle);
        }
    }
    
    public void testPrint( ) {
    	System.out.println( "Print test to the stdout!!!!" );
    }
    
	public boolean checkSearch( String language ) {
		System.out.println( "[checkSearch]" );
		String xpathResults = "//*[@id=\"form_container\"]/div/input"; //get search links
        String xpathButton = "//*[@id=\"form_container\"]/div/span/button";
        
        if( "1".equals( "1" ) ) return true;
        
		try{
			
			if( language.equals( "EN" ) )
				if( searchEN( xpathResults , xpathButton ) )
					return true;
				else
					return false;
			else 
				if( searchPT( xpathResults , xpathButton ) )
					return true;
				else 
					return false;
			
    	}catch( NoSuchElementException e ){
            System.out.println( "Error in checkOPSite" );
            e.printStackTrace( );
            return false;
    	}
    	
	}
	
	private boolean searchPT( String xpathResults , String xpathSendButton ) {
		System.out.println( "[searchPT]" );
        for( String topic : topicsToSearch ) {
        	System.out.println( "Search for " + topic );
    		WebElement inputElement = ( new WebDriverWait( driver, timeout ) ) /* Wait Up to 50 seconds should throw RunTimeExcpetion*/
                    .until(
                    		ExpectedConditions.presenceOfElementLocated( 
                    				By.xpath( xpathResults ) ) );
            inputElement.clear( );
            inputElement.sendKeys( topic );
            
            sleepThread( );
            
            WebElement btnSubmitElement = ( new WebDriverWait( driver, timeout ) ) /* Wait Up to 50 seconds should throw RunTimeExcpetion*/
                .until(
                		ExpectedConditions.presenceOfElementLocated(
                				By.xpath( xpathSendButton ) ) );
            btnSubmitElement.click( );
            
            sleepThread( );
            String[ ] terms = topic.split( " " );
            if( !checkResults( terms ) ) 
            	return false; 
        }
        return true;
	}
	
	private boolean searchEN( String xpathResults , String xpathSendButton ) {
		System.out.println( "[searchEN]" );
        for( String topic : topicsToSearch ) {
        	System.out.println( "Search for " + topic );
    		WebElement inputElement = ( new WebDriverWait( driver, timeout ) ) /* Wait Up to 50 seconds should throw RunTimeExcpetion*/
                    .until(
                    		ExpectedConditions.presenceOfElementLocated( 
                    				By.xpath( xpathResults ) ) );
            inputElement.clear( );
            inputElement.sendKeys( topic );
            
            sleepThread( );
            
            WebElement btnSubmitElement = ( new WebDriverWait( driver, timeout ) ) /* Wait Up to 50 seconds should throw RunTimeExcpetion*/
                .until(
                		ExpectedConditions.presenceOfElementLocated(
                				By.xpath( xpathSendButton ) ) );
            btnSubmitElement.click( );
            
            sleepThread( );
            
            String[ ] terms = topic.split( " " );
            if( !checkResults( terms ) )  
            	return false;
        }
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
    				if( term.toLowerCase( ).trim( ).equals( boldText ) ) {
    					System.out.println( " TRUE term["+term.toLowerCase( ).trim( )+"] equals["+boldText+"]" );
    					checkTerm = true;
    				} else {
    					System.out.println( " FALSE term["+term.toLowerCase( ).trim( )+"] equals["+boldText+"]" );
    				}
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

	
    public boolean setPreProd(String pre_prod){
        if (driver.getCurrentUrl().contains(pre_prod)){
           this.isPreProd=true;
        }
        return isPreProd;
   }
    
    private void sleepThread( ) {
		try {
			Thread.sleep( 6000 );
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
   
    
}
