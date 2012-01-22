package isi.net.http;

import java.io.IOException;
import java.nio.channels.WritableByteChannel;

public interface RequestHandler {
	boolean ShouldHandleDirect (Request request);
	void Handle (Response response, WritableByteChannel client, Request request) throws IOException;
	void HandleDirect (Response response, WritableByteChannel client, Request request) throws IOException;
}
