/**
 * Copyright (C) 2016 Fernando Melo <fernando.melo@fccn.pt>
 * Copyright (C) 2016 SAW Group - FCCN <saw@asa.fccn.pt>
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
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.NoSuchElementException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import java.io.FileInputStream;
import java.util.Properties;
import java.io.File;
import java.io.InputStreamReader;
import java.io.FileOutputStream;  
import java.nio.channels.Channels;  
import java.nio.channels.ReadableByteChannel;  



/**
 * @author Fernando Melo
 * 
 */


public class ReplayTabletPage {
    private final WebDriver driver;
    private List<String> testURLs = new ArrayList<String>();
    private boolean isPreProd;
    private String baseURL;
    private String cgi_path;
    private String tableofVersionsURLExpected;
    private String logoURLPTExpected;
    private String searchURL;
    private static final String filenamePreProd = "pre_production_URL_testlist.txt";
    private static final String filenameProd = "production_URL_testlist.txt";
    private static final int waitingPeriod = 10000; //Time to load the Web page in miliseconds
    private static final String helpURLPTExpected = "http://sobre.arquivo.pt/ajuda";
    private static final String emailHrefPTExpected = "mailto:?subject=Arquivo.pt%20pesquise%20p%C3%A1ginas%20do%20passado%20&body=Ol%C3%A1,%0AEncontrei%20este%20website%20que%20poder%C3%A1%20achar%20interessante:%20[sub]";
    private static final String emailOnClickExpected = "this.href = this.href.replace('[sub]',window.location)";
    private static final String tableofVersionsPTExpected = "Tabela de versões";
    private static final String activeItem ="active";
    private static final String activeDay = "active-day";
    private String serverName  ="";
    private BufferedReader br;
    private String pageUrl =""; 
    private String [] tokens;
    private Properties prop;
    private BufferedReader inputPt = null;
    private BufferedReader inputEn = null;
    private String baseScreenshotURL;

    public ReplayTabletPage(WebDriver driver, boolean isPreProd) {
        this.driver = driver;
        //driver.manage().window().setSize(new Dimension(1280, 768)); 
        br = null;
        pageUrl = driver.getCurrentUrl();
        tokens = pageUrl.split("/");
        serverName = "http://"+tokens[2]+"/";
        baseScreenshotURL = serverName + "screenshot/?url=";
        logoURLPTExpected = searchURL+"/?l=pt";
        prop = new Properties();
    
        try {
          

            inputPt = new BufferedReader(new InputStreamReader(new FileInputStream("pt.properties"), "UTF8"));
            inputEn = new BufferedReader(new InputStreamReader(new FileInputStream("en.properties"), "UTF8"));
            prop.load(inputPt);
            // start with properties in PT
          String currentURL;
          if(isPreProd)
            br = new BufferedReader(new FileReader(filenamePreProd));
          else
            br = new BufferedReader(new FileReader(filenameProd)); 

          while ((currentURL = br.readLine()) != null) {
            testURLs.add(currentURL);
          }

        } catch (IOException e) {
          e.printStackTrace();
        } finally {
          try {
            if (br != null)br.close();
          } catch (IOException e) {
            e.printStackTrace();
          }
        }
    }

    /**
     * Do the Replay Tests for our set of URLS 
     */
    public boolean inspectURLs(String language){
      System.out.println("Inspecting URLS language: " + language);
      if(language.equals("EN")){
        //switch properties to english
        try{
          prop.load(inputEn);
        }catch(IOException e){
          System.out.println("Error Loading English Properties");
          return false;
        }  
      }

      for(String currentURL:testURLs){
        System.out.println("Current URL: " + currentURL);
        try {
			goToCurrentURL(currentURL);
		} catch (Exception e) {
			return false;
		}
        System.out.println("Went to the URL");
        try {
			switchLanguage(language);
		} catch (Exception e) {
			return false;
		} // Can be optimized to only change TO PT on the first URL, and all others have to be in PT too
        System.out.println("Switched the language!");
        if( /*!screenshotOk(currentURL) ||*/ !printOk(currentURL) ||
           !facebookOk(currentURL) || 
           !twitterOk(currentURL) ||
           !emailOk(currentURL) ||
           !logoOk(currentURL))
        {
          return false;
        }
      }
      return true;
    }


    /**
     * Check if the screenshot URL and title are correct
     * TODO:: Update this new version popup
     */


    public boolean screenshotOk(String currentURL){
      try{
        String screenshotURL = driver.findElement(By.xpath("//a[@id=\"a_screenshot\"]")).getAttribute("href");
        String screenshotTitle = driver.findElement(By.xpath("//a[@id=\"a_screenshot\"]")).getAttribute("title");
        
        String expectedscreenshotURL= baseScreenshotURL + encodeURIComponent(serverName + "noFrame/replay/" + currentURL);
        String expectedscreenshotTitle=prop.getProperty("screenTitle");

        if(screenshotURL.equals(expectedscreenshotURL) && 
           screenshotTitle.equals(expectedscreenshotTitle)){
          //if URL and Title are ok lets download the screenshot
            downloadFileFromURLUsingNIO(  "./screenshot.png",  screenshotURL);  
            if(checkFileSize("./screenshot.png")) return true;
              return false;
        }
        else{
          System.out.println("Found this Screenshot URL: " + screenshotURL);
          System.out.println("Expected this Screenshot URL: " + expectedscreenshotURL );
          System.out.println("Found this Screenshot Title: " + screenshotTitle);
          System.out.println("Expected this Screenshot Title: " + expectedscreenshotTitle);
          return false;
        }     

      }catch(NoSuchElementException e){
          System.out.println("Could not find the screenshot element");
          return false;
      }catch (Exception e){
        System.out.println("Some problem downloading the screenshot");
        return false;
      } 
    }   

    /**
     * Check if the print href and title are correct
     */
    public boolean printOk(String currentURL){
      try{
        String printHref = driver.findElement(By.xpath("//a[@id=\"a_print\"]")).getAttribute("href");
        String printTitle = driver.findElement(By.xpath("//a[@id=\"a_print\"]")).getAttribute("title");
        
        String expectedprintHref= "javascript:getImageToPrint(\""+ encodeURIComponent(serverName + "noFrame/replay/" + currentURL)+ "\")";
        String expectedprintTitle=prop.getProperty("printTitle");

        if(printHref.equals(expectedprintHref) && 
           printTitle.equals(expectedprintTitle)){
          return true;
        }
        else{
          System.out.println("Found this Print Href: " + printHref);
          System.out.println("Expected this Print Href: " + expectedprintHref );
          System.out.println("Found this Print Title: " + printTitle);
          System.out.println("Expected this Print Title: " + expectedprintTitle);
          return false;
        }     

      }catch(NoSuchElementException e){
          System.out.println("Could not find the print element");
          return false;
      }catch (Exception e){
        System.out.println("Error in the PrintOk");
        System.out.println("Should not have reached here");
        return false;
      } 
    }  

    /**
     * Check if the Facebook class and title are correct
     */
    public boolean facebookOk(String currentURL){
      try{
        String faceClass = driver.findElement(By.xpath("//li[@class=\"facebook\"]/a")).getAttribute("class");
        String faceTitle = driver.findElement(By.xpath("//li[@class=\"facebook\"]/a")).getAttribute("title");
        
        String expectedfaceClass= "addthis_button_facebook";
        String expectedfaceTitle=prop.getProperty("faceTitle");

        if(faceClass.startsWith(expectedfaceClass) && 
           faceTitle.equals(expectedfaceTitle)){
          return true;
        }
        else{
          System.out.println("Found this Facebook class: " + faceClass);
          System.out.println("Expected this Facebook Class: " + expectedfaceClass );
          System.out.println("Found this Facebook Title: " + faceTitle);
          System.out.println("Expected this Facebook Title: " + expectedfaceTitle);
          return false;
        }     

      }catch(NoSuchElementException e){
          System.out.println("Could not find the facebook element");
          return false;
      }catch (Exception e){
        System.out.println("Error Testing Facebook");
        System.out.println("Should not have reached here");

        return false;
      } 
    }  

    /**
     * Check if the Facebook class and title are correct
     */
    public boolean twitterOk(String currentURL){
      try{
        String twitterClass = driver.findElement(By.xpath("//li[@class=\"twitter\"]/a")).getAttribute("class");
        String twitterTitle = driver.findElement(By.xpath("//li[@class=\"twitter\"]/a")).getAttribute("title");
        
        String expectedtwitterClass= "addthis_button_twitter";
        String expectedtwitterTitle=prop.getProperty("twitterTitle");

        if(twitterClass.startsWith(expectedtwitterClass) && 
           twitterTitle.equals(expectedtwitterTitle)){
          return true;
        }
        else{
          System.out.println("Found this Twitter class: " + twitterClass);
          System.out.println("Expected this Twitter Class: " + expectedtwitterClass );
          System.out.println("Found this Twitter Title: " + twitterTitle);
          System.out.println("Expected this Twitter Title: " + expectedtwitterTitle);
          return false;
        }     

      }catch(NoSuchElementException e){
          System.out.println("Could not find the twitter element");
          return false;
      }catch (Exception e){
        System.out.println("Error Testing Twitter");
        System.out.println("Should not have reached here");
        return false;
      } 
    } 

    /**
     * Check if the Email title, onclick and href are correct
     */
    public boolean emailOk(String currentURL){
      try{
        String emailHref = driver.findElement(By.xpath("//li[@id=\"liEmail\"]/a")).getAttribute("href");
        String emailonClick = driver.findElement(By.xpath("//li[@id=\"liEmail\"]/a")).getAttribute("onclick");
        String emailTitle = driver.findElement(By.xpath("//li[@id=\"liEmail\"]/a")).getAttribute("title");

        String expectedemailHref = "mailto:?subject="+prop.getProperty("emailMessage")+"[sub]";
        String expectedemailonClick = "this.href = this.href.replace('[sub]',window.location)";
        String expectedemailTitle = prop.getProperty("mailTitle");

        if(emailHref.equals(expectedemailHref)  && 
           emailonClick.startsWith(expectedemailonClick) &&
           emailTitle.equals(expectedemailTitle)){
          return true;
        }
        else{
          System.out.println("Found this Email href: " + emailHref);
          System.out.println("Expected this Email href: " + expectedemailHref );
          System.out.println("Found this Email onclick: " + emailonClick );
          System.out.println("Found this Email title: " + emailTitle);
          System.out.println("Expected this Email title: " + expectedemailTitle );          
          return false;
        }     

      }catch(NoSuchElementException e){
          System.out.println("Could not find email anchor");
          return false;
      }catch (Exception e){
        System.out.println("Error testing email");        
        System.out.println("Should not have reached here");

        return false;
      } 
    }   

    /**
     * Check if the Logo exists and href is correct
     */
    public boolean logoOk(String currentURL){
      try{

        String anchorHref = driver.findElement(By.xpath("//div[@id=\"logodiv\"]/a[2]")).getAttribute("href");
        String logoAlt = driver.findElement(By.xpath("//div[@id=\"logodiv\"]/a[2]/img")).getAttribute("alt");
        String logoSrc = driver.findElement(By.xpath("//div[@id=\"logodiv\"]/a[2]/img")).getAttribute("src");

        String expectedanchorHref = serverName+"?l="+ prop.getProperty("lang") ;
        String expectedlogoAlt = "logo";
        String expectedlogoSrc = serverName+"wayback/static/resources/img/AWP_200_100.png";

        if(expectedanchorHref.equals(anchorHref) && expectedlogoAlt.equals(logoAlt) && expectedlogoSrc.equals(logoSrc)) {
          return true;
        }
        else{
          System.out.println("Found this Anchor href: " + anchorHref);
          System.out.println("Expected this Anchor href: " + expectedanchorHref );
          System.out.println("Found this Logo Alt: " + logoAlt);
          System.out.println("Expected this Logo Alt: " + expectedlogoAlt);
          System.out.println("Found This Logo Src: " + logoSrc);
          System.out.println("Expected this Logo Src: " + expectedlogoSrc);          
          return false;
        }     

      }catch(NoSuchElementException e){
          System.out.println("Could not find the Logo Anchor or the Logo Image");
          return false;
      }catch (Exception e){
        System.out.println("Error Checking Logo");
        System.out.println("Should not have reached here");
        return false;
      } 
    } 



    /**
     * Jump to the current URL
     * wait some time to load the Webpage
     * @throws Exception 
     */
    public void goToCurrentURL(String currentURL) throws Exception{
      try{
        driver.get(serverName+"wayback/"+currentURL);
        try {
            Thread.sleep(waitingPeriod);                 //wait for page to load
        } catch(InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
      }catch(Exception e ){
        System.out.println("Some error going to the current URL");
        e.printStackTrace();
        throw new Exception( e );
      }      
    }

    public String truncateURL(String url){
      if(url.startsWith("https://")){
          url = url.substring(8,url.length());
        }else if (url.startsWith("http://")){
          url = url.substring(7,url.length());
        }             
      if (url.length() > 40){
        return url.substring(0,26) + "..." + url.substring((url.length() - 11),url.length()) ;
      }
      else
        return url;
    }

    /**
    * Changes the Language of the Page, to the value given in lang string (PT or EN)
     * @throws Exception 
    */
    public void switchLanguage(String lang) throws Exception{
      try{
          try {
            Thread.sleep(10000);  //wait for page to load
          } catch(InterruptedException ex) {
            Thread.currentThread().interrupt();
          }          
        System.out.println("SwitchingLang: " + driver.getCurrentUrl());        
        if(driver.findElement(By.xpath("//a[@id=\"changeLanguage\"]")).getText().equals(lang)){
          System.out.println("Going to change the language.");
          driver.findElement(By.id("changeLanguage")).click(); //change the language
          System.out.println("Clicked on the changeLanguage id");
          try {
            Thread.sleep(waitingPeriod);  //wait for page to load
          } catch(InterruptedException ex) {
            Thread.currentThread().interrupt();
          }  
        } //else You are already in the desired language
      }catch (Exception e){
        System.out.println("Error switching language to: " + lang);
        e.printStackTrace();
        throw new Exception ( e );
      }
    }


 private static void downloadFileFromURLUsingNIO(String fileName,String fileUrl) throws IOException {  
    System.out.println("Download Starting");
    URL url = new URL(fileUrl);  
    ReadableByteChannel rbc = Channels.newChannel(url.openStream());  
    FileOutputStream fOutStream = new FileOutputStream(fileName);  
    fOutStream.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);  
    fOutStream.close();  
    rbc.close(); 
    System.out.println("Download Successful");
 }

 private static boolean checkFileSize(String pathToFile){

    File file =new File(pathToFile);
    
    if(file.exists()){
      
      double bytes = file.length();
      double kilobytes = (bytes / 1024);
      
      System.out.println("kilobytes : " + kilobytes);
      if(kilobytes > 200) return true;
      else{
        System.out.println("File too small to Be Ok Less or Equal to 200Kb");
        return false;
      }  

    }else{
       System.out.println("File does not exist!");
       return false;
    }

 }  



  /**
   * Encodes the passed String as UTF-8 using an algorithm that's compatible
   * with JavaScript's <code>encodeURIComponent</code> function. Returns
   * <code>null</code> if the String is <code>null</code>.
   * 
   * @param s The String to be encoded
   * @return the encoded String
   */
  public static String encodeURIComponent(String s)
  {
    String result = null;

    try
    {
      result = URLEncoder.encode(s, "UTF-8")
                         .replaceAll("\\+", "%20")
                         .replaceAll("\\%21", "!")
                         .replaceAll("\\%27", "'")
                         .replaceAll("\\%28", "(")
                         .replaceAll("\\%29", ")")
                         .replaceAll("\\%7E", "~");
    }

    // This exception should never occur.
    catch (UnsupportedEncodingException e)
    {
      result = s;
    }

    return result;
  }  
    
}
