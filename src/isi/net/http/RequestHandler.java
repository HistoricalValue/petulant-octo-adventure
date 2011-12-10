package isi.net.http;

import java.io.Writer;

public interface RequestHandler {
	void Handle (Writer client, Request request);
}
