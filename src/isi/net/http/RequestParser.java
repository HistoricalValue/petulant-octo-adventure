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
import static isi.util.StreamTokenisers.SetStreamTokeniserWordMode;
import static isi.util.StreamTokenisers.SetStreamTokeniserLineMode;

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
	
	///////////////////////////////////////////////////////
	
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
				final String value = tokens[1];
				
				request.GetFields().AddField(name, value);
				
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

