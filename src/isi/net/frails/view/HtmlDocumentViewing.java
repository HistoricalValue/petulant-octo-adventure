package isi.net.frails.view;

import isi.net.frails.Viewing;
import isi.util.html.Document;
import java.io.IOException;

public class HtmlDocumentViewing implements Viewing {

	///////////////////////////////////////////////////////
	// state
	private final Document doc;

	///////////////////////////////////////////////////////
	//
	public HtmlDocumentViewing (final Document doc) {
		this.doc = doc;
	}

	@Override
	public void WriteTo (final Appendable w) throws IOException {
		doc.WriteTo(w);
	}

}
