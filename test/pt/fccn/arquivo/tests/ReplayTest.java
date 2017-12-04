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
package pt.fccn.arquivo.tests;

import static org.junit.Assert.*;

import org.junit.Test;

import pt.fccn.arquivo.pages.ReplayPage;
import pt.fccn.arquivo.pages.IndexPage;
import pt.fccn.saw.selenium.WebDriverTestBaseParalell;
import pt.fccn.saw.selenium.Retry;

/**
 * @author Fernando Melo
 *
 */
public class ReplayTest extends WebDriverTestBaseParalell{
    /**
     * PRE-PROD TESTS
     * Tests the Replay for a given SET of URLS
     * TODO:: ENGLISH AND PT
     * TODO:: Add Production tests comparing the output on both Brokers
     */
    public ReplayTest(String os,String version, String browser, String deviceName, String deviceOrientation) {
            super(os, version, browser, deviceName, deviceOrientation);
    }


    boolean isPreProd = true;
    @Test
    @Retry
    public void replayTest( ) {
    	System.out.print("Running ReplayTest. \n");
        ReplayPage replay = new ReplayPage(driver, isPreProd);      
        assertTrue("Failed The Replay Tests in Portuguese", replay.inspectURLs("PT"));
        assertTrue("Failed The Replay Tests in English", replay.inspectURLs("EN"));
    }
}
