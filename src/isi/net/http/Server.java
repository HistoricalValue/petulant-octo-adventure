package isi.net.http;

import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;
import java.util.List;

public class Server {
	
	///////////////////////////////////////////////////////
	// state
	private final ServerSocket sock;
	private final List<RequestHandler> handlers = new LinkedList<>();
	
	///////////////////////////////////////////////////////
	// constructors
	public Server (final ServerSocket sock) {
		this.sock = sock;
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
		try (final Socket client = sock.accept()) {
		try (final InputStream ins = client.getInputStream()) {
		try (final OutputStream outs = client.getOutputStream()) {
		try (final OutputStreamWriter outsw = new OutputStreamWriter(outs, Request.CHARSET)) {
		try (final BufferedWriter boutsw = new BufferedWriter(outsw)) {
		try (final ByteArrayOutputStream baouts = new ByteArrayOutputStream(1 << 19)) {
			final Response response = new Response();

			try (final OutputStreamWriter baoutsw = new OutputStreamWriter(baouts, Request.CHARSET)) {
			try (final BufferedWriter bob = new BufferedWriter(baoutsw)) {
				NotifyHandlers(response, bob, new RequestParser(ins).Parse());
			}}

			response.SetContentLength(baouts.size());
			response.WriteTo(boutsw);
			
			boutsw.flush();
			outsw.flush();

			baouts.writeTo(outs);

			outs.flush();
		}}}}}}
	}
	
	///////////////////////////////////////////////////////
	// private
	private void NotifyHandlers (final Response res, final Writer w, final Request req) {
		for (final RequestHandler h : handlers)
			h.Handle(res, w, req);
	}
}
