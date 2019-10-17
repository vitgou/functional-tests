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

import static org.hamcrest.CoreMatchers.endsWith;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import pt.fccn.arquivo.selenium.Retry;
import pt.fccn.arquivo.selenium.WebDriverTestBaseParalell;

/**
 * @author Simao Fontes
 * @author Ivo Branco - major rewrite
 *
 */
public class TestSponsorImage extends WebDriverTestBaseParalell {
	public TestSponsorImage(String os, String version, String browser, String deviceName, String deviceOrientation) {
		super(os, version, browser, deviceName, deviceOrientation);
	}

	@Test
	@Retry
	public void testSponsorImage() {

		WebElement fccnImageElement = driver.findElement(By.xpath("//*[@id=\"wp_editor_widget-10\"]/div/div[1]/a/img"));
		assertThat("Check fccn image is present", fccnImageElement.getAttribute("src"), endsWith("logo-fccn.png"));

		WebElement ministerImageElement = driver
				.findElement(By.xpath("//*[@id=\"wp_editor_widget-10\"]/div/div[2]/a/img"));
		assertThat("Check minister image is present", ministerImageElement.getAttribute("src"),
				endsWith("10-Digital_PT_4C_H_FC_MCTES_cinza.png"));
	}

}
