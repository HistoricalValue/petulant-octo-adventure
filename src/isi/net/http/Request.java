package isi.net.http;

import isi.net.http.helpers.ResponseRequestFields;
import isi.util.Strings;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;

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
		bob.append(method).append(" ").append(path).append(" ").append(version).append("\n");
		
		for (final Map.Entry<String, List<String>> field: fields)
			bob.append(field.getKey()).append(": ").append(Strings.Join(field.getValue(), "; ")).append("\n");
		
		return bob.toString();
	}
}
