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
		requestTokeniser.whitespaceChars(' ', ' ');
		requestTokeniser.whitespaceChars('\n', '\n');
		requestTokeniser.whitespaceChars('\t', '\t');
		requestTokeniser.whitespaceChars('\r', '\r');
		requestTokeniser.wordChars('a', 'z');
		requestTokeniser.wordChars('A', 'Z');
		requestTokeniser.wordChars('0', '9');
		SetStreamTokeniserWordChars(requestTokeniser, "~`!@#$%^&*()_+=-{}[]:\");'|\\<>,./?");
		requestTokeniser.eolIsSignificant(true);
	}
	private static void SetStreamTokeniserWordChars (final StreamTokenizer t, final String wordchars) {
		for (final char c : wordchars.toCharArray())
			t.wordChars(c, c);
	}
	
	///////////////////////////////////////////////////////
	
	public Request Parse () throws IOException {
		final Builder02<Request, Method, String, String> builder = new Builder02<>(Request.class, Method.class, String.class, String.class);
		
		builder.Set00(Method.valueOf(ReadString()));
		builder.Set01(ParseUrl(ReadString(), Request.CHARSET));
		builder.Set02(ReadString());
		ReadEOL();

		try {
			return builder.Build();
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

