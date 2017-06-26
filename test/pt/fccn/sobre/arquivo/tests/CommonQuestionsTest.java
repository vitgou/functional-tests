package pt.fccn.sobre.arquivo.tests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import pt.fccn.arquivo.pages.AdvancedPage;
import pt.fccn.arquivo.pages.IndexPage;
import pt.fccn.arquivo.pages.ReplayPage;
import pt.fccn.saw.selenium.Retry;
import pt.fccn.saw.selenium.WebDriverTestBaseParalell;
import pt.fccn.sobre.arquivo.pages.CommonQuestionsPage;
import pt.fccn.sobre.arquivo.pages.IndexSobrePage;

public class CommonQuestionsTest extends WebDriverTestBaseParalell {

	public CommonQuestionsTest( String os, String version, String browser, String deviceName, String deviceOrientation ) {
		super( os, version, browser, deviceName, deviceOrientation );
	}
	
	boolean isPreProd = true;
	
	@Test
	@Retry
	public void commonQuestionsTest( ) {
		System.out.print( "Running Common Questions Test. \n");
        IndexSobrePage index = new IndexSobrePage( driver );
        System.out.println( "Going to the CommonQuestions" );
        CommonQuestionsPage commonQuestions = index.goToCommonQuestionsPage( );
        assertTrue("Failed The Common Question in Portuguese", commonQuestions.inspectQuestions( "PT" ) );
		assertTrue("Failed The Common Question in English", commonQuestions.inspectQuestions( "EN" ) );
	}

}
