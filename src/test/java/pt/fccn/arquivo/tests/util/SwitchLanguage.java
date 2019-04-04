package pt.fccn.arquivo.tests.util;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Moved from IndexPage.java
 *
 * @author ibranco
 *
 */
public class SwitchLanguage {

    /**
     * Change to the English version
     */
     public static void switchEnglishLanguage(WebDriver driver){ //*[@id="changeLanguage"]
     	String xpathEnglishVersion = "//*[@id=\"changeLanguage\"]";
       	if( driver.findElement( By.xpath( xpathEnglishVersion ) ).getText( ).equals( "English" ) ) {
       		System.out.println( "Change language to English" );
       		driver.findElement( By.xpath( xpathEnglishVersion ) ).click( );
       		sleepThread( );
       	}
     }

     private static void sleepThread( ) {
 		try {
 			Thread.sleep( 6000 );
 		} catch ( InterruptedException e ) {
 			e.printStackTrace( );
 		}
 	}
}
