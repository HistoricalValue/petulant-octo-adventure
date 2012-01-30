package isi.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Matchers {
	
	///////////////////////////////////////////////////////
	//
	private static final Matcher NLm = Pattern.compile("\n").matcher("");
	private static final Pattern NL = NLm.pattern();
	public static Matcher NL () {
		return NLm.usePattern(NL).reset();
	}

	///////////////////////////////////////////////////////
	//
	public static boolean matches (final Matcher m, final Pattern pattern, final CharSequence input) {
		m.usePattern(pattern);
		m.reset(input);
		return m.matches();
	}

	public static boolean matches (final Matcher m, final String input) {
		m.reset(input);
		return m.matches();
	}

	public static boolean matches (final Matcher m, final Pattern pattern) {
		m.usePattern(pattern);
		return m.matches();
	}

	///////////////////////////////////////////////////////
	//

	private Matchers () {
	}
}
