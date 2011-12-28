package isi.net.http;

import isi.net.http.helpers.ResponseRequestFields;
import java.nio.charset.Charset;

public class Request {
	
	///////////////////////////////////////////////////////
	
	public static final Charset CHARSET = Charset.forName("UTF-8");
		
	///////////////////////////////////////////////////////
	// state
	private final Method method;
	private final String path;
	private final String version;
	private final ResponseRequestFields fields = new ResponseRequestFields();

	///////////////////////////////////////////////////////
	// construction
	public Request (final Method method, final String path, final String version) {
		this.method	= method;
		this.path = path;
		this.version = version;
	}
	
	///////////////////////////////////////////////////////
	//
	
	public String GetPath () {
		return path;
	}
	
	///////////////////////////////////////////////////////

	public ResponseRequestFields GetFields () {
		return fields;
	}
	
	///////////////////////////////////////////////////////
	// Object
	@Override
	public String toString () {
		final StringBuilder bob = new StringBuilder(1 << 14);
		bob.append(method).append(" ").append(path).append(" ").append(version).append("\n")
				.append(fields).append("\n");
		
		return bob.toString();
	}
}
