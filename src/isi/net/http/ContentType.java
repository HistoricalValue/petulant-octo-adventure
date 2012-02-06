package isi.net.http;

public enum ContentType {
	Html("text/html", true),
	Css("text/css", true),
	Javascript("text/javascript", true),
	Plaintext("text/plain", true),

	_7z("application/x-7z-compressed", false),

	Png("image/png", false),

	OctetStream("application/octet-stream", false);

	public String GetHeaderString () {
		return headerString;
	}

	public boolean IsText () {
		return isText;
	}

	///////////////////////////////////////////////////////
	// state
	private final String headerString;
	private final boolean isText;

	private ContentType (final String headerString, final boolean isText) {
		this.headerString = headerString;
		this.isText = isText;
	}
}
