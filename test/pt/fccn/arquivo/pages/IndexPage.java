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



import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.NoSuchElementException;

import java.net.URL;
import javax.xml.parsers.*;
import org.w3c.dom.*;
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
public class IndexPage {
    // Webdriver that handles page interractions
    private final WebDriver driver;
    private static final String pageURLCheck = "index.jsp";
    private String url =null;
    private static final String searchBox = "txtSearch";
    private static final String searchButton = "btnSubmit";
    private static final String highlightId = "ver-destaques";
    private static final String linkTextEN = "English";
    private String termsandconditionstitleTextPT = "Termos e Condições";
    private static final String linkTextPT = "Português";
    private static final String titleTextEN = "Arquivo.pt - the Portuguese Web Archive: search pages from the past";
    private static final String titleTextPT = "Arquivo.pt: pesquisa sobre o passado";
    private static final String cssTermsConditions = "#terms-conditions";
    private boolean isPreProd=false;
    /**
     * Starts a new Index page
     */
    public IndexPage(WebDriver driver){
        this.driver = driver;
        try {
          Thread.sleep(5000);                 //wait for page to load
        } catch(InterruptedException ex) {
          Thread.currentThread().interrupt();
        }  
        // Check that we're on the right page.
        String pageTitle= driver.getTitle();
        if (!(pageTitle.contentEquals(titleTextEN) || (pageTitle.contentEquals(titleTextPT)))){
            throw new IllegalStateException("This is not the index page\n Title of current page: " + pageTitle);
        }
    }
    
    /**
     * @param pre_prod
     * @return
     */
    public boolean setPreProd(String pre_prod){
         if (driver.getCurrentUrl().contains(pre_prod)){
            this.isPreProd=true;
         }
         return isPreProd;
    }
    
    /**
     * Searches for a string in the interface
     * @param searchTerms String of terms to search for
     * @return result page for query
     */
    public SearchPage search(String searchTerms){
        WebElement searchBoxElement = (new WebDriverWait(driver, 25)) /* Wait Up to 25 seconds should throw RunTimeExcpetion*/
            .until(ExpectedConditions.presenceOfElementLocated(By.id(searchBox)));        
        searchBoxElement.clear();
        searchBoxElement.sendKeys(searchTerms);
        WebElement searchButtonElement = (new WebDriverWait(driver, 25)) /* Wait Up to 25 seconds should throw RunTimeExcpetion*/
            .until(ExpectedConditions.presenceOfElementLocated(By.id(searchButton)));    
        searchButtonElement.submit();

        return new SearchPage(driver,isPreProd);
    }
    

    private static Document loadTestDocument(String url) throws Exception {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true);
        return factory.newDocumentBuilder().parse(new URL(url).openStream());
    }

    /**
     * Searches for a string in the interface
     * @param searchTerms String of terms to search for
     * @return result page for query
     */
    public OpenSearchPage opensearch(String searchTerms,boolean isPredprod){
        Document doc = null;
        try
        {
            String[] Url = driver.getCurrentUrl().split(".pt");
            DocumentBuilderFactory f = 
                    DocumentBuilderFactory.newInstance();
            DocumentBuilder b = f.newDocumentBuilder();
            doc = b.parse(Url[0]+".pt/opensearch?query="+searchTerms);
            doc.getDocumentElement().normalize();
            System.out.println("URL: " + Url[0]+".pt/opensearch?query="+searchTerms);
            /*driver.get(Url[0]+".pt/opensearch?query="+searchTerms);*/      
                        
            /*
            NodeList nList = doc.getElementsByTagName("item");
            for (int temp = 0; temp < nList.getLength(); temp++) {
                Node nNode = nList.item(temp);
                System.out.println("\nCurrent Element :" + nNode.getNodeName());
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    System.out.println("URL: " + eElement.getElementsByTagName("link").item(0).getTextContent());
                    System.out.println("Arcname: " + eElement.getElementsByTagName("pwa:arcname").item(0).getTextContent());
                }
            }
            */
        }catch(Exception e){System.out.println("Error loading XML: " + e);}
        return new OpenSearchPage(driver,isPredprod,doc);
    }
    
    
    /**
     * Change language of the page to english
     */
    public void langToEnglish(){
        try{         
             WebElement langElem = driver.findElement(By.linkText(linkTextEN));             
             langElem.click();
             try {
                Thread.sleep(5000);                 //wait for page to load
             } catch(InterruptedException ex) {
                Thread.currentThread().interrupt();
             }              
             String pageTitle = driver.getTitle();
             if (!titleTextEN.contentEquals(pageTitle))
                 throw new IllegalStateException("Expected Title: "+ titleTextEN + "\nFound Title: " + pageTitle);
        }catch (Exception e ){
            System.out.println("Problems changing language to English");
            e.printStackTrace();
            throw e;
        }     
    }
    /**
     * Click the Highlights page
     */
    public HighlightsPage goToHighlightsPage(){
        WebElement highligthLink = driver.findElement(By.id(highlightId));
        highligthLink.click();
        return new HighlightsPage(driver);
    }
    
    /**
     * Return the terms and conditions page
     * @return terms and conditions page
     */
    public TermsAndConditionsPage getTermsAndConditionsPage(){
        driver.findElement(By.linkText(termsandconditionstitleTextPT)).click();
        try {
            Thread.sleep(5000);                 //wait for page to load
        } catch(InterruptedException ex) {
            Thread.currentThread().interrupt();
        }          
        return new TermsAndConditionsPage(driver);
    }
    
    /**
     * Return the image source from sponsor
     * @return the image
     */
    public boolean isSponsorGovImageCorrect (){
        WebElement image = driver.findElement(By.cssSelector("img[usemap=\"#logomap\"]"));
        String src = image.getAttribute("src");
        return src.endsWith("mec-web.png");
    }
    /**
     * Click the Highlights page
     */
    public AdvancedPage goToAdvancedPage(){
        try{
            WebElement advancedLink = driver.findElement(By.id("pesquisa-avancada"));
            advancedLink.click();
        }
        catch(NoSuchElementException e){
                  System.out.println("Could not find the pesquisa-avancada element");
                  throw e;
        } catch (Exception e){
            System.out.println("Unexpected Error. Unable to go to AdvancedPage");
            throw e;
        }                  
        return new AdvancedPage(driver);
    }
    
    /**
     * Click the Highlights page
     */
    public Arcproxyinspection arcProxy(Boolean isPreProd){
        
        return new Arcproxyinspection(driver,isPreProd);
    }
    
    /**
     * Make a search by URL and inspect if the hostname is not case-sensitive
     * for instance, fccn.pt and fccn.PT are the same
     * @param query
     * @return
     */
    public boolean searchbyURL(String query,String queryPT){
        
        this.url = driver.getCurrentUrl();
        
        String date="26 Nov"; // historical link selected
        String title = getTitlesearchbyURL(query,date);
        String title_cap=getTitlesearchbyURL(queryPT,date);
        if (title==null){
            throw new IllegalStateException("Title is null");
        }
        else if (!title.equals(title_cap)){
            System.out.println("Title no caps: " + title);
            System.out.println("Title with caps: " + title_cap);
            return false;
        }
        System.out.println("Passed Title no caps: " + title);
        System.out.println("Passed Title with caps: " + title_cap);
        return true;
    
}

/**
 * Get the title of a page 
 * query url
 * date on format "10 Dez"
 * @return title of the webapge on 10 Dez
 */
public String getTitlesearchbyURL(String query,String date){
        driver.get(this.url);
        WebElement txtSearchElement = (new WebDriverWait(driver, 25)) /* Wait Up to 25 seconds should throw RunTimeExcpetion*/
            .until(ExpectedConditions.presenceOfElementLocated(By.id("txtSearch")));           
        txtSearchElement.clear();
        txtSearchElement.sendKeys(query);
        WebElement btnSubmitElement = (new WebDriverWait(driver, 25)) /* Wait Up to 25 seconds should throw RunTimeExcpetion*/
            .until(ExpectedConditions.presenceOfElementLocated(By.id("btnSubmit")));   
        btnSubmitElement.click();
        WebElement dateAnchorElement = (new WebDriverWait(driver, 25)) /* Wait Up to 25 seconds should throw RunTimeExcpetion*/
            .until(ExpectedConditions.presenceOfElementLocated(By.linkText(date)));   
        dateAnchorElement.click();             
        if((new WebDriverWait(driver, 25)) /* Wait Up to 25 seconds should throw RunTimeExcpetion*/
            .until(ExpectedConditions.titleContains("Arquivo.pt"))){
            return driver.getTitle();
        }
        else{
            System.out.println("Unexpected title: " + driver.getTitle());
            throw new IllegalStateException("Unexpected title: " + driver.getTitle());
        }
}

}
