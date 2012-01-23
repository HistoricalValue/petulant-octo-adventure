package isi.net.http;

import java.io.IOException;
import java.nio.channels.WritableByteChannel;

public interface RequestHandler {
	boolean ShouldHandleDirect (Request request);
	void Handle (ResponseHeader response, WritableByteChannel client, Request request) throws IOException;
	void HandleDirect (ResponseHeader response, WritableByteChannel client, Request request) throws IOException;
}
