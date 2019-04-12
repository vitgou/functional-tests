/**
 * Copyright (C) 2015 Hugo Viana <hugo.viana@fccn.pt>
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



import java.util.regex.Pattern;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


/**
 * @author Hugo Viana
 *
 */
public class OpenSearchPage {
    private final WebDriver driver;
    private final String numberOfResultsTag = "feedContent";
    private final String listOfResultsTag = "resultados-lista";
    private static final String searchBox = "txtSearch";
    private static final String searchButton = "btnSubmit";
    private final String pageURLCheck = "opensearch";

 // Patern to detect if there are results
    private Pattern noResultsPattern = Pattern.compile("\\d Resultados");
    private boolean isPredProd=false;

    private Document doc;
    /**
     * Create a new OpenSearchPage from navigation Document contains the XML From Opensearch
     * @param driver
     */
    public OpenSearchPage(WebDriver driver,boolean isPreProd, Document doc){  
        this.isPredProd=isPreProd;
        this.driver= driver;
        this.doc = doc;
    }
    /**
     * Create a new OpenSearchPage from navigation
     * @param driver
     */
    public OpenSearchPage(WebDriver driver,boolean isPreProd){	
    	this.isPredProd=isPreProd;
        this.driver= driver;
        // Check that we're on the right page.
        if (!(driver.getCurrentUrl().contains(pageURLCheck))) {
            throw new IllegalStateException("This is not the results opensearch page\n URL of current page: " + driver.getCurrentUrl());
        }
    }
    
    /**
     * Gather search for a term
     * @param query Term that was searched
     * @return true if the term exists in the first result title or in the snippet text
     */
    public boolean existsResults(){
        try{
            System.out.println("Checking if results are presented!");
            NodeList nList = doc.getElementsByTagName("item");
            System.out.println("Number of Results: " + nList.getLength());
            if (nList.getLength()<=0)
            	return false;
            return true;
        }catch(Exception e){
            //some error getting the item tag in openSearch
            e.printStackTrace();
            return false;
        }
    }
    /**
     * Verify coherence in result between opensearch api and search on arquivo.pt
     * @param firstTitleOfResultList The first result when searching for a query through arquivo.pt full text-search
     * @return true if the first result are coherent with opensearch result, false otherwises
     */
    public boolean inspectCoherence(String firstTitleOfResultList) {
            try{
                NodeList nList = doc.getElementsByTagName("item");            
                Node nNode = nList.item(0);
                String firstTitleOpenSearch ="";
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    firstTitleOpenSearch = eElement.getElementsByTagName("title").item(0).getTextContent();
                    System.out.println("Found Title: " + firstTitleOpenSearch );                   
                }
                return firstTitleOpenSearch.contains(firstTitleOfResultList);
            }catch (Exception e) {
                e.printStackTrace(); 
                return false;
            }	
    }

    /**
     * It is only needed when the test is run individually, if not IndexPage will set this variable
     * Verify that the term exists in as a search result
     * @param query Term that was searched
     * @return true if the term exists in the first result title or in the snippet text
     */
    public String setFirstResult(String searchTerms) {
    	
        String openSearchUrl = driver.getCurrentUrl();

        //driver.get(openSearchUrl.split("opensearch")[0]);
    	driver.findElement(By.id(searchBox)).clear();
        driver.findElement(By.id(searchBox)).sendKeys(searchTerms);
        driver.findElement(By.id(searchButton)).submit();
        
        try {
          Thread.sleep(5000); //wait 5 seconds for page to load
        } catch(InterruptedException ex) {
          Thread.currentThread().interrupt();
        }

    	WebElement listOfResults = driver.findElement(By.id(listOfResultsTag));
    	String result = listOfResults.findElement(By.xpath("//*[@id=\"resultados-lista\"]/ul/li[1]/h2")).getText(); 
        return result;
    }
    
    
}
