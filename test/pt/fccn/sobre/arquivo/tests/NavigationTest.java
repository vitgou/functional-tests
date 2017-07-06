package pt.fccn.sobre.arquivo.tests;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.IOException;

import org.junit.Test;

import pt.fccn.saw.selenium.Retry;
import pt.fccn.saw.selenium.WebDriverTestBaseParalell;
import pt.fccn.sobre.arquivo.pages.AboutPage;
import pt.fccn.sobre.arquivo.pages.AudioPage;
import pt.fccn.sobre.arquivo.pages.CollaboratePage;
import pt.fccn.sobre.arquivo.pages.IndexSobrePage;
import pt.fccn.sobre.arquivo.pages.NewsOnMediaPage;
import pt.fccn.sobre.arquivo.pages.NewsPage;
import pt.fccn.sobre.arquivo.pages.PresentationsPage;
import pt.fccn.sobre.arquivo.pages.PublicationsPage;
import pt.fccn.sobre.arquivo.pages.ReportsPage;
import pt.fccn.sobre.arquivo.pages.SiteMapPage;
import pt.fccn.sobre.arquivo.pages.VideoPage;

public class NavigationTest  extends WebDriverTestBaseParalell {

	public NavigationTest(String os, String version, String browser, String deviceName, String deviceOrientation) {
		super( os, version, browser, deviceName, deviceOrientation );
	}
	
	@Test
	@Retry
	public void navigation( )  {
		System.out.print( "Running navigation Test. \n");
		IndexSobrePage index = null;
		try{
			index = new IndexSobrePage( driver );
			NewsPage news = index.goToNewsPage( );
			assertTrue("Failed The News Page Test in Portuguese", news.checkNewsLinks( "PT" ) );
			PublicationsPage publications = index.goToPublicationsPage( );
			ReportsPage reports = index.goToReportsPage( );
			assertTrue("Failed The Reports Page Test in Portuguese", reports.checkReportsLinks( "PT" ) );
			//TODO NewsOnMediaPage newsonmedia = index.goToNewOnMediaPage( );
			//assertTrue("Failed The New on Media Page Test in Portuguese", newsonmedia.checkNewsLinks( "PT") );
			AudioPage audio = index.goToAudioPage( "PT" );
			assertTrue("Failed The Audio Page Test in Portuguese", audio.checkAudioLinks( "PT" ) );
			VideoPage video = index.goToVideoPage( );
			assertTrue("Failed The Video Page Test in Portuguese", video.checkVideoLinks( "PT" ) );
			PresentationsPage pres = index.goToPresentationsPage( );
			assertTrue("Failed The Presentations Page Test in Portuguese", pres.checkPresentationLinks( "PT" ) );
			CollaboratePage coll = index.goToCollaboratePage( );
			assertTrue("Failed The Collaborate Page Test in Portuguese", coll.checkCollaborateLinks( "PT" ) );
			AboutPage about = index.goToAboutPage( );
			assertTrue("Failed The Collaborate Page Test in Portuguese", about.checkAboutLinks( "PT" ) );
			
			
			
	        //TODO english version missing
	        
		} catch( IOException e ) {
			fail("IOException -> IndexSobrePage");
		}
		
    }
	

}
