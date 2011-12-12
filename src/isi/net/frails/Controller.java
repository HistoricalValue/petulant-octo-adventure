package isi.net.frails;

import isi.net.http.Request;
import isi.net.http.Response;

public interface Controller {
	Viewing Handle (Response response, Request request);
}
