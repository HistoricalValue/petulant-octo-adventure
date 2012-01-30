package isi.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;

public class IndentingAppendable implements Appendable {

	///////////////////////////////////////////////////////
	// state
	private final Appendable appendable;
	private int nesting;
	private final ArrayList<String> indents = new ArrayList<>(3);
	
	private void EnsureNestingCovered () {
		while (nesting >= indents.size())
			indents.add(indents.get(nesting - 1) + "    ");
	}
	
	private String UpdateIndent () {
		EnsureNestingCovered();
		return indents.get(nesting);
	}
	
	///////////////////////////////////////////////////////
	// constructors
	public IndentingAppendable (final Appendable appendable, final int level) {
		this.appendable = appendable;
		indents.add("\n");
		nesting = level;
	}
	
	///////////////////////////////////////////////////////
	//
	public IndentingAppendable NestIn () {
		++nesting;
		return this;
	}
	
	public IndentingAppendable NestOut () {
		if (nesting == 0)
			throw new IndexOutOfBoundsException();
		--nesting;
		return this;
	}
	
	///////////////////////////////////////////////////////
	// Implementing Appendable
	@Override
	public IndentingAppendable append (final CharSequence csq) throws IOException {
		final Matcher NL = Matchers.NL();
		appendable.append(NL.reset(csq).matches()? NL.replaceAll(UpdateIndent()) : csq);
		return this;
	}

	@Override
	public IndentingAppendable append (final CharSequence csq, final int start, final int end) throws IOException {
		return append(csq.subSequence(start, end));
	}

	@Override
	public IndentingAppendable append (final char c) throws IOException {
		if (c == '\n')
			appendable.append(UpdateIndent());
		else
			appendable.append(c);
		return this;
	}
	
}
