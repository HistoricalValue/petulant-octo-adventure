package isi.util.html;

import java.io.IOException;
import java.nio.CharBuffer;

public class Document implements Readable {
	
	///////////////////////////////////////////////////////
	
	private static final String
			Doctype =
			"<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \n"
			+ "		\"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">\n";
	
	///////////////////////////////////////////////////////
	// state
	private final Element html, head, body, style, script, title;
	
	public Document (final String title) {
		final ElementBuilder b = new ElementBuilder();
		this.title = b.title(title);
		style = b.link()
				.attr("rel", "stylesheet")
				.attr("type", "text/css");
		script = b.script()
				.attr("type", "text/javascript");
		head = b.head(this.title, script, style);
		body = b.body();
		html = b.html(head, body)
				.attr("xmlns", "http://www.w3.org/1999/xhtml")
				.attr("xml:lang", "en")
				.attr("lang", "en");
	}
	
	///////////////////////////////////////////////////////
	//
	public void WriteTo (final Appendable w) throws IOException {
		w.append(Doctype);
		html.WriteTo(w);
	}
	
	@Override
	public int read (final CharBuffer cb) throws IOException {
		final int pos = cb.position();
		WriteTo(cb);
		final int result = cb.position() - pos;
		assert result >= 0;
		return result;
	}
	
	///////////////////////////////////////////////////////
	// setters/getters

	public void SetStylesheet (final String stylesheet) {
		assert html.GetChild("head").GetChild("link", "rel=stylesheet") == style;
		style.attr("href", stylesheet);
	}
	public void SetJavascript (final String javascript) {
		assert html.GetChild("head").GetChild("script") == script;
		script.attr("src", javascript);
	}
	
	public Document AddElement (final Element element) {
		body.AddSubelement(element);
		return this;
	}
	
	public Document AddElements (final Element... elements) {
		for (final Element element: elements)
			AddElement(element);
		return this;
	}
	
	///////////////////////////////////////////////////////
	// utils
	public static Document FromString (final String text) {
		return new Document("Quicktext").AddElement(new ElementBuilder().pre(text));
	}
	
}
