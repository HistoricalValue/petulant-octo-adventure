package isi.net.http;

import java.io.Writer;

public interface RequestHandler {
	void Handle (Response response, Writer client, Request request);
}
