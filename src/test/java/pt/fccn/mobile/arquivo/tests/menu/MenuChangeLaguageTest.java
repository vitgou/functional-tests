package pt.fccn.mobile.arquivo.tests.menu;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.Locale;

import org.junit.Test;
import org.openqa.selenium.By;

import pt.fccn.arquivo.selenium.Retry;
import pt.fccn.arquivo.selenium.WebDriverTestBaseParalell;
import pt.fccn.mobile.arquivo.utils.LocaleUtils;
import pt.fccn.mobile.arquivo.utils.LocalizedString;

/**
 * 
 * @author pedro.gomes.fccn@gmail.com
 *
 */

public class MenuChangeLaguageTest extends WebDriverTestBaseParalell {

	public MenuChangeLaguageTest(String os, String version, String browser, String deviceName, String deviceOrientation) {
		super(os, version, browser, deviceName, deviceOrientation);
	}
	
	@Test
	@Retry
	public void menuChangeLaguageTestPTtoEN() {
		LocaleUtils.changeLanguageToPT(this);
		menuChangeLaguageTest(LocaleUtils.PORTUGUESE);
	}

	@Test
	@Retry
	public void menuChangeLaguageTestENtoPT() {
		LocaleUtils.changeLanguageToEN(this);
		menuChangeLaguageTest(LocaleUtils.ENGLISH);
	}
	
	private void menuChangeLaguageTest(Locale locale) {
		
		//regular verification
		
		String pageLabel = new LocalizedString().pt("Páginas").en("Pages").apply(locale);
		
		assertThat("Verify page label",
				driver.findElement(By.xpath("//*[@id=\"PageButton\"]")).getText(), containsString(pageLabel));
		
		String ImageLabel = new LocalizedString().pt("Imagens").en("Images").apply(locale);
		
		assertThat("Verify image label",
				driver.findElement(By.xpath("//*[@id=\"ImageButton\"]")).getText(), containsString(ImageLabel));
		
		String advancedLabel = new LocalizedString().pt("Pesquisa avançada").en("Advanced search").apply(locale);
		
		assertThat("Verify advanced search label",
				driver.findElement(By.xpath("//*[@id=\"advancedSearchButton\"]")).getText(), containsString(advancedLabel));
		
		run("Open menu", () -> driver.findElement(By.id("menuButton")).click());
		
		String languageLabel = new LocalizedString().pt("English").en("Português").apply(locale);

		assertThat("Verify language label",
				driver.findElement(By.xpath("//*[@id=\"changeLanguage\"]")).getText(), containsString(languageLabel));
		
		run("Change language", () -> driver.findElement(By.id("changeLanguage")).click());
		
		//opposite verification after changing the language
		
		String pageLabel_changeLang = new LocalizedString().pt("Pages").en("Páginas").apply(locale);
		
		assertThat("Verify page label",
				driver.findElement(By.xpath("//*[@id=\"PageButton\"]")).getText(), containsString(pageLabel_changeLang));
		
		String ImageLabel_changeLang = new LocalizedString().pt("Images").en("Imagens").apply(locale);
		
		assertThat("Verify image label",
				driver.findElement(By.xpath("//*[@id=\"ImageButton\"]")).getText(), containsString(ImageLabel_changeLang));
		
		String advancedLabel_changeLang = new LocalizedString().pt("Advanced search").en("Pesquisa avançada").apply(locale);
		
		assertThat("Verify advanced search label",
				driver.findElement(By.xpath("//*[@id=\"advancedSearchButton\"]")).getText(), containsString(advancedLabel_changeLang));
		
		run("Open menu", () -> driver.findElement(By.id("menuButton")).click());
		
		String languageLabel_changeLang = new LocalizedString().pt("Português").en("English").apply(locale);
		
		assertThat("Verify language label",
				driver.findElement(By.xpath("//*[@id=\"changeLanguage\"]")).getText(), containsString(languageLabel_changeLang));
				
	}

}
