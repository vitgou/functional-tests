package pt.fccn.sobre.arquivo.pages;

import java.io.FileNotFoundException;
import org.openqa.selenium.WebDriver;

public class SiteMapPage {

	WebDriver driver;
	
	public SiteMapPage( WebDriver driver ) throws FileNotFoundException{
		this.driver = driver;
		
	}
	
	public boolean checkSiteMap( String language ) {
		System.out.println( "[checkSiteMap]" );
		
		
		return true;
		
	}
	
}
