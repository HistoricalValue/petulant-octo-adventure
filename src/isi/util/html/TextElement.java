package isi.util.html;

import java.io.IOException;
import java.util.Iterator;
import static java.util.Objects.requireNonNull;

public class TextElement extends Element {
	
	///////////////////////////////////////////////////////
	// state
	private final String text;
	
	///////////////////////////////////////////////////////
	// constructors 
	public TextElement (final String text) {
		super("");
		this.text = requireNonNull(text);
	}
	
	///////////////////////////////////////////////////////
	//
	@Override
	public void WriteTo (final Appendable appendable) throws IOException {
		appendable.append(Helpers.h(text));
	}

	@Override
	public void AddSubelement (final Element subelement) {
		throw new RuntimeException("A text element cannot have subelements");
	}

	@Override
	public void AddSubelements (final Iterable<? extends Element> subelements) {
		final Iterator<? extends Element> iter = subelements.iterator();
		AddSubelement(iter.hasNext()? iter.next() : null);
	}

	@Override
	public Element attr (String name, String value) {
		throw new RuntimeException("A text element cannot have attributes");
	}
	
	
	
}
