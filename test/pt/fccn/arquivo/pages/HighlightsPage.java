/**
 * 
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

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * @author Nutchwax 
 *
 */
public class HighlightsPage {
    private final WebDriver driver;

    private static final String titleTextEN = "Examples of pages preserved by Arquivo.pt";
    private static final String titleTextPT = "Exemplos de páginas preservadas no Arquivo.pt &mdash; Sobre o Arquivo.pt";

	private static final String titleFirstPortuguesePagePT = "Home Page de Portugal / Portugal Home Page - preservado pelo Arquivo.pt";
	private static final String titleFirstPortuguesePageEN = "Home Page de Portugal / Portugal Home Page - preserved by Arquivo.pt";
	private static final String titleSmithsonianPT ="The Smithsonian Institution Home Page - preservado pelo Arquivo.pt";
	private static final String titleSmithsonianEN ="The Smithsonian Institution Home Page - preserved by Arquivo.pt";
	private static final String titleClixPT = "UEFA Euro 2004  - preservado pelo Arquivo.pt";
	private static final String titleClixEN = "UEFA Euro 2004  - preserved by Arquivo.pt";
	private static final String titleExpoPT = "EURO - O que é o euro ? - preservado pelo Arquivo.pt";
	private static final String titleExpoEN = "EURO - O que é o euro ? - preserved by Arquivo.pt";
	private static final String titlePublicoPT = "PUBLICO - preservado pelo Arquivo.pt";
	private static final String titlePublicoEN = "PUBLICO - preserved by Arquivo.pt";
	private static final String titleSapoPT = "Projecto HidroNet - Links 1 - preservado pelo Arquivo.pt";
	private static final String titleSapoEN = "Projecto HidroNet - Links 1 - preserved by Arquivo.pt";
	private static final String titleTimPT = "Tim Berners-Lee - preservado pelo Arquivo.pt";
	private static final String titleTimEN = "Tim Berners-Lee - preserved by Arquivo.pt";
	private static final String titlePresidenciaisPT ="portuguese presidentials of 2001 - preservado pelo Arquivo.pt";
	private static final String titlePresidenciaisEN ="portuguese presidentials of 2001 - preserved by Arquivo.pt";
	private static final String titleEuroPT ="Futebol Internacional - Notícias do dia - preservado pelo Arquivo.pt";
	private static final String titleEuroEN ="Futebol Internacional - Notícias do dia - preserved by Arquivo.pt";
	private static final String titleEloyEN ="Eloy Rodrigues - HOME PAGE - preservado pelo Arquivo.pt";
	private static final String titleEloyPT ="Eloy Rodrigues - HOME PAGE - preserved by Arquivo.pt";
	private static final String titlePortugalTelecomPT ="P O R T U G A L T E L E C O M - preservado pelo Arquivo.pt";
	private static final String titlePortugalTelecomEN ="P O R T U G A L T E L E C O M - preserved by Arquivo.pt";
	private static final String titleMinistrePT ="Ministère de l'Education Nationale - preservado pelo Arquivo.pt";
	private static final String titleMinistreEN ="Ministère de l'Education Nationale - preserved by Arquivo.pt";
	private static final String titleSicPT ="SIC Online - Cavaco Silva em Bragança - preservado pelo Arquivo.pt";
	private static final String titleSicEN ="SIC Online - Cavaco Silva em Bragança - preserved by Arquivo.pt";
	private static final String titleCimiterioPT ="Visita ao Cemitério - preservado pelo Arquivo.pt";
	private static final String titleCimiterioEN ="Visita ao Cemitério - preserved by Arquivo.pt";
	private static final String titleSapoDesportoPT ="Sapo Infordesporto - preservado pelo Arquivo.pt";
	private static final String titleSapoDesportoEN ="Sapo Infordesporto - preserved by Arquivo.pt";
	private static final String titleNimasPT ="NIMAS - FITAS EM CARTAZ - preservado pelo Arquivo.pt";
	private static final String titleNimasEN ="NIMAS - FITAS EM CARTAZ - preserved by Arquivo.pt";
	private static final String titleXLDBPT ="Referencias - preservado pelo Arquivo.pt";
	private static final String titleXLDBEN ="Referencias - preserved by Arquivo.pt";
	private static final String titleBosniaPT ="BOSNIA NOW - preservado pelo Arquivo.pt";
	private static final String titleBosniaEN ="BOSNIA NOW - preserved by Arquivo.pt";
	private static final String titleFitasPT ="NIMAS - FITAS EM CARTAZ - preservado pelo Arquivo.pt";
	private static final String titleFitasEN ="NIMAS - FITAS EM CARTAZ - preserved by Arquivo.pt";
	private static final String titleBeachcamPT = "BeachCam - Praia do Guincho - preservado pelo Arquivo.pt";
	private static final String titleBeachcamEN = "BeachCam - Praia do Guincho - preserved by Arquivo.pt";
    
    private static final String h1Title= "First Portuguese web page (1996)";
    
    public HighlightsPage(WebDriver driver) {
        this.driver = driver;
        // Check that we're on the right page.
        String pageTitle= driver.getTitle();

        if ((titleTextEN.contains(pageTitle))){
            throw new IllegalStateException("This is not the " + this.getClass().getName()+ "\n Title of current page: " + pageTitle+"\nExpected title: "+titleTextEN);
        }
    }
    
    /**
     * Verify if page has correct text
     * @return true if page contains the expected text
     */
    public boolean isPageCorrect() {
    	try{
			try {
	            Thread.sleep(5000);                 //wait for page to load
	        } catch(InterruptedException ex) {
	            Thread.currentThread().interrupt();
	        }      		
    		System.out.println("Current Page: " + driver.getCurrentUrl());
    		String foundH1Title = driver.findElement(By.xpath("//*[@id=\"parent-fieldname-text\"]/ul/li[1]/a")).getText();
    		System.out.println("Found Title: " + foundH1Title);
    		return (h1Title.compareTo(foundH1Title) == 0);
    	}catch(Exception e){
    		System.out.println("Error comparing h1 title");
    		e.printStackTrace();
    		return false;
    	}
        
    }
    /**
     * Run through the links of highlights
     * @return true if all links from 
     */
    public boolean goThroughHighlights()  {
    	try{
			try {
	            Thread.sleep(5000);                 //wait for page to load
	        } catch(InterruptedException ex) {
	            Thread.currentThread().interrupt();
	        }    		
	        List <WebElement> listOfHighlights = driver.findElements(By.id("boxes"));
	        for (WebElement element : listOfHighlights) {
	        	element.click();
	        }
	        return true;
	    }catch(Exception e){
	    	System.out.println("Some Error navigating through the Highlights");
	    	return false;
	    }
    }
    
    
    /**
     * 
     * Navigates across each URL at http://sobre.arquivo.pt/about-the-archive/example-pages-in-the-portuguese-web-archive
     * @return true if all of links are not broken in the main page
     */
    public boolean checkLinkHighligths(){
       try{	
	       List<WebElement> linkList= driver.findElements(By.tagName("a"));
	   	   int statuscode=0;
	       for(int i=0 ; i<linkList.size() ; i++)
	       {
	       	if(linkList.get(i).getAttribute("href") != null)
	       	  {
	       		if (linkList.get(i).getAttribute("href").contains("/wayback")){
					statuscode=getResponseCode(linkList.get(i).getAttribute("href"));
					if (statuscode!= 200){
						return false;
					}
	       		}
	       	  }	
	       }
	       return true;
	    }catch (Exception e){ 
	    	System.out.println("Error finding one of Highlights!");
	    	e.printStackTrace();
	    	return false;
	    }   
    }
    
    /**
     * @return true if all of the links are correct
     */
    public boolean checkHighligthsPageLinks(){
    	
    	String title=null;	
    	int i =0;
    	List<String> aux = new ArrayList<String>();
    	try{
    		aux = getHiglightsUrl();
    	}catch (Exception e){
    		System.out.println("Some Error getting List of Highlight URls");
    		e.printStackTrace()
    	}
    	
       for(i=0 ; i<aux.size() ; i++)
       {
    	   title = getIdTitle(aux.get(i));
    	   
       	 	if (!inspectTitlesMatches(title)){
       	 		System.out.print("\n\nunexpected title: "+title);
       	 		return false;
       	 	}
       }
       return true;
    }
    
    /**
     * @return a list with all of the current url's 
     */
    private List<String> getHiglightsUrl() throws Exception{	
    	List<WebElement> linkList= driver.findElements(By.className("external-link"));
    	List<String> highlights = new ArrayList<String>();
    	for( int i =0; i< linkList.size();i++)
    		highlights.add(linkList.get(i).getAttribute("href"));
		return highlights;    	
    }
    /**
	 * @param Url - each highlights url
	 * @return Title of the webpage
	 */
	private String  getIdTitle (String Url){
		WebDriverWait wait = new WebDriverWait(driver, 20);


		driver.get(Url);
		//wait until title was loaded
		try {
            Thread.sleep(5000);                 //wait for page to load
        } catch(InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
		wait.until(ExpectedConditions.urlContains(Url));
		//wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("title")));
		return driver.getTitle();
	}

    /**
     * titlepage: current driven title
     * @return true if matches any title
     */
    public boolean inspectTitlesMatches(String titlepage){


    	if (titlePresidenciaisPT.contains(titlepage) || titlePresidenciaisEN.contains(titlepage)){
    		return true;
    	}
		    
		if (titleClixPT.equals(titlepage)|| titleClixEN.equals(titlepage)){
			return true;
		}
		
		if (titleExpoPT.equals(titlepage) ||titleExpoEN.equals(titlepage)){
			return true;
		}
		
		if (titleSmithsonianPT.equals(titlepage) || titleSmithsonianEN.equals(titlepage)){
			return true;
		}
		if (titlePublicoEN.equals(titlepage)||titlePublicoPT.equals(titlepage)){
			return true;
		}
		
		if (titleFirstPortuguesePageEN.contains(titlepage) || titleFirstPortuguesePagePT.contains(titlepage)){
			return true;
		}
		
		if (titleSapoEN.equals(titlepage) || titleSapoPT.equals(titlepage)){
			return true;
		}
		
		if (titleTimEN.equals(titlepage)||titleTimPT.equals(titlepage)){
			return true;
		}
		if (titleEuroEN.equals(titlepage) ||titleEuroPT.equals(titlepage)){
			return true;
		}
		if (titleEloyEN.equals(titlepage) ||titleEloyPT.equals(titlepage)){
			return true;
		}
		if (titleXLDBEN.equals(titlepage) || titleXLDBPT.equals(titlepage)){
			return true;
		}
		if (titleSapoDesportoEN.equals(titlepage) ||titleSapoDesportoPT.equals(titlepage)){
			return true;
		}
		if (titlePortugalTelecomEN.equals(titlepage)||titlePortugalTelecomPT.equals(titlepage)){
			return true;
		}
		if (titleMinistreEN.equals(titlepage) || titleMinistrePT.equals(titlepage)){
			return true;
		}
		if (titleCimiterioEN.equals(titlepage) || titleCimiterioPT.equals(titlepage)){
			return true;
		}
		if (titleNimasEN.equals(titlepage) || titleNimasPT.equals(titlepage)){
			return true;
		}
		if (titleSicEN.equals(titlepage) || titleSicPT.equals(titlepage)){
			return true;
		}
		if (titleBosniaEN.equals(titlepage) ||titleBosniaPT.equals(titlepage)){
			return true;
		}
		
		if (titleFitasEN.equals(titlepage)||titleFitasPT.equals(titlepage)){
			return true;
		}
		if (titleBeachcamEN.equals(titlepage) || titleBeachcamPT.equals(titlepage)){
			return true;
		}
		else{
			return false;
		}
    	
    	
    }
    
    /**
     * @param urlString
     * @return the statuscode from the page
     */
    public int getResponseCode(String urlString) {         
        URL u=null;
        HttpURLConnection huc=null;
		try {
			u = new URL(urlString);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
        try {
			huc = (HttpURLConnection) u.openConnection();
			huc.setRequestMethod("GET");  
	        huc.connect();  
	        return huc.getResponseCode();  
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;  
        
  } 
}
