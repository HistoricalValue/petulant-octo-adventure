package isi.net.http;

public enum ContentType {
	Html("text/html"),
	Css("text/css"),
	Javascript("text/javascript"),
	
	Png("image/png"),
	
	Plaintext("text/plain");
	
	public String GetHeaderString () {
		return headerString;
	}
	
	///////////////////////////////////////////////////////
	// state
	private final String headerString;
	
	private ContentType (final String headerString) {
		this.headerString = headerString;
	}
}
