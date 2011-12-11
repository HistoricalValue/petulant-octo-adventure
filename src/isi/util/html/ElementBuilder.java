package isi.util.html;

import java.util.Arrays;

public class ElementBuilder {
	
	///////////////////////////////////////////////////////
	// state
	
	///////////////////////////////////////////////////////
	// constructors
	public ElementBuilder () {
	}
	
	///////////////////////////////////////////////////////
	//
	public Element MakeElement (final String name, final Element... subelements) {
		final Element element = new Element(name);
		element.AddSubelements(Arrays.asList(subelements));
		return element;
	}
	
	///////////////////////////////////////////////////////
	//

	public Element text (final String text) {
		return new TextElement(text);
	}
	
	public Element p (final Element... subelements) {
		return MakeElement("p", subelements);
	}
	
	public Element li (final String text) {
		return MakeElement("li", text(text));
	}
	
	public Element li (final Element... kids) {
		return MakeElement("li", kids);
	}
	
	public Element ul (final String... lis) {
		final Element[] subelements = new Element[lis.length];
		for (int i = 0; i < lis.length; ++i)
			subelements[i] = li(lis[i]);
		return MakeElement("ul", subelements);
	}
	
	public Element ul (final Element... kids) {
		final Element[] subelements = new Element[kids.length];
		for (int i = 0; i < kids.length; ++i)
			subelements[i] = li(kids[i]);
		return MakeElement("ul", subelements);
	}
	
	public Element head (final Element... kids) {
		return MakeElement("head", kids);
	}
	
	public Element html (final Element... kids) {
		return MakeElement("html", kids);
	}
	
	public Element link () {
		return MakeElement("link");
	}
	
	public Element body (final Element... kids) {
		return MakeElement("body", kids);
	}
	
	public Element a (final String text) {
		return MakeElement("a", text(text)).attr("href", Helpers.h(text));
	}
	
	public Element h1 (final Element... kids) {
		return MakeElement("h1", kids);
	}
	
	public Element h1 (final String text) {
		return h1(text(text));
	}
}
