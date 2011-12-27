package isi.net.http;

import isi.util.meta.Builder02;
import isi.util.streams.StreamTokeniserTokenType;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StreamTokenizer;
import java.lang.reflect.InvocationTargetException;
import static isi.net.http.helpers.Url.ParseUrl;

public class RequestParser {

	///////////////////////////////////////////////////////
	// state
	private final StreamTokenizer requestTokeniser;
	
	///////////////////////////////////////////////////////
	// construction
	public RequestParser (final InputStream ins) {
		requestTokeniser = new StreamTokenizer(new BufferedReader(new InputStreamReader(ins, Request.CHARSET)));
		requestTokeniser.resetSyntax();
	}
	private static abstract class StreamTokeniserCharacterClassSetter {
		public abstract void SetClass (StreamTokenizer t, char first, char last);
		public static StreamTokeniserCharacterClassSetter GetWordClassSetter () {
			return new StreamTokeniserCharacterClassSetter() {
				@Override
				public void SetClass (final StreamTokenizer t, final char first, final char last) {
					t.wordChars(first, last);
				}
			};
		}
		public static StreamTokeniserCharacterClassSetter GetWhitespaceClassSetter () {
			return new StreamTokeniserCharacterClassSetter() {
				@Override
				public void SetClass (final StreamTokenizer t, final char first, final char last) {
					t.whitespaceChars(first, last);
				}
			};
		}
	}
	private static void SetStreamTokeniserCharsClass (final StreamTokenizer t, final char first, final char last, final StreamTokeniserCharacterClassSetter setter) {
		setter.SetClass(t, first, last);
	}
	private static void SetStreamTokeniserCharsClass (final StreamTokenizer t, final char[] chars, final StreamTokeniserCharacterClassSetter setter) {
		for (final char c: chars)
			SetStreamTokeniserCharsClass(t, c, c, setter);
	}
	private static void SetStreamTokeniserCharsClass (final StreamTokenizer t, final String chars, final StreamTokeniserCharacterClassSetter setter) {
		SetStreamTokeniserCharsClass(t, chars.toCharArray(), setter);
	}
	private static void SetStreamTokeniserCharsAsWord (final StreamTokenizer t, final char first, final char last) {
		SetStreamTokeniserCharsClass(t, first, last, StreamTokeniserCharacterClassSetter.GetWordClassSetter());
	}
	private static void SetStreamTokeniserCharsAsWord (final StreamTokenizer t, final String wordchars) {
		SetStreamTokeniserCharsClass(t, wordchars, StreamTokeniserCharacterClassSetter.GetWordClassSetter());
	}
	private static void SetStreamTokeniserCharsAsWhitespace (final StreamTokenizer t, final String chars) {
		SetStreamTokeniserCharsClass(t, chars, StreamTokeniserCharacterClassSetter.GetWhitespaceClassSetter());
	}
	private static String GetEOLChars () {
		return "\r\n";
	}
	private static String GetWhitespaceChars () {
		return " \t";
	}
	private static String GetPunctuationWordChars () {
		return "~`!@#$%^&*()_+=-{}[]:\");'|\\<>,./?";
	}
	private static char[][] GetWordCharsRanges () {
		return new char[][] {
			new char[] {'a', 'z'},
			new char[] {'A', 'Z'},
			new char[] {'0', '9'}
		};
	}
	private static void SetStreamTokeniserWordChars (final StreamTokenizer t) {
		for (final char[] range: GetWordCharsRanges())
			SetStreamTokeniserCharsAsWord(t, range[0], range[1]);
		SetStreamTokeniserCharsAsWord(t, GetPunctuationWordChars());
	}
	private static void SetStreamTokeniserWhitespaceChars (final StreamTokenizer t) {
		SetStreamTokeniserCharsAsWhitespace(t, GetWhitespaceChars());
	}
	private static void SetStreamTokeniserWhitespaceAsWord (final StreamTokenizer t) {
		SetStreamTokeniserCharsAsWord(t, GetWhitespaceChars());
	}
	private static void SetStreamTokeniserEOLChars (final StreamTokenizer t) {
		SetStreamTokeniserCharsAsWhitespace(t, GetEOLChars());
	}
	private static void SetStreamTokeniserWordMode (final StreamTokenizer t) {
		t.resetSyntax();
		SetStreamTokeniserWordChars(t);
		SetStreamTokeniserWhitespaceChars(t);
		SetStreamTokeniserEOLChars(t);
		t.eolIsSignificant(true);
	}
	private static void SetStreamTokeniserLineMode (final StreamTokenizer t) {
		t.resetSyntax();
		SetStreamTokeniserWordChars(t);
		SetStreamTokeniserWhitespaceAsWord(t);
		SetStreamTokeniserEOLChars(t);
		t.eolIsSignificant(true);
	}
	
	///////////////////////////////////////////////////////
	
	@SuppressWarnings("AssignmentToForLoopParameter")
	public Request Parse () throws IOException {
		final Builder02<Request, Method, String, String> builder = new Builder02<>(Request.class, Method.class, String.class, String.class);
		
		SetStreamTokeniserWordMode(requestTokeniser);
		
		builder.Set00(Method.valueOf(ReadString()));
		builder.Set01(ParseUrl(ReadString(), Request.CHARSET));
		builder.Set02(ReadString());
		ReadEOL();
		
		SetStreamTokeniserLineMode(requestTokeniser);

		try {
			final Request request = builder.Build();

			for (StreamTokeniserTokenType tt = StreamTokeniserTokenType.valueOf(requestTokeniser.nextToken()); tt != StreamTokeniserTokenType.EOL; tt = StreamTokeniserTokenType.valueOf(requestTokeniser.nextToken())) {
				if (tt != StreamTokeniserTokenType.WORD)
					throw new RuntimeException("Malformed HTTP request: expecting line");
				
				final String line = requestTokeniser.sval;
				final String[] tokens = line.split(":\\s*");
				final String name = tokens[0];
				final String valuesStr = tokens[1];
				final String[] values = valuesStr.split(";\\s*");
				
				request.GetFields().AddField(name, values);
				
				ReadEOL();
			}
			
			return request;
		} catch (NoSuchMethodException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
			throw new AssertionError(ex);
		}
	}
	
	///////////////////////////////////////////////////////
	// private
	private void ReadToken (final StreamTokeniserTokenType wanted) throws IOException {
		final StreamTokeniserTokenType tt = StreamTokeniserTokenType.valueOf(requestTokeniser.nextToken());
		if (tt != wanted)
			throw new RuntimeException("Parse error wanted " + wanted + " != " + tt);
	}
	
	private String ReadString () throws IOException {
		ReadToken(StreamTokeniserTokenType.WORD);
		return requestTokeniser.sval;
	}
	
	private void ReadEOL () throws IOException {
		ReadToken(StreamTokeniserTokenType.EOL);
	}
	
}

