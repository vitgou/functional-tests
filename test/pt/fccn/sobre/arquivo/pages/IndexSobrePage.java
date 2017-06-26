package pt.fccn.sobre.arquivo.pages;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


public class IndexSobrePage {
	WebDriver driver;
	
	private final int timeout = 50;
	
    public IndexSobrePage( WebDriver driver ) throws IOException {
        this.driver = driver;
        
        
        Properties prop = new Properties( );
    	InputStream input = null;
    	try {
			input = new FileInputStream( "sobreTestsFiles/props_indexPage.properties" );
			prop.load( input ); //load a properties file
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
        String pageTitle= driver.getTitle();
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
            				By.xpath("//*[@id=\"wp_editor_widget-8\"]/ul/li[1]/a")
            				)
            		);            
            cQuestionsLink.click( );
            System.out.println( "Finished goToCommonQuestionsPage() method" );
        }catch( NoSuchElementException e ){
        	System.out.println( "Could not find the pesquisa-avancada element" );
        	throw e;
        } 
        
        return new CommonQuestionsPage( driver );
    }
    
    
    
}

