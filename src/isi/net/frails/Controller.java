package isi.net.frails;

import isi.net.http.Request;
import isi.net.http.ResponseHeader;

public interface Controller {
	Viewing Handle (ResponseHeader response, Request request);
}
