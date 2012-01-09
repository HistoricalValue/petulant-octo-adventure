package isi.util.charstreams;

import java.io.IOException;

public class MultiAppendableDelegate implements Appendable {

	///////////////////////////////////////////////////////
	// state
	private final Iterable<? extends Appendable> appendables;
	
	///////////////////////////////////////////////////////
	//
	public MultiAppendableDelegate (final Iterable<? extends Appendable> appendables) {
		this.appendables = isi.util.Collections.newUnmodifiableList(appendables);
	}
	public MultiAppendableDelegate (Appendable... appendables) {
		this(java.util.Arrays.asList(appendables));
	}
	
	@Override
	public Appendable append (final CharSequence csq) throws IOException {
		for (final Appendable a: appendables)
			a.append(csq);
		return this;
	}

	@Override
	public Appendable append (final CharSequence csq, final int start, final int end) throws IOException {
		for (final Appendable a: appendables)
			a.append(csq, start, end);
		return this;
	}

	@Override
	public Appendable append (final char c) throws IOException {
		for (final Appendable a: appendables)
			a.append(c);
		return this;
	}
	
}
