package isi.util.html;

import java.io.IOException;
import static java.util.Objects.requireNonNull;

public class TextElement extends Element {
	
	///////////////////////////////////////////////////////
	// state
	private final String text;
	private final boolean isHtml;
	
	///////////////////////////////////////////////////////
	// constructors 
	public TextElement (final String text, final boolean isHtml) {
		super("", EmptinessPolicy.Must);
		this.text = requireNonNull(text);
		this.isHtml = isHtml;
	}
	
	///////////////////////////////////////////////////////
	//
	@Override
	public void WriteTo (final Appendable appendable) throws IOException {
		appendable.append(isHtml? text : Helpers.h(text));
	}

	@Override
	public Element attr (String name, String value) {
		throw new RuntimeException("A text element cannot have attributes");
	}	
	
}
