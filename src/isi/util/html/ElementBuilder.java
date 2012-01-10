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
	
	public Element li (final Element... kids) {
		return MakeElement("li", kids);
	}
	public Element li (final String text) {
		return li(text(text));
	}
	
	private Element xl (final String name, final String... lis) {
		final Element[] subelements = new Element[lis.length];
		for (int i = 0; i < lis.length; ++i)
			subelements[i] = li(lis[i]);
		return MakeElement(name, subelements);
	}
	
	private Element xl (final String name, final Element... kids) {
		final Element[] subelements = new Element[kids.length];
		for (int i = 0; i < kids.length; ++i)
			subelements[i] = li(kids[i]);
		return MakeElement(name, subelements);
	}
	
	public Element ul_lis (final String... lis) {
		return xl("ul", lis);
	}
	
	public Element ul (final Element... kids) {
		return xl("ul", kids);
	}
	
	public Element ol_lis (final String... lis) {
		return xl("ol", lis);
	}
	
	public Element ol (final Element... kids) {
		return xl("ol", kids);
	}
	
	public Element head (final Element... kids) {
		return MakeElement("head", kids);
	}
	
	public Element title (final String title) {
		return MakeElement("title", text(title));
	}
	
	public Element html (final Element... kids) {
		return MakeElement("html", kids);
	}
	
	public Element link () {
		return MakeElement("link");
	}
	
	public Element script () {
		return MakeElement("script", text(""));
	}
	
	public Element body (final Element... kids) {
		return MakeElement("body", kids);
	}
	
	public Element a (final String href, final String text) {
		return MakeElement("a", text(text)).attr("href", href);
	}
	
	public Element a (final String text) {
		return a(text, text);
	}
	
	public Element h1 (final Element... kids) {
		return MakeElement("h1", kids);
	}
	
	public Element h1 (final String text) {
		return h1(text(text));
	}
	
	public Element tr (final Element... kids) {
		return MakeElement("tr", kids);
	}
	
	public Element td (final Element... kids) {
		return MakeElement("td", kids);
	}
	public Element td (final String text) {
		return td(text(text));
	}
	
	public Element th (final Element... kids) {
		return MakeElement("th", kids);
	}
	public Element th (final String text) {
		return th(text(text));
	}
	
	public Element table (final Element... kids) {
		return MakeElement("table", kids);
	}
	
	public Element tbody (final Element... kids) {
		return MakeElement("tbody", kids);
	}
	
	public Element dl (final Element... kids) {
		return MakeElement("dl", kids);
	}
	
	public Element dt (final Element... kids) {
		return MakeElement("dt", kids);
	}
	
	public Element dt (final String text) {
		return dt(text(text));
	}
	
	public Element dd (final Element... kids) {
		return MakeElement("dd", kids);
	}
	
	public Element dd (final String text) {
		return dd(text(text));
	}
	
	public Element div (final Element... kids) {
		return MakeElement("div", kids);
	}
	
	public Element div (final String text) {
		return div(text(text));
	}
	
	///////////////////////////////////////////////////////
	//
	
	private Element xl (final String name, final Iterable<? extends String> texts) {
		Element xl = MakeElement(name);
		
		for (final String text: texts)
			xl.AddSubelement(li(text));
		
		return xl;
	}
	
	public Element ol (final Iterable<? extends String> texts) {
		return xl("ol", texts);
	}
}
