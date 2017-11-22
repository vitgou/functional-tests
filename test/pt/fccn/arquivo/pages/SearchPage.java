/**
 * Copyright (C) 2011 Simao Fontes <simao.fontes@fccn.pt>
 * Copyright (C) 2011 SAW Group - FCCN <saw@asa.fccn.pt>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package pt.fccn.arquivo.pages;


import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.plaf.basic.BasicInternalFrameTitlePane.RestoreAction;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;


/**
 * @author Simao Fontes
 *
 */
public class SearchPage {
    private final WebDriver driver;
    private final String numberOfResultsTag = "resultados";
    private final String listOfResultsTag = "resultados-lista";
    private final String resultTextTag = "resumo";
    private final String pageURLCheck = "search.jsp";
    // Patern to detect if there are results
    private Pattern noResultsPattern = Pattern.compile("\\d Resultados");
    
    // Tags for searching
    private final String resultLinkXpath = "//div[@id='" + listOfResultsTag + "']/ul/li/h2/a";
    private boolean ispre_prod=false;
    private String server_name=null;
    /**
     * Create a new SearchPage from navigation
     * @param driver
     */
    public SearchPage(WebDriver driver,boolean Ispre_prod){
        this.driver= driver;
        // Check that we're on the right page.
        this.ispre_prod=Ispre_prod;
      
        if (!((new WebDriverWait(driver, 25)).until(ExpectedConditions.urlContains(pageURLCheck)))) {
            throw new IllegalStateException("This is not the results search page\n URL of current page: " + driver.getCurrentUrl());
        }
        if (ispre_prod){
        	
   			this.server_name=driver.getCurrentUrl().replace("search.jsp?l=pt&query=fccn","");
        }
        
    }
    
    /**
     * Verify that the title of the search page contains the term searched.
     * @param term term that was searched in the system
     * @return true if term is in the title of the page
     */
    public boolean titleIsCorrect(String query){
    	
        if (driver.getTitle().contains(query) && !query.isEmpty()){
        	return true;
        }
        else
            return false;
    }
    
    /**
     * Verify that the spellchecker suggests a query
     * @param query the test query for the spellchecker
     * @return true if it suggest the term "teste"
     */
    public boolean spellcheckerOK(String query) {
        WebElement txtSearchElement = (new WebDriverWait(driver, 25)) /* Wait Up to 25 seconds should throw RunTimeExcpetion*/
            .until(ExpectedConditions.presenceOfElementLocated(By.id("txtSearch")));    

        txtSearchElement.clear();
        txtSearchElement.sendKeys(query);

        WebElement btnSubmitElement = (new WebDriverWait(driver, 25)) /* Wait Up to 25 seconds should throw RunTimeExcpetion*/
            .until(ExpectedConditions.presenceOfElementLocated(By.id("btnSubmit")));    

    	  btnSubmitElement.click();
    	  
        WebElement spellCheckerElement = (new WebDriverWait(driver, 25))
            .until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"second-column\"]/div[2]/span/a")));  

    	  String spellCheckerTest = spellCheckerElement.getText();

    	  if(!spellCheckerTest.equals("teste")){
    		  System.out.print("Spellchecker is not working on properly");
    		  return false;	
    	  }
    	  return true;
    }
    
    /**
     * Verify that the spellchecker no suggests a correct query (#30 issue github)
     * @param query the test query for the spellchecker
     * @return 
     */
    public boolean spellcheckerCorrect( ) {
    	System.out.println( "[SpellCheckerCorrect]" );

    	if( driver.findElements( By.xpath( "//*[@id=\"second-column\"]/div[2]/span/a" ) ).size( ) <= 0 ) {
    		System.out.println( "spellCheckCorrect true" );
    		return true;
    	} else {
    		String text = driver.findElement( By.xpath( "//*[@id=\"second-column\"]/div[2]/span/a" ) ).getText( );
    		System.out.println( "spellCheckCorrect false text = " + text );
        	return false;
        }
    	
    }    
    
    
    /**
     * Verify that the term exists in as a search result
     * @param query Term that was searched
     * @return true if the term exists in the first result title or in the snippet text
     */
    public boolean existsInResults(String query)  {
        boolean result = false;
        
        String resultsTags = driver.findElement(By.id(numberOfResultsTag)).getText();
        Matcher m = noResultsPattern.matcher(resultsTags);
            if (m.matches()){
                System.out.print("No results for this query");
                return false;
            }
        
        WebElement listOfResults = driver.findElement(By.id(listOfResultsTag));
        String resultTitle = listOfResults.findElement(By.xpath("/html/body/div[2]/div/div[2]/div[2]/div[3]/ul/li")).getText();
        if (resultTitle.toLowerCase().contains(query))
            result = true;
        else
            if (listOfResults.findElement(By.className(resultTextTag)).getText().contains(query)){
                result = true;
             
            }
        return result;
    }
    
    /**
     * Verify that the term exists in as a search result
     * @param query Term that was searched
     * @return true if the term exists in the first result title or in the snippet text
     */
    public String getFirstResult() {
      WebElement listOfResults = (new WebDriverWait(driver, 25)) /* Wait Up to 25 seconds should throw RunTimeExcpetion*/
        .until(ExpectedConditions.presenceOfElementLocated(By.id(listOfResultsTag)));

    	WebElement firstResult = (new WebDriverWait(driver, 25))
        .until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"resultados-lista\"]/ul/li[1]/h2")));  

    	return firstResult.getText();
    }
    
    /**
     * Returns the first result from the query
     * @return returns the archived page from the first result of a query
     */
    public ArchivedPage firstResult(){
        WebElement archivedPageLink = driver.findElement(By.xpath(resultLinkXpath));
        archivedPageLink.click();
        return new ArchivedPage(driver);
    }
    /**
     * Inspect that the replay bar is working
   	 * @param 
   	 * @param id
   	 * @return
   	 * +"wayback/wayback/20120825003419/http://blogs.sapo.pt/"
   	 */
   	 public boolean  testReplayBar (){
   		 
   		WebElement replay_bar =null;
   		ArchivedPage getfirstResult=null;
   		
   		if(!ispre_prod){
   			
   			getfirstResult=firstResult();
   			driver.get(driver.getCurrentUrl());
   		}
   		else {
   			try {
				driver.get(this.server_name+"wayback/wayback/20120825003419/http://blogs.sapo.pt/");
			} catch (Exception e) {
				return false;
			}

   		}
   		
   		//This could happen when a page is offline, for that it can not find the replay_bar with the date		
   		try{
   			replay_bar = driver.findElement(By.xpath("//*[@id=\"replay_iframe\"]"));
        //System.out.print("Replay_bar= "+replay_bar.getAttribute("src"));
   		}catch(NoSuchElementException e){
   			//System.out.print("Replay bar not found. "+this.getClass().getName());
   			return false;
   		}
   			
   			if (replay_bar.getAttribute("src") != null)
   					return true;
   			
   		return false;
   	}
  
}
