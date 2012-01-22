package isi.net.http;

import static isi.util.logging.Loggers.L;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.net.SocketAddress;
import java.nio.channels.Channels;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.channels.WritableByteChannel;
import java.util.LinkedList;
import java.util.List;

public class Server {

	///////////////////////////////////////////////////////
	// state
	private final ServerSocketChannel server;
	private final List<RequestHandler> handlers = new LinkedList<>();

	///////////////////////////////////////////////////////
	// constructors
	public Server (final SocketAddress bindAddress, final int backlog) throws IOException {
		server = ServerSocketChannel.open().bind(bindAddress, backlog);
	}

	///////////////////////////////////////////////////////
	//
	public Server AddHandler (final RequestHandler handler) {
		handlers.add(handler);
		return this;
	}

	///////////////////////////////////////////////////////
	//
	public void Serve () throws IOException {
		try (final SocketChannel client = server.accept()) {
		//
		try (final OutputStream outs = Channels.newOutputStream(client)) {
		try (final Reader r = Channels.newReader(client, Request.Encoding.newDecoder(), 1 << 14)) {
		//
		try (final ByteArrayOutputStream baouts = new ByteArrayOutputStream(1 << 14)) {
		//
			final Request request = new RequestParser(r).Parse();
			final Response response = new Response();
			
			try (final WritableByteChannel baoutswch = Channels.newChannel(baouts)) {
				NotifyHandlers(response, client, baoutswch, request);
			}

			response.SetContentLength(baouts.size());
			try (final ByteArrayOutputStream responseBaouts = new ByteArrayOutputStream(1 << 10)) {
			try (final OutputStreamWriter responseBaoutsw = new OutputStreamWriter(responseBaouts, Request.Encoding)) {
				response.WriteTo(responseBaoutsw);
				responseBaoutsw.flush();
				responseBaouts.writeTo(outs);
			}}

			baouts.writeTo(outs);
		}}}}
		catch (final IOException ioex) {
			L().e(ioex);
		}
	}

	///////////////////////////////////////////////////////
	// private
	private void NotifyHandlers (final Response res, final SocketChannel client, final WritableByteChannel w, final Request req) throws IOException {
		for (final RequestHandler h : handlers)
			if (h.ShouldHandleDirect(req))
				h.HandleDirect(res, client, req);
			else
				h.Handle(res, w, req);
	}
}
