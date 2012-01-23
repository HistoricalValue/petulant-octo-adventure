package isi.net.http;

import isi.util.Ref;
import java.io.IOException;
import java.io.Reader;
import java.net.SocketAddress;
import java.nio.channels.Channels;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

import static isi.util.logging.Loggers.L;

public class Server {

	///////////////////////////////////////////////////////
	// state
	private final ServerSocketChannel server;
	private final RequestHandler handler;

	///////////////////////////////////////////////////////
	// constructors
	public Server (final SocketAddress bindAddress, final int backlog, final RequestHandler handler) throws IOException {
		server = ServerSocketChannel.open().bind(bindAddress, backlog);
		this.handler = handler;
	}

	///////////////////////////////////////////////////////
	//
	public void Serve () throws IOException {
		try (	final SocketChannel client = server.accept();
				final Reader r = Channels.newReader(client, Request.Encoding.newDecoder(), 1 << 14);)
		{
			final Request request = new RequestParser(r).Parse();
			final boolean isDirect = handler.ShouldHandleDirect(request);
			
			try (final Response response = isDirect? new DirectResponse(client) : new BufferedResponse(client)) {
				handler.Handle(response, request, isDirect);
			}
			
		}
		catch (final IOException ioex) {
			L().e(ioex);
		}
	}

	///////////////////////////////////////////////////////
	// private
}
