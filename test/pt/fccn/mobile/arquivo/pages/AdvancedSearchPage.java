package pt.fccn.mobile.arquivo.pages;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class AdvancedSearchPage {
	private final WebDriver driver;
	private final String topicsToSearch = "expo 98";
	private final String negTerm = "concerto";
	private final String expressionTerm = "pavilhao da utopia";
	private final String[ ] terms = new String[ ]{ "pavilhao da utopia", "pavilhao",  "da",  "utopia", "expo" , "98" };
	private final String siteSearch = "www.arquivo.pt";
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
    
    public boolean checkSiteOperator( String  language ) {
    	System.out.println(  "[checkSiteOperator]" );
    	String inputSearch 		= "//*[@id=\"adv_and\"]";
    	String divExpandable 	= "//*[@id=\"domains\"]/legend/i";
    	String inputSiteSearch 	= "//*[@id=\"site\"]"; 
    	String buttonSearch 	= "//*[@id=\"btnSubmitBottom\"]";
    	
		try{
			if( language.equals( "EN" ) ) {
				switchLanguage( );
			}
			
			if( searchSiteSearch( inputSearch , inputSiteSearch , divExpandable , buttonSearch ) )
				return true;
			else 
				return false;
			
    	} catch( NoSuchElementException e ) {
            System.out.println( "Error in checkOPSite" );
            e.printStackTrace( );
            return false;
    	}
    }
    
	private boolean searchSiteSearch( String inputSearch, String inputSiteSearch, String divExpandable, String buttonSearch ) {
		System.out.println( "[searchSiteSearch]" );
        
    	System.out.println( "Search for " + topicsToSearch );
		WebElement elementSearch = ( new WebDriverWait( driver, timeout ) ) /* Wait Up to 50 seconds should throw RunTimeExcpetion*/
                .until(
                		ExpectedConditions.presenceOfElementLocated( 
                				By.xpath( inputSearch ) ) );
		elementSearch.clear( );
		elementSearch.sendKeys( topicsToSearch );
        
	    WebElement divElement = ( new WebDriverWait( driver, timeout ) ) /* Wait Up to 50 seconds should throw RunTimeExcpetion*/
	            .until(
	            		ExpectedConditions.presenceOfElementLocated(
	            				By.xpath( divExpandable ) ) );
	    divElement.click( );
	        
	    sleepThread( );
	    
		System.out.println( "Search for site \"" + siteSearch+ "\"" );
        WebElement elementExpression = ( new WebDriverWait( driver, timeout ) ) /* Wait Up to 50 seconds should throw RunTimeExcpetion*/
                .until(
                		ExpectedConditions.presenceOfElementLocated( 
                				By.xpath( inputSiteSearch ) ) );
        elementExpression.clear( );
        elementExpression.sendKeys( siteSearch );
        
        WebElement btnSubmitElement = ( new WebDriverWait( driver, timeout ) ) /* Wait Up to 50 seconds should throw RunTimeExcpetion*/
            .until(
            		ExpectedConditions.presenceOfElementLocated(
            				By.xpath( buttonSearch ) ) );
        btnSubmitElement.click( );
        
        sleepThread( );
       
        if( !checkSiteResults( siteSearch ) )
        	return false;

        return true;
	}
		
	private boolean checkSiteResults( String siteSearch ) {
		System.out.println( "[AdvancedSearch][checkSiteResults]" );
		String getURLResults = "//*[@id=\"resultados-lista\"]/ul/li/div[1]/div/a";
		String domainSearch = expandURL( siteSearch.toLowerCase( ).trim( ) );

	    try{
    		List< WebElement > results = ( new WebDriverWait( driver, timeout ) )
	                .until( ExpectedConditions
	                        .visibilityOfAllElementsLocatedBy(
	                        		      By.xpath( getURLResults )
	                        )
	        );
    		
    		System.out.println( "results size = " + results.size( ) );
    		for( WebElement elem : results ) {
    			String url = elem.getText( ).toLowerCase( ).trim( );
    			String treatedURL = expandURL( url );
				System.out.println( "[AdvancedSearch][checkSiteResults] domainSearch["+domainSearch+"] equals treatedURL["+treatedURL+"]" );
				if( !domainSearch.equals( treatedURL ) ) 
					return false;
    		}
	    	
    		return true;
    		
	    } catch( NoSuchElementException e ){
            System.out.println( "Error in checkOPSite" );
            e.printStackTrace( );
            return false;
    	}
	}
    
    public boolean checkAdvancedSearch( String language ) {
    	System.out.println(  "[checkAdvancedSearch]" );
    	String xpathExpandableDiv = "//*[@id=\"conteudo-pesquisa\"]/form/div[1]";
    	
    	String inputSearch = "//*[@id=\"adv_and\"]"; //*[@id="adv_and"]
    	String inputExpression = "//*[@id=\"adv_phr\"]"; //*[@id="adv_phr"]
    	String inputNeg = "//*[@id=\"adv_not\"]"; //*[@id="adv_not"]
    	String buttonSearch = "//*[@id=\"btnSubmitBottom\"]"; //*[@id="btnSubmitBottom"]
    	
		try{
			WebElement btnSubmitElement = ( new WebDriverWait( driver, timeout ) ) /* Wait Up to 50 seconds should throw RunTimeExcpetion*/
	            .until(
	            		ExpectedConditions.presenceOfElementLocated(
	            				By.xpath( xpathExpandableDiv ) ) );
	        btnSubmitElement.click( );
			
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
        
		System.out.println( "Search for expression \"" + expressionTerm+ "\"" );
        WebElement elementExpression = ( new WebDriverWait( driver, timeout ) ) /* Wait Up to 50 seconds should throw RunTimeExcpetion*/
                .until(
                		ExpectedConditions.presenceOfElementLocated( 
                				By.xpath( inputExpression ) ) );
        elementExpression.clear( );
        elementExpression.sendKeys( expressionTerm );
 
        System.out.println( "Search for denial operator -" + negTerm );
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
        
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", btnSubmitElement);
        
        btnSubmitElement.click( );
        
        sleepThread( );
       /*
        if( !checkResults( terms ) )
        	return false;
*/
        return true;
	}
	
	private boolean searchFullTextEN( String inputSearch , String inputExpression , String inputNeg , String buttonSearch ) {
		System.out.println( "[AdvancedTest][searchEN]" );
        
    	System.out.println( "Search for " + topicsToSearch );
		WebElement elementSearch = ( new WebDriverWait( driver, timeout ) ) /* Wait Up to 50 seconds should throw RunTimeExcpetion*/
                .until(
                		ExpectedConditions.presenceOfElementLocated( 
                				By.xpath( inputSearch ) ) );
		elementSearch.clear( );
		elementSearch.sendKeys( topicsToSearch );
		
		System.out.println( "Search for expression \"" + expressionTerm+ "\"" );
        WebElement elementExpression = ( new WebDriverWait( driver, timeout ) ) /* Wait Up to 50 seconds should throw RunTimeExcpetion*/
                .until(
                		ExpectedConditions.presenceOfElementLocated( 
                				By.xpath( inputExpression ) ) );
        elementExpression.clear( );
        elementExpression.sendKeys( expressionTerm );
        
        System.out.println( "Search for denial operator -" + negTerm );
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
        
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", btnSubmitElement);
        
        sleepThread( );
       /*
        if( !checkResults( terms ) )
        	return false;
*/
        return true;
	}
	
	private boolean checkResults( String[ ] terms ) {
		System.out.println( "[AdvancedSearch][checkResults]" );
		String getResumeResults = "//*[@id=\"resultados-lista\"]/ul/li[1]/div[2]/span/em";
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
    				System.out.println( "[AdvancedSearch][checkResults] term["+term.toLowerCase( ).trim( )+"] equals boldText["+boldText+"]" );
    				if( term.toLowerCase( ).trim( ).equals( boldText ) ) 
    					checkTerm = true;
    			} 	
    			if( !checkTerm )
    				return false;
    		}
	    	
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
    
    
    /**
     * remove protocol and subdirectories in the url
     * @param url
     * @return
     */
	public String expandURL( String url ) {
		 	String urlResult;
		 	
	    	if ( url.startsWith( "http://" ) ) 
	    		urlResult = urlRemoveProtocol( "http://" , url );
			else if ( url.startsWith( "https://" ) ) 
				urlResult = urlRemoveProtocol( "https://" , url );
			else 
				urlResult = urlRemoveProtocol( "http://" , "http://".concat( url ) );
			
			if( urlResult == null || urlResult == "" )
				return "";
			
			if( urlResult.startsWith( "www." ) ) 
				urlResult = urlResult.replaceFirst( "www." , "" );
			
			urlResult = removeSubdirectories( urlResult );
			
	    	return urlResult;
	}

	/**
	 * remove protocol in the url stIndexPage index = new IndexPage( driver );ring
	 * @param protocol
	 * @param url
	 * @return
	 */
	public String urlRemoveProtocol( String protocol , String url ) {
		String urlexpanded = "";
		String siteHost = "";
		try{
			URL siteURL = new URL( url );
			siteHost = siteURL.getHost( );
			url = url.replace( siteHost, siteHost.toLowerCase( ) ); // hostname to lowercase
			urlexpanded = url.substring( protocol.length( ) );
			return urlexpanded; 
		} catch ( MalformedURLException e ) {
			return null;
		}
	}
	
	/**
	 * remove subdirectorires in the url string
	 * @param input
	 * @return
	 */
	public static String removeSubdirectories( String input ) {
		if( input == null || input.equals( "" ) ) return "";
		int idx = input.indexOf( "/" );
		if( idx == -1 ) return input;
		return input.substring( 0 , idx );
	}

    private void sleepThread( ) {
 		try {
 			Thread.sleep( 5000 );
 		} catch ( InterruptedException e ) {
 			e.printStackTrace( );
 		}
 	}
    
}
