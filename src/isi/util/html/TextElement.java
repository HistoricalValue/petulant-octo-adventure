package isi.util.html;

import java.io.IOException;

public class TextElement extends Element {
	
	///////////////////////////////////////////////////////
	// state
	private final String text;
	
	///////////////////////////////////////////////////////
	// constructors 
	public TextElement (final String text) {
		super("");
		this.text = text;
	}
	
	///////////////////////////////////////////////////////
	//
	@Override
	public void WriteTo (final Appendable appendable) throws IOException {
		appendable.append(text);
	}
	
	
}
