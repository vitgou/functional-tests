package pt.fccn.sobre.arquivo.pages;

import java.io.FileNotFoundException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SuggestionPage {

	WebDriver driver;
	private final int timeout = 50;
	
	public SuggestionPage( WebDriver driver ) throws FileNotFoundException{
		this.driver = driver;
	}
	
	
	public boolean sendSuggestion( String language ) {
		System.out.println( "[sendSuggestion]" );
		
      	WebElement advAnd = (new WebDriverWait(driver, timeout)) /* Wait Up to 25 seconds should throw RunTimeExcpetion*/
            .until(ExpectedConditions.presenceOfElementLocated(By.id("adv_and")));             
        advAnd.clear();
        advAnd.sendKeys("sapo");
        WebElement siteElement = (new WebDriverWait(driver, timeout)) /* Wait Up to 25 seconds should throw RunTimeExcpetion*/
            .until(ExpectedConditions.presenceOfElementLocated(By.id("site")));
        siteElement.clear();
        siteElement.sendKeys("sapo.pt");

        WebElement btnSubmitElement = (new WebDriverWait(driver, timeout)) /* Wait Up to 25 seconds should throw RunTimeExcpetion*/
            .until(ExpectedConditions.presenceOfElementLocated(By.id("btnSubmitTop")));
        btnSubmitElement.click();
        
            
		return true;
	
	}
	
	
	
	
}
