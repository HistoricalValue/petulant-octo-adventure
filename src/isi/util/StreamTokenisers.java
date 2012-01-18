package isi.util;

import isi.util.streams.StreamTokeniserTokenType;
import java.io.IOError;
import java.io.IOException;
import java.io.StreamTokenizer;
import java.io.StringReader;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Amalia
 */
public class StreamTokenisers {

	///////////////////////////////////////////////////////
	//
	public static StreamTokenizer New (final String s) {
		return new StreamTokenizer(new StringReader(s));
	}

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
	//

	public static String GetLine (final StreamTokenizer t) throws IOException {
		SetStreamTokeniserLineMode(t);
		final StreamTokeniserTokenType tt = StreamTokeniserTokenType.valueOf(t.nextToken());
		assert tt == StreamTokeniserTokenType.WORD;
		return t.sval;
	}

	public static List<String> ReadAllWordTokens (final StreamTokenizer t) throws IOException {
		final List<String> result = new LinkedList<>();
		for (StreamTokeniserTokenType tt = StreamTokeniserTokenType.valueOf(t.nextToken()); tt == StreamTokeniserTokenType.WORD; tt = StreamTokeniserTokenType.valueOf(t.nextToken()))
			result.add(t.sval);

		return result;
	}

	public static List<String> ReadAllWordTokensToTheEnd (final StreamTokenizer t) throws IOException {
		SetStreamTokeniserWordMode(t);
		final List<String> result = ReadAllWordTokens(t);
		final StreamTokeniserTokenType tt = StreamTokeniserTokenType.valueOf(t.nextToken());
		assert tt == StreamTokeniserTokenType.EOF;
		return result;
	}

	///////////////////////////////////////////////////////
	//

	public static Iterable<StreamTokeniserTokenType> ToIterable (final StreamTokenizer t) {
		return new Iterable<StreamTokeniserTokenType>() {
			@Override
			public Iterator<StreamTokeniserTokenType> iterator () {
				return new Iterator<StreamTokeniserTokenType>() {
					private StreamTokeniserTokenType buffer;

					private void FillBuffer () {
						if (buffer == null)
							try {
								buffer = StreamTokeniserTokenType.valueOf(t.nextToken());
							} catch (final IOException ex) {
								throw new RuntimeException(ex);
							}
					}

					@Override
					public boolean hasNext () {
						FillBuffer();
						return buffer != StreamTokeniserTokenType.EOF;
					}

					@Override
					public StreamTokeniserTokenType next () {
						FillBuffer();
						final StreamTokeniserTokenType result = buffer;
						buffer = null;
						return result;
					}

					@Override
					public void remove () {
						throw new UnsupportedOperationException("Not supported (ever).");
					}
				};
			}
		};
	}

	///////////////////////////////////////////////////////
	// private

	///////////////////////////////////////////////////////
	// constructors

	private StreamTokenisers () {
	}
}
