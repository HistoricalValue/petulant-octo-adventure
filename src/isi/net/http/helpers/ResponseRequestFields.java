package isi.net.http.helpers;

import java.io.IOException;
import java.io.Writer;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class ResponseRequestFields {
	
	///////////////////////////////////////////////////////
	// state
	private final Map<String, List<String>> fields = new HashMap<>(20);
	
	///////////////////////////////////////////////////////
	//
	public ResponseRequestFields AddField (final String field, final String... values) {
		if (values.length == 0)
			throw new IllegalArgumentException("0");
		if (fields.put(field, Arrays.asList(values)) != null)
			throw new IllegalArgumentException(field);
		
		return this;
	}
	
	public boolean HasField (final String field) {
		return fields.containsKey(field);
	}
	
	public ResponseRequestFields SetValues (final String field, final String... values) {
		fields.put(field, Arrays.asList(values));
		return this;
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
}
