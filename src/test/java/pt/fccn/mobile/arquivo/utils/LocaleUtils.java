package pt.fccn.mobile.arquivo.utils;

import pt.fccn.arquivo.selenium.WebDriverTestBaseParalell;

/**
 *
 * @author Ivo Branco <ivo.branco@fccn.pt>
 *
 */
public class LocaleUtils {

	public static void changeLanguageToPT(WebDriverTestBaseParalell test) {
		String language = "pt";
		changeLanguageTo(test, language);
	}

	public static void changeLanguageToEN(WebDriverTestBaseParalell test) {
		String language = "en";
		changeLanguageTo(test, language);
	}

	private static void changeLanguageTo(WebDriverTestBaseParalell test, String language) {
		test.getDriver().get(test.getTestURL() + "?l=" + language);
	}

}
