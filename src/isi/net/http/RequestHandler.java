package isi.net.http;

import java.io.IOException;

public interface RequestHandler {
	boolean ShouldHandleDirect (Request request);
	void Handle (Response response, Request request, boolean isDirect) throws IOException;
}
