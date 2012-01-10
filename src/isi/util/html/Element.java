package isi.util.html;

import java.util.Deque;
import isi.util.Collections;
import isi.util.Iterators;
import isi.util.Strings;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import static isi.util.html.Helpers.h;

public class Element {
	
	public final static String	ATTR_CLASS	= "class",
								ATTR_ID		= "id";
	
	///////////////////////////////////////////////////////
	// state
	private final String name;
	private final List<Element> subelements = new ArrayList<>(20);
	private final Map<String, Deque<String>> attributes = new HashMap<>(5);
	
	///////////////////////////////////////////////////////
	// constructors
	public Element (final String name) {
		this.name = name;
	}

	///////////////////////////////////////////////////////
	//
	public void AddSubelement (final Element subelement) {
		subelements.add(subelement);
	}
	
	public void AddSubelements (final Iterable<? extends Element> subelements) {
		for (final Element subelement : subelements)
			AddSubelement(subelement);
	}
	
	///////////////////////////////////////////////////////
	//
	public String GetName () {
		return name;
	}
	
	///////////////////////////////////////////////////////
	//
	public Element GetChild (final int i) {
		return subelements.get(i);
	}
	
	public Element GetChild (final String name, final String... attrs) {
		for (final Element child: subelements)
			if (child.GetName().equals(name)) {
				boolean matches = true;
				for (int i = 0; matches && i < attrs.length; ++i) {
					final String wanted = attrs[i];
					final String[] pair = wanted.split("\\=", 2);
					assert pair.length == 2;
					
					final String having = child.GetAttributeValue(pair[0]);
					matches = having != null && having.equals(pair[1]);
				}
				if (matches)
					return child;
			}
		return null;
	}
	
	///////////////////////////////////////////////////////
	//
	public Element attr (final String name, final Iterable<? extends String> values) {
		final Object previous = attributes.put(name, Collections.newUnmodifiableDeque(values));
		if (previous != null)
			throw new RuntimeException(name);
		return this;
	}
	
	public Element attr (final String name, final String value) {
		return attr(name, Iterators.SingleItem(value));
	}
	
	public Deque<String> GetAttribute (final String name) {
		return attributes.get(name);
	}
	
	public String GetAttributeValue (final String name) {
		final Deque<String> values = GetAttribute(name);
		return values == null? null : values.peekFirst();
	}
	
	///////////////////////////////////////////////////////
	//
	public Element SetClass (final Iterable<? extends String> classes) {
		return attr(ATTR_CLASS, classes);
	}
	
	public Element SetClass (final String klass) {
		return attr(ATTR_CLASS, klass);
	}
	
	public String GetClass () {
		return GetAttributeValue(ATTR_CLASS);
	}
	
	public Deque<String> GetClasses () {
		return GetAttribute(ATTR_CLASS);
	}
	
	public Element SetId (final String id) {
		return attr(ATTR_ID, id);
	}
	
	public String GetId () {
		return GetAttributeValue(ATTR_ID);
	}
	
	///////////////////////////////////////////////////////
	//
	public void WriteTo (final Appendable appendable) throws IOException {
		appendable.append('<').append(name);
		for (final Map.Entry<String, Deque<String>> attr: attributes.entrySet())
			appendable.append(' ').append(attr.getKey()).append('=').append('"')
					.append(h(Strings.Join(attr.getValue(), " "))).append('"');
		
		if (subelements.isEmpty())
			appendable.append("/>");
		else {
			appendable.append('>');
			for (final Element subelement : subelements)
				subelement.WriteTo(appendable);
			appendable.append("</").append(name).append('>');
		}
	}
}
