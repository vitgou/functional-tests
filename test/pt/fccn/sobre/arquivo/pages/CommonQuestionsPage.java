package pt.fccn.sobre.arquivo.pages;

import java.io.File;
import java.io.FileNotFoundException;
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
			s = new Scanner( new File( dir.concat( File.separator ).concat( filename ) ) , "utf-8" );
			while ( s.hasNext( ) ) {
				if( language.equals( "pt" ) )
					CommonQuestionsPT.add( s.next( ) );
				else
					CommonQuestionsEN.add( s.next( ) );
			}
			s.close( );
			return true;
		} catch ( FileNotFoundException e ) {
			e.printStackTrace( );
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
