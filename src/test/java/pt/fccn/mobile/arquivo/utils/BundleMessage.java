package pt.fccn.mobile.arquivo.utils;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.function.BiFunction;

/**
 *
 * @author Ivo Branco <ivo.branco@fccn.pt>
 *
 */
public class BundleMessage implements BiFunction<Locale, Object[], String> {
	Map<Locale, String> templateMap = new HashMap<>();

	public String put(Locale locale, String template) {
		return templateMap.put(locale, template);
	}

	public BundleMessage add(Locale locale, String template) {
		put(locale, template);
		return this;
	}

	public BundleMessage pt(String template) {
		put(Locale.forLanguageTag("pt"), template);
		return this;
	}

	public BundleMessage en(String template) {
		put(Locale.ENGLISH, template);
		return this;
	}

	public String format(Locale locale, Object... arguments) {
		String template = templateMap.get(locale);
		Objects.requireNonNull(template, () -> "Missing template for locale=" + locale.getLanguage());
		return new MessageFormat(template, locale).format(arguments, new StringBuffer(), null).toString();
	}

	@Override
	public String apply(Locale locale, Object[] arguments) {
		return format(locale, arguments);
	}
}
