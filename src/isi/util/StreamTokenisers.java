package isi.util;

import java.io.StreamTokenizer;

/**
 *
 * @author Amalia
 */
public class StreamTokenisers {
	
	///////////////////////////////////////////////////////
	// Usual character groups
	public static String EOLChars () {
		return "\r\n";
	}
	
	public static String WhitespaceChars () {
		return " \t";
	}
	
	public static String PunctuationWordChars () {
		return "~`!@#$%^&*()_+=-{}[]:\");'|\\<>,./?";
	}
	
	public static char[][] WordCharsRanges () {
		return new char[][] {
			new char[] {'a', 'z'},
			new char[] {'A', 'Z'},
			new char[] {'0', '9'}
		};
	}
	
	///////////////////////////////////////////////////////
	//
	public static abstract class StreamTokeniserCharacterClassSetter {
		public abstract void SetClass (StreamTokenizer t, char first, char last);
		
		public static class WordClassSetter extends StreamTokeniserCharacterClassSetter {
			@Override
			public void SetClass (final StreamTokenizer t, final char first, final char last) {
				t.wordChars(first, last);
			}
		}
		
		public static class WhitespaceClassSetter extends StreamTokeniserCharacterClassSetter {
			@Override
			public void SetClass (final StreamTokenizer t, final char first, final char last) {
				t.whitespaceChars(first, last);
			}
		}
	}
	
	///////////////////////////////////////////////////////
	//
	public static void SetStreamTokeniserCharsClass (final StreamTokenizer t, final char first, final char last, final StreamTokeniserCharacterClassSetter setter) {
		setter.SetClass(t, first, last);
	}
	
	public static void SetStreamTokeniserCharsClass (final StreamTokenizer t, final char[] chars, final StreamTokeniserCharacterClassSetter setter) {
		for (final char c: chars)
			SetStreamTokeniserCharsClass(t, c, c, setter);
	}
	
	public static void SetStreamTokeniserCharsClass (final StreamTokenizer t, final String chars, final StreamTokeniserCharacterClassSetter setter) {
		SetStreamTokeniserCharsClass(t, chars.toCharArray(), setter);
	}
	
	///////////////////////////////////////////////////////
	//
	public static void SetStreamTokeniserCharsAsWord (final StreamTokenizer t, final char first, final char last) {
		SetStreamTokeniserCharsClass(t, first, last, new StreamTokeniserCharacterClassSetter.WordClassSetter());
	}
	
	public static void SetStreamTokeniserCharsAsWord (final StreamTokenizer t, final char[] chars) {
		SetStreamTokeniserCharsClass(t, chars, new StreamTokeniserCharacterClassSetter.WordClassSetter());
	}
	
	public static void SetStreamTokeniserCharsAsWord (final StreamTokenizer t, final String chars) {
		SetStreamTokeniserCharsClass(t, chars, new StreamTokeniserCharacterClassSetter.WordClassSetter());
	}
	
	public static void SetStreamTokeniserCharsAsWhitespace (final StreamTokenizer t, final char first, final char last) {
		SetStreamTokeniserCharsClass(t, first, last, new StreamTokeniserCharacterClassSetter.WhitespaceClassSetter());
	}
	
	public static void SetStreamTokeniserCharsAsWhitespace (final StreamTokenizer t, final char[] chars) {
		SetStreamTokeniserCharsClass(t, chars, new StreamTokeniserCharacterClassSetter.WhitespaceClassSetter());
	}
	
	public static void SetStreamTokeniserCharsAsWhitespace (final StreamTokenizer t, final String chars) {
		SetStreamTokeniserCharsClass(t, chars, new StreamTokeniserCharacterClassSetter.WhitespaceClassSetter());
	}
	
	///////////////////////////////////////////////////////
	//
	public static void SetStreamTokeniserWordChars (final StreamTokenizer t) {
		for (final char[] range: WordCharsRanges())
			SetStreamTokeniserCharsAsWord(t, range[0], range[1]);
		SetStreamTokeniserCharsAsWord(t, PunctuationWordChars());
	}
	
	public static void SetStreamTokeniserWhitespaceChars (final StreamTokenizer t) {
		SetStreamTokeniserCharsAsWhitespace(t, WhitespaceChars());
	}
	
	public static void SetStreamTokeniserWhitespaceAsWord (final StreamTokenizer t) {
		SetStreamTokeniserCharsAsWord(t, WhitespaceChars());
	}
	
	public static void SetStreamTokeniserEOLChars (final StreamTokenizer t) {
		SetStreamTokeniserCharsAsWhitespace(t, EOLChars());
	}
	
	///////////////////////////////////////////////////////
	//
	public static void SetStreamTokeniserWordMode (final StreamTokenizer t) {
		t.resetSyntax();
		SetStreamTokeniserWordChars(t);
		SetStreamTokeniserWhitespaceChars(t);
		SetStreamTokeniserEOLChars(t);
		t.eolIsSignificant(true);
	}
	
	public static void SetStreamTokeniserLineMode (final StreamTokenizer t) {
		t.resetSyntax();
		SetStreamTokeniserWordChars(t);
		SetStreamTokeniserWhitespaceAsWord(t);
		SetStreamTokeniserEOLChars(t);
		t.eolIsSignificant(true);
	}
	
	///////////////////////////////////////////////////////
	// private
	
	///////////////////////////////////////////////////////
	// constructors
	
	private StreamTokenisers () {
	}
}
