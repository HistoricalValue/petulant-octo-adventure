package isi.net.http.helpers;

import isi.util.Strings;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

public class ResponseRequestFields implements Iterable<Map.Entry<String, String>> {

	///////////////////////////////////////////////////////
	// state
	private final Map<String, String> fields = new HashMap<>(20);

	///////////////////////////////////////////////////////
	//
	public ResponseRequestFields AddField (final String field, final String value) {
		if (fields.containsKey(field))
			throw new IllegalArgumentException(field);

		return SetValue(field, value);
	}

	public boolean HasField (final String field) {
		return fields.containsKey(field);
	}

	public String GetField (final String field) {
		return fields.get(field);
	}

	public ResponseRequestFields SetValue (final String field, final String value) {
		if (value.isEmpty())
			throw new IllegalArgumentException("isEmpty()");

		fields.put(field, value);

		return this;
	}
	public ResponseRequestFields SetValue (final String field, final String... values) {
		return SetValue(field, Arrays.asList(values));
	}
	public ResponseRequestFields SetValue (final String field, final Iterable<? extends String> values) {
		return SetValue(field, Strings.Join(values, "; "));
	}

	///////////////////////////////////////////////////////
	//

	@Override
	public Iterator<Entry<String, String>> iterator () {
		return Collections.unmodifiableMap(fields).entrySet().iterator();
	}

	///////////////////////////////////////////////////////
	//
	public StringBuilder WriteTo (final StringBuilder bob, final String EOL) {
		for (Map.Entry<String, String> entry : this)
			bob.append(entry.getKey()).append(": ").append(entry.getValue()).append(EOL);

		return bob;
	}

	public Appendable WriteTo (final Appendable w, final String EOL) throws IOException {
		for (Map.Entry<String, String> entry : this)
			w.append(entry.getKey()).append(": ").append(entry.getValue()).append(EOL);

		return w;
	}

	///////////////////////////////////////////////////////
	// Object
	@Override
	public String toString () {
		return WriteTo(new StringBuilder(1 << 14), "\n").toString();
	}

	///////////////////////////////////////////////////////
	// private
}
