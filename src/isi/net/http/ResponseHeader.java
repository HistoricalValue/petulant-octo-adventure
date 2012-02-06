package isi.net.http;

import isi.net.http.helpers.ResponseRequestFields;
import isi.util.OnceSettable;
import java.io.IOException;
import java.io.Writer;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class ResponseHeader {

	///////////////////////////////////////////////////////
	// state
	private OnceSettable<Status> status = new OnceSettable<>();
	private OnceSettable<ContentType> contentType = new OnceSettable<>();
	private OnceSettable<Charset> encoding = new OnceSettable<>();
	private OnceSettable<Long> contentLength = new OnceSettable<>();
	private final ResponseRequestFields fields = new ResponseRequestFields();

	///////////////////////////////////////////////////////
	// constructors
	public ResponseHeader () {
		fields
				.AddField("Pragma", "no-cache")
				.AddField("Pragma-directive", "no-cache")
				.AddField("Cache-directive", "no-cache");
	}

	///////////////////////////////////////////////////////
	//
	public ResponseHeader SetStatus (final Status status) {
		this.status.SetQuiet(status);
		return this;
	}

	public ResponseHeader SetEncoding (final Charset encoding) {
		if (!contentType.GetIfSet().IsText())
			throw new IllegalArgumentException();
		this.encoding.SetQuiet(encoding);
		return this;
	}

	public ResponseHeader SetContentLength (final long length) {
		contentLength.SetQuiet(length);
		return this;
	}

	public ResponseHeader SetContentType (final ContentType type) {
		contentType.SetQuiet(type);
		return this;
	}

	public Writer WriteTo (final Writer w) throws IOException {
		// Set content type as a field
		if (contentType.IsSet()) {
			final List<String> values = new ArrayList<>(2);
			values.add(contentType.GetIfSet().GetHeaderString());

			if (contentType.GetIfSet().IsText())
				values.add("charset=" + encoding.GetIfSet().name());

			fields.SetValue("Content-type", values);
		}

		if (contentLength.IsSet())
			fields.SetValue("Content-length", contentLength.GetIfSet().toString());

		w
				.append("HTTP/1.1 ")
				.append(Integer.toString(status.GetIfSet().GetCode()))
				.append(" ")
				.append(status.toString())
				.append("\r\n");
		fields.WriteTo(w, "\r\n").append("\r\n");

		return w;
	}
}
