package isi.net.http;

import java.nio.charset.Charset;

public class Request {
	
	///////////////////////////////////////////////////////
	
	public static final Charset CHARSET = Charset.forName("UTF-8");
		
	///////////////////////////////////////////////////////
	// state
	private final Method method;
	private final String path;
	private final String version;

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
	// Object
	@Override
	public String toString () {
		return method.toString() + " " + path + " " + version;
	}
}
