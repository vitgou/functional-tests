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
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.Dimension;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.io.BufferedInputStream;  
import java.io.File;  
import java.io.FileOutputStream;  
import java.io.IOException;  
import java.net.MalformedURLException;  
import java.net.URL;  
import java.nio.channels.Channels;  
import java.nio.channels.ReadableByteChannel;  



/**
 * @author Fernando Melo
 * 
 */


public class ReplayPage {
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

    public ReplayPage(WebDriver driver, boolean isPreProd) {
        this.driver = driver;
        driver.manage().window().setSize(new Dimension(1280, 768));
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

/*
// This is just a test to retrieve array of properties
            String [] months = prop.getProperty("months").split("#");
            System.out.println(months[2]);

            prop.load(inputEn);
            months = prop.getProperty("months").split("#");
            System.out.println(months[2]);
*/
           

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
        goToCurrentURL(currentURL);
        switchLanguage(language); // Can be optimized to only change TO PT on the first URL, and all others have to be in PT too

        if(!replayBarURLsOk(currentURL) ||  
           /*!screenshotOk(currentURL) ||*/ !printOk(currentURL) ||
           !facebookOk(currentURL) || !twitterOk(currentURL) ||
           !emailOk(currentURL) ||
           !tableOfVersionsOk(currentURL) || !logoOk(currentURL) ||
           !checkLeftMenu(currentURL))
        {
          return false;
        }
      }
      return true;
    }

    /**
     * Check if both the expanded URL that appears on the replayBar is correct
     */
    public boolean replayBarURLsOk(String currentURL){
      String maximzedURL ="";
      String urlWithoutDate = "";
      try{        
        urlWithoutDate = currentURL.substring(15);
        urlWithoutDate =truncateURL(urlWithoutDate);
        maximzedURL = driver.findElement(By.xpath("//p[@id=\"update1\"]")).getText();
        System.out.println("URL: "+ maximzedURL);

        if(maximzedURL.equals(urlWithoutDate)){
          return true;
        }
        else{
          System.out.println("Expected URL: "+ urlWithoutDate);
          System.out.println("Found URL: "+ maximzedURL);
          return false;
        }
      }catch(NoSuchElementException e){
          System.out.println("Expected URL: "+ urlWithoutDate);
          System.out.println("Found maximized URL: "+ maximzedURL);
          // Could not find a Version in the specified date
          return false;
      }catch (Exception e){
        //should never enter here print stack trace if so
        e.printStackTrace();
        return false;
      }
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
        
        String expectedfaceClass= "addthis_button_facebook at300b";
        String expectedfaceTitle=prop.getProperty("faceTitle");

        if(faceClass.equals(expectedfaceClass) && 
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
        
        String expectedtwitterClass= "addthis_button_twitter at300b";
        String expectedtwitterTitle=prop.getProperty("twitterTitle");

        if(twitterClass.equals(expectedtwitterClass) && 
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
        System.out.println("Should not have reached here");
        return false;
      } 
    } 






    /**
     * Check Left Menu that contains the list of versions for the URL
     */
    public boolean checkLeftMenu(String currentURL){
      try{ 
        String urlNoDate = currentURL.substring(15);    
        String timestamp = currentURL.substring(0,14); //complete timestamp in the 14 digit format 
        String year = currentURL.substring(0,4);
        String monthstr = currentURL.substring(4,6);
        int monthInt = Integer.parseInt(monthstr);
        String [] months = prop.getProperty("months").split("#");
        String monthStr = months[(monthInt-1)];
        String daystr = currentURL.substring(6,8);
        int day = Integer.parseInt(daystr);
        String hours = currentURL.substring(8,10);
        String minutes = currentURL.substring(10,12);  

        System.out.println("Year: " + year);
        System.out.println("Month: " + monthStr);
        System.out.println("Day: " + day);
        System.out.println("Hours: " + hours);
        System.out.println("Minutes: " + minutes);     
        
        // Check the year in the left menu and check if it is open
        String yearClass = driver.findElement(By.xpath("//a[@id=\""+year+"\"]")).getAttribute("class");
        String yearText = driver.findElement(By.xpath("//a[@id=\""+year+"\"]")).getText();
        // Check the month in the left menu and check if it is open
        String monthClass = driver.findElement(By.xpath("//a[@id=\""+year+"_"+monthstr+"\"]")).getAttribute("class");
        String monthText = driver.findElement(By.xpath("//a[@id=\""+year+"_"+monthstr+"\"]")).getText();
       // Check the day in the left menu and check if it is open
        String dayClass = driver.findElement(By.xpath("//a[@id=\""+year+"_"+monthstr+"_"+day+"\"]")).getAttribute("class");
        String dayText = driver.findElement(By.xpath("//a[@id=\""+year+"_"+monthstr+"_"+day+"\"]")).getText();
         //Check the time of the day Text and if the link is correct
        String hoursMinutesURL = driver.findElement(By.xpath("//a[@id=\"a_"+timestamp+"\"]")).getAttribute("href");
        hoursMinutesURL = hoursMinutesURL.replaceAll(",%20", ", ");
        String hoursClass = driver.findElement(By.xpath("//a[@id=\"a_"+timestamp+"\"]")).getAttribute("class");
        String hoursMinutesText = driver.findElement(By.xpath("//a[@id=\"a_"+timestamp+"\"]")).getText(); 
        String expectedURL = "javascript:jumpToVersion('"+urlNoDate+"', '"+timestamp+"')";       

       if(yearText.equals(year) && yearClass.equals(activeItem) &&
           monthText.equals(monthStr) && monthClass.equals(activeItem) &&
           dayText.equals(""+day) && dayClass.equals(activeItem) &&
           hoursMinutesText.equals(hours+":"+minutes) && 
           hoursClass.equals(activeDay)&&
           hoursMinutesURL.equals(expectedURL)){
          return true;
        }
        else{
          System.out.println("Found this Year text: " + yearText);
          System.out.println("Expected this Year text: " + year);
          System.out.println("The year has the class " +yearClass);
          System.out.println("Expected year class="+activeItem);
          System.out.println("Found this Month text: "+ monthText);
          System.out.println("Expected this Month text: "+ monthStr);
          System.out.println("Found this Month class: " + monthClass);
          System.out.println("Expected Month class: "+activeItem);
          System.out.println("Found this day Text: "+ dayText);
          System.out.println("Expected this day Text: "+day);
          System.out.println("Found this hours and minutes Text: "+ hoursMinutesText);
          System.out.println("Expected this hours and minutes Text: "+ hours +":"+minutes);
          System.out.println("Found this versionURL: "+ hoursMinutesURL);
          System.out.println("Expected this versionURL: "+ expectedURL);

          return false;
        }     

      }catch(NoSuchElementException e){
          System.out.println("Could not find at least one of the following elements: year, year_monthstr, year_monthstr_daystr, or a_timestamp");
          return false;
      }catch (Exception e){
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
        System.out.println("Should not have reached here");
        return false;
      } 
    }   


    /**
     * Check if the Table of Versions title and href are correct
     */
    public boolean tableOfVersionsOk(String currentURL){
      try{
        String tableOfVersionsHref = driver.findElement(By.xpath("//a[@id=\"versionsTable\"]")).getAttribute("href");
        String tableOfVersionsTitle = driver.findElement(By.xpath("//a[@id=\"versionsTable\"]")).getAttribute("title");
        
        String urlNoDate = currentURL.substring(15);

        String expectedtableOfVersionsHref = serverName+"search.jsp?l="+ prop.getProperty("lang")+"&query="+ urlNoDate+"&btnSubmit=Pesquisar";
        String expectedtableOfVersionsTitle = prop.getProperty("versionsStoredTable");


        if(expectedtableOfVersionsHref.equals(tableOfVersionsHref) && expectedtableOfVersionsTitle.equals(tableOfVersionsTitle)) {
          return true;
        }
        else{
          System.out.println("Found this Table of Versions href: " + tableOfVersionsHref);
          System.out.println("Expected this Table of Versions href: " + expectedtableOfVersionsHref );
          System.out.println("Found this Table of Versions title: " + tableOfVersionsTitle);
          System.out.println("Expected this Table of Versions title: " + expectedtableOfVersionsTitle );          
          return false;
        }     

      }catch(NoSuchElementException e){
          System.out.println("Could not find the Table of Versions Anchor");
          return false;
      }catch (Exception e){
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
        System.out.println("Should not have reached here");
        return false;
      } 
    } 



    /**
     * Jump to the current URL
     * wait some time to load the Webpage
     */
    public void goToCurrentURL(String currentURL){
      driver.get(serverName+"wayback/"+currentURL);
      try {
          Thread.sleep(waitingPeriod);                 //wait for page to load
      } catch(InterruptedException ex) {
          Thread.currentThread().interrupt();
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
    */
    public void switchLanguage(String lang){

      if(driver.findElement(By.xpath("//a[@id=\"changeLanguage\"]")).getText().equals(lang)){
        driver.findElement(By.id("changeLanguage")).click(); //change the language
        try {
          Thread.sleep(waitingPeriod);  //wait for page to load
        } catch(InterruptedException ex) {
          Thread.currentThread().interrupt();
        }  
      } //else You are already in the desired language
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
