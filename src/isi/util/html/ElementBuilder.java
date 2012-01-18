package isi.util.html;

import java.util.Arrays;
import static isi.util.html.Element.EmptinessPolicy.May;
import static isi.util.html.Element.EmptinessPolicy.Must;
import static isi.util.html.Element.EmptinessPolicy.Mustnt;
import isi.util.html.Element.EmptinessPolicy;

public class ElementBuilder {

	///////////////////////////////////////////////////////
	// state

	///////////////////////////////////////////////////////
	// constructors
	public ElementBuilder () {
	}

	///////////////////////////////////////////////////////
	//
	public Element MakeElement (final String name, final EmptinessPolicy emptiness, final Element... subelements) {
		final Element element = new Element(name, emptiness);
		element.AddSubelements(Arrays.asList(subelements));
		return element;
	}

	///////////////////////////////////////////////////////
	//

	public Element text (final String text) {
		return new TextElement(text, false);
	}

	public Element html (final String html) {
		return new TextElement(html, true);
	}

	public Element p (final Element... subelements) {
		return MakeElement("p", May, subelements);
	}

	public Element p (final String text) {
		return p(text(text));
	}

	public Element li (final Element... kids) {
		return MakeElement("li", May, kids);
	}
	public Element li (final String text) {
		return li(text(text));
	}

	private Element xl (final String name) {
		return MakeElement(name, Mustnt);
	}

	private Element xl (final String name, final String... lis) {
		final Element[] subelements = new Element[lis.length];
		for (int i = 0; i < lis.length; ++i)
			subelements[i] = li(lis[i]);
		return MakeElement(name, Mustnt, subelements);
	}

	private Element xl (final String name, final Element... kids) {
		final Element[] subelements = new Element[kids.length];
		for (int i = 0; i < kids.length; ++i)
			subelements[i] = li(kids[i]);
		return MakeElement(name, Mustnt, subelements);
	}

	public Element ul (final String... lis) {
		return xl("ul", lis);
	}

	public Element ul (final Element... kids) {
		return xl("ul", kids);
	}

	public Element ul () {
		return xl("ul");
	}

	public Element ol (final String... lis) {
		return xl("ol", lis);
	}

	public Element ol () {
		return xl("ol");
	}

	public Element ol (final Element... kids) {
		return xl("ol", kids);
	}

	public Element head (final Element... kids) {
		return MakeElement("head", Mustnt, kids);
	}

	public Element title (final String title) {
		return MakeElement("title", May, text(title));
	}

	public Element html (final Element... kids) {
		return MakeElement("html", Mustnt, kids);
	}

	public Element link () {
		return MakeElement("link", Must);
	}

	public Element script () {
		return MakeElement("script", Mustnt);
	}

	public Element body (final Element... kids) {
		return MakeElement("body", Mustnt, kids);
	}

	public Element a (final String href, final String text) {
		return MakeElement("a", May, text(text)).attr("href", href);
	}

	public Element a (final String text) {
		return a(text, text);
	}

	public Element h1 (final Element... kids) {
		return MakeElement("h1", May, kids);
	}

	public Element h1 (final String text) {
		return h1(text(text));
	}

	public Element h2 (final Element... kids) {
		return MakeElement("h2", May, kids);
	}
	public Element h2 (final String text) {
		return h2(text(text));
	}

	public Element tr (final Element... kids) {
		return MakeElement("tr", Mustnt, kids);
	}

	public Element td (final Element... kids) {
		return MakeElement("td", May, kids);
	}
	public Element td (final String text) {
		return td(text(text));
	}

	public Element th (final Element... kids) {
		return MakeElement("th", May, kids);
	}
	public Element th (final String text) {
		return th(text(text));
	}

	public Element table (final Element... kids) {
		return MakeElement("table", Mustnt, kids);
	}

	public Element tbody (final Element... kids) {
		return MakeElement("tbody", Mustnt, kids);
	}

	public Element dl (final Element... kids) {
		return MakeElement("dl", Mustnt, kids);
	}

	public Element dt (final Element... kids) {
		return MakeElement("dt", May, kids);
	}

	public Element dt (final String text) {
		return dt(text(text));
	}

	public Element dd (final Element... kids) {
		return MakeElement("dd", May, kids);
	}

	public Element dd (final String text) {
		return dd(text(text));
	}

	public Element div (final Element... kids) {
		return MakeElement("div", May, kids);
	}

	public Element div (final String text) {
		return div(text(text));
	}

	public Element pre (final Element... kids) {
		return MakeElement("pre", May, kids);
	}

	public Element pre (final String text) {
		return pre(text(text));
	}

	///////////////////////////////////////////////////////
	//

	private Element xl (final String name, final Iterable<? extends String> texts) {
		Element xl = MakeElement(name, Mustnt);

		for (final String text: texts)
			xl.AddSubelement(li(text));

		return xl;
	}

	public Element ol (final Iterable<? extends String> texts) {
		return xl("ol", texts);
	}
}
