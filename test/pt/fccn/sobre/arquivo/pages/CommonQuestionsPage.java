package pt.fccn.sobre.arquivo.pages;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CommonQuestionsPage {
	
	WebDriver driver;
	List< String > CommonQuestionsPT;
	List< String > CommonQuestionsEN;
	private final String dir = "sobreTestsFiles";
	private final int timeout = 50;
	
	public CommonQuestionsPage( WebDriver driver ) throws FileNotFoundException{
		this.driver = driver;
		CommonQuestionsPT = new ArrayList< String >( );
		CommonQuestionsEN = new ArrayList< String >( );
		if( !loadQuestions( "CommonQuestions_pt.txt" , "pt" ) ) 
			throw new FileNotFoundException( );
		
		if( !loadQuestions( "CommonQuestions_en.txt" , "en" ) )
			throw new FileNotFoundException( );
		
	}
	
	
	private boolean loadQuestions( String filename , String language ) {
		Scanner s;
		try {
			String line;

		    InputStream fis = new FileInputStream( dir.concat( File.separator ).concat( filename ) );
		    InputStreamReader isr = new InputStreamReader(fis, Charset.forName("UTF-8"));
		    BufferedReader br = new BufferedReader(isr);
		    while ((line = br.readLine()) != null) {
				if( language.equals( "pt" ) )
					CommonQuestionsPT.add( line );
				else
					CommonQuestionsEN.add( line );
			}
			br.close( );
			isr.close( );
			fis.close( );
			
			for( String question : CommonQuestionsPT ) {
				System.out.println( "QuestionPT => " + question );
			}
			
			for( String question : CommonQuestionsEN ) {
				System.out.println( "QuestionEN => " + question );
			}
			
			return true;
		} catch ( FileNotFoundException exFile ) {
			exFile.printStackTrace( );
			return false;
		} catch ( IOException exIo ) {
			exIo.printStackTrace( );
			return false;
		}
		
	}
	
	
	public boolean inspectQuestions( String language ) {
		System.out.println( "[inspectQuestions]" );
    	String xpathDivs = "//*[@id=\"post-2096\"]/div/div/div/h3";
    	int idx = 0;
    	try{
    		List< WebElement > results = ( new WebDriverWait( driver, timeout ) )
	                .until( ExpectedConditions
	                        .visibilityOfAllElementsLocatedBy(
	                        		      By.xpath( xpathDivs )
	                        )
	        );
	        System.out.println( "List["+CommonQuestionsPT.size( )+"] != Results["+results.size( )+"]" );
    		if( CommonQuestionsPT.size( ) != results.size( ) )
    			return false;
    		
	        for( WebElement elem : results ) { 
	    		String question = elem.getText( );
	    		Charset.forName( "UTF-8" ).encode( question );
	    		System.out.println( "question = " + question + " != " + CommonQuestionsPT.get( idx ) );
	    		if( !question.equals( CommonQuestionsPT.get( idx ) ) )
	    			return false;
	    		idx++;
	    	}
	        
	    	return true;
    	} catch( Exception e ){
            System.out.println("Error in checkOPSite");
            e.printStackTrace();
            return false;
    	}

	}
}
