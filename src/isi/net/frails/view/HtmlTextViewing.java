package isi.net.frails.view;

import isi.net.frails.Viewing;
import java.io.IOException;

public class HtmlTextViewing implements Viewing {

	///////////////////////////////////////////////////////
	// state
	private final String text;
	
	///////////////////////////////////////////////////////
	//
	public HtmlTextViewing (final String text) {
		this.text = text;
	}
	
	///////////////////////////////////////////////////////
	//
	@Override
	public void WriteTo (final Appendable w) throws IOException {
		w.append(text);
	}
	
}
