package isi.net.http;

import isi.net.http.helpers.ResponseRequestFields;
import java.io.IOException;
import java.io.Writer;

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
		fields.SetValues("Content-length", Integer.toString(length));
		return this;
	}
	
	public Response SetContentType (final ContentType type) {
		fields.SetValues("Content-type", type.GetHeaderString(), "charset=utf8");
		return this;
	}
	
	public Writer WriteTo (final Writer w) throws IOException {
		w
				.append("HTTP/1.1 ")
				.append(Integer.toString(status.GetCode()))
				.append(" ")
				.append(status.toString())
				.append("\r\n");
		return fields.WriteTo(w).append("\r\n");
	}
}
