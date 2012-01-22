package isi.net.http;

import isi.net.http.helpers.ResponseRequestFields;
import java.io.IOException;
import java.io.Writer;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class Response {

	///////////////////////////////////////////////////////
	// state
	private Status status;
	private ContentType contentType;
	private Charset encoding;
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
	
	public Response SetEncoding (final Charset encoding) {
		if (!contentType.IsText())
			throw new IllegalArgumentException();
		this.encoding = encoding;
		return this;
	}

	public Response SetContentLength (final long length) {
		fields.SetValue("Content-length", Long.toString(length));
		return this;
	}

	public Response SetContentType (final ContentType type) {
		contentType = type;
		return this;
	}

	public Writer WriteTo (final Writer w) throws IOException {
		// Set content type as a field
		if (contentType != null) {
			final List<String> values = new ArrayList<>(2);
			values.add(contentType.GetHeaderString());

			if (contentType.IsText())
				values.add("charset=" + encoding.name());

			fields.SetValue("Content-type", values);
		}
		
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
