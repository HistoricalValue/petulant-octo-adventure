package isi.net.http;

import isi.net.http.helpers.ResponseRequestFields;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

public class Response {
	
	///////////////////////////////////////////////////////
	// state
	private Status status;
	private final ResponseRequestFields fields = new ResponseRequestFields();
	
	///////////////////////////////////////////////////////
	// constructors
	public Response () {
		fields
				.AddField("Pragma", "no-cache")
				.AddField("Pragma-directive", "no-cache")
				.AddField("Cache-directive", "no-cache");
	}
	
	///////////////////////////////////////////////////////
	//
	public Response SetStatus (final Status status) {
		this.status = status;
		return this;
	}
	
	public Response SetContentLength (final int length) {
		fields.SetValue("Content-length", Integer.toString(length));
		return this;
	}
	
	public Response SetContentType (final ContentType type) {
		final List<String> values = new ArrayList<>(2);
		values.add(type.GetHeaderString());
		
		if (type.IsText())
			values.add("charset=utf8");
		
		fields.SetValue("Content-type", values);
		return this;
	}
	
	public Writer WriteTo (final Writer w) throws IOException {
		w
				.append("HTTP/1.1 ")
				.append(Integer.toString(status.GetCode()))
				.append(" ")
				.append(status.toString())
				.append("\r\n");
		fields.WriteTo(w, "\r\n").append("\r\n");
		return w;
	}
}
