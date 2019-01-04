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
package pt.fccn.arquivo.tests;

import static org.junit.Assert.*;

import org.junit.Test;

import pt.fccn.arquivo.pages.HighlightsPage;
import pt.fccn.arquivo.pages.IndexPage;
import pt.fccn.saw.selenium.WebDriverTestBaseParalell;
import pt.fccn.saw.selenium.Retry;

/**
 * @author Simao Fontes
 *
 */
public class HighlightsTest extends WebDriverTestBaseParalell{
    /**
     * Test the link for more highlights
     */

    public HighlightsTest(String os,
                                    String version, String browser, String deviceName, String deviceOrientation) {
            super(os, version, browser, deviceName, deviceOrientation);
    }

    @Test
    @Retry
    public void highlightLinkTest() {
    	System.out.print("Running HighlightsTest. \n");
        IndexPage index = new IndexPage(driver);
        try{
        	index.langToEnglish( );
	    } catch( Exception e ) {
	    	fail("Exception -> highlightLinkTest");
	    }
        HighlightsPage highlightPage = index.goToHighlightsPage();
        System.out.println("Starting Assertions!");	
        assertTrue("The page displayed has not got the correct text being displayed",
                highlightPage.isPageCorrect());
        System.out.println("Passed 1st Assertion!");
        assertTrue("The page link is broken ",
                highlightPage.checkLinkHighligths());
        System.out.println("Passed 2nd  Assertion!");
        assertTrue("The title of the page is not correct ",
                highlightPage.checkHighligthsPageLinks());
        System.out.println("Passed 3rd Assertion!");
    }
}
