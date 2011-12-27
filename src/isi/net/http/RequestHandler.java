package isi.net.http;

import java.io.IOException;
import java.io.Writer;

public interface RequestHandler {
	void Handle (Response response, Writer client, Request request) throws IOException;
}
