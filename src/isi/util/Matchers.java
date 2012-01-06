package isi.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Matchers {
	
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
