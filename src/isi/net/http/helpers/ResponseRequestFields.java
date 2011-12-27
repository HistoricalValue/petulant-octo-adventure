package isi.net.http.helpers;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class ResponseRequestFields implements Iterable<Map.Entry<String, List<String>>> {
	
	///////////////////////////////////////////////////////
	// state
	private final Map<String, List<String>> fields = new HashMap<>(20);
	
	///////////////////////////////////////////////////////
	//
	public ResponseRequestFields AddField (final String field, final Collection<String> values) {
		if (fields.containsKey(field))
			throw new IllegalArgumentException(field);
		
		return SetValues(field, values);
	}
	public ResponseRequestFields AddField (final String field, final String... values) {
		return AddField(field, Arrays.asList(values));
	}
	
	public boolean HasField (final String field) {
		return fields.containsKey(field);
	}
	
	public ResponseRequestFields SetValues (final String field, final Collection<String> values) {
		if (values.isEmpty())
			throw new IllegalArgumentException("isEmpty()");
		
		fields.put(field, new ArrayList<>(values));
		
		return this;
	}
	public ResponseRequestFields SetValues (final String field, final String... values) {
		return SetValues(field, Arrays.asList(values));
	}
	
	///////////////////////////////////////////////////////
	//
	
	@Override
	public Iterator<Entry<String, List<String>>> iterator () {
		return Collections.unmodifiableMap(fields).entrySet().iterator();
	}
	
	///////////////////////////////////////////////////////
	//
	public Writer WriteTo (final Writer w) throws IOException {
		for (Map.Entry<String, List<String>> entry : fields.entrySet()) {
			final Iterator<String> vali = entry.getValue().iterator();
			assert vali.hasNext();
			
			w.append(entry.getKey()).append(": ").append(vali.next());
			while (vali.hasNext())
				w.append("; ").append(vali.next());
			w.append("\r\n");
		}
		
		return w;
	}
	
	///////////////////////////////////////////////////////
	// private 	
}
