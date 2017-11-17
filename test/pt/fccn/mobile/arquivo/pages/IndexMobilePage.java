package pt.fccn.mobile.arquivo.pages;

import java.io.FileNotFoundException;
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
    private String url =null;
    private String[ ] topicsToSearch = new String[ ]{ "Antonio Costa" , "livros" , "Simone de Beauvoir" };
    private String[ ] URLToSearch = new String[ ]{ "cienciaHoje.pt" , "uc.pt" };
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
    
    /**
     * Click the link to the footer of the page
     * @throws FileNotFoundException 
     */
    public AdvancedSearchPage goToAdvancedPage( ) {
    	
    	String xpathButton = "//*[@id=\"menuButton\"]";
    	String xpathAdvancedSearchMenu = "//*[@id=\"mainMenu\"]/a[2]";
    	try{
            System.out.println( "Start goToAdvancedPage( ) method" );
            WebElement menuButton = ( new WebDriverWait( driver, timeout ) ) /* Wait Up to 50 seconds should throw RunTimeExcpetion*/
            .until(
            		ExpectedConditions.presenceOfElementLocated(
            				By.xpath( xpathButton )
            				) 
            		);            
            menuButton.click( );
            sleepThread( );
            WebElement advancedSearchMenu = ( new WebDriverWait( driver, timeout ) ) /* Wait Up to 50 seconds should throw RunTimeExcpetion*/
            .until(
            		ExpectedConditions.presenceOfElementLocated(
            				By.xpath( xpathAdvancedSearchMenu )
            				) 
            		);            
            advancedSearchMenu.click( );
            System.out.println( "Finished goToAdvancedPage( ) method" );
        }catch( NoSuchElementException e ){
        	System.out.println( "Could not find the link element" );
        	throw e;
        }
        
        return new AdvancedSearchPage( driver );
    }
    
    public boolean checkURLSearch( String language ) {
    	System.out.println(  "[checkURLSearch]" );
    	String xpathResults = "//*[@id=\"form_container\"]/div/input"; //get search links
        String xpathButton = "//*[@id=\"form_container\"]/div/span/button";
     
		try{
			if( language.equals( "EN" ) ) {
				switchLanguage( );
				if( searchURlEN( xpathResults , xpathButton ) )
					return true;
				else
					return false;
			}
			else 
				if( searchURLPT( xpathResults , xpathButton ) )
					return true;
				else 
					return false;
			
    	}catch( NoSuchElementException e ){
            System.out.println( "Error in checkOPSite" );
            e.printStackTrace( );
            return false;
    	}
    	
    }
    
    
    
	private boolean searchURLPT( String xpathResults , String xpathSendButton ) {
		System.out.println( "[searchURLPT]" );
        for( String url : URLToSearch ) {
        	System.out.println( "Search for " + url );
    		WebElement inputElement = ( new WebDriverWait( driver, timeout ) ) /* Wait Up to 50 seconds should throw RunTimeExcpetion*/
                    .until(
                    		ExpectedConditions.presenceOfElementLocated( 
                    				By.xpath( xpathResults ) ) );
            inputElement.clear( );
            inputElement.sendKeys( url );
            
            sleepThread( );
            
            WebElement btnSubmitElement = ( new WebDriverWait( driver, timeout ) ) /* Wait Up to 50 seconds should throw RunTimeExcpetion*/
                .until(
                		ExpectedConditions.presenceOfElementLocated(
                				By.xpath( xpathSendButton ) ) );
            btnSubmitElement.click( );
            
            sleepThread( );
      
            if( !checkURLResults( url ) )
            	return false;
        }
        return true;
	}
	
	
	private boolean searchURlEN( String xpathinput , String xpathSendButton ) {
		System.out.println( "[searchURLEN]" );
        for( String topic : URLToSearch ) {
        	System.out.println( "Search for " + topic );
    		WebElement inputElement = ( new WebDriverWait( driver, timeout ) ) /* Wait Up to 50 seconds should throw RunTimeExcpetion*/
                    .until(
                    		ExpectedConditions.presenceOfElementLocated( 
                    				By.xpath( xpathinput ) ) );
            inputElement.clear( );
            inputElement.sendKeys( topic );
            
            sleepThread( );
            
            WebElement btnSubmitElement = ( new WebDriverWait( driver, timeout ) ) /* Wait Up to 50 seconds should throw RunTimeExcpetion*/
                .until(
                		ExpectedConditions.presenceOfElementLocated(
                				By.xpath( xpathSendButton ) ) );
            btnSubmitElement.click( );
            
            sleepThread( );
            
            if( !checkURLResults( url ) )
            	return false;
        }
        return true;
	}
	
	private boolean checkURLResults( String url ) {
		System.out.println( "[checkURLResults]" );
		String getResumeResults = "//*[@id=\"years\"]/*[@class=\"yearUl row\"]/*[@class=\"col-xs-6 text-left yearText\"]/h4";
	    try{
    		List< WebElement > results = ( new WebDriverWait( driver, timeout ) )
	                .until( ExpectedConditions
	                        .visibilityOfAllElementsLocatedBy(
	                        		      By.xpath( getResumeResults )
	                        )
	        );
    		
    		if( results.size( ) > 0 )
    			return true;
    		else 
    			return false;
    		
	    } catch( NoSuchElementException e ) {
            System.out.println( "Error in checkOPSite" );
            e.printStackTrace( );
            return false;
	    }
	}
	
	public boolean checkFullTextSearch( String language ) {
		System.out.println( "[checkSearch]" );
		String xpathResults = "//*[@id=\"form_container\"]/div/input"; //get search links
        String xpathButton = "//*[@id=\"form_container\"]/div/span/button";
     
		try{
			if( language.equals( "EN" ) ) {
				switchLanguage( );
				if( searchFullTextEN( xpathResults , xpathButton ) )
					return true;
				else
					return false;
			}
			else 
				if( searchFullTextPT( xpathResults , xpathButton ) )
					return true;
				else 
					return false;
			
    	}catch( NoSuchElementException e ){
            System.out.println( "Error in checkOPSite" );
            e.printStackTrace( );
            return false;
    	}
    	
	}
	
	private boolean searchFullTextPT( String xpathResults , String xpathSendButton ) {
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
	
	private boolean searchFullTextEN( String xpathinput , String xpathSendButton ) {
		System.out.println( "[searchEN]" );
        for( String topic : topicsToSearch ) {
        	System.out.println( "Search for " + topic );
    		WebElement inputElement = ( new WebDriverWait( driver, timeout ) ) /* Wait Up to 50 seconds should throw RunTimeExcpetion*/
                    .until(
                    		ExpectedConditions.presenceOfElementLocated( 
                    				By.xpath( xpathinput ) ) );
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

	
    public boolean setPreProd( String pre_prod ){
        if ( driver.getCurrentUrl( ).contains( pre_prod ) ){
           this.isPreProd = true;
        }
        return isPreProd;
    }
    
    
    /**
    * Change to the English version
    */
    private void switchLanguage( ){
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
