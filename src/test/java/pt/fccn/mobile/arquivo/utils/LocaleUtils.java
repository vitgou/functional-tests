package pt.fccn.mobile.arquivo.utils;

import pt.fccn.arquivo.selenium.WebDriverTestBaseParalell;

/**
 *
 * @author Ivo Branco <ivo.branco@fccn.pt>
 *
 */
public class LocaleUtils {

	private static final String EN = "en";
	private static final String PT = "pt";

	public static void changeLanguageToPT(WebDriverTestBaseParalell test) {
		String language = PT;
		changeLanguageTo(test, language);
	}

	public static void changeLanguageToEN(WebDriverTestBaseParalell test) {
		String language = EN;
		changeLanguageTo(test, language);
	}

	private static void changeLanguageTo(WebDriverTestBaseParalell test, String language) {
		test.getDriver().get(test.getTestURL() + "?" + "l=" + language);
	}

	public static String languagePTUrlQueryParameter() {
		return "l=" + PT;
	}

	public static String languageENUrlQueryParameter() {
		return "l=" + EN;
	}
}
