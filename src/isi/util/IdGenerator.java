package isi.util;

public class IdGenerator {

	///////////////////////////////////////////////////////
	// state
	private final String prefix, suffix;
	private int seed = 0;

	///////////////////////////////////////////////////////
	// constructors
	public IdGenerator (final String prefix, final String suffix) {
		this.prefix = prefix;
		this.suffix = suffix;
	}

	///////////////////////////////////////////////////////
	//
	@SuppressWarnings("ValueOfIncrementOrDecrementUsed")
	public String next () {
		return prefix + Integer.toString(seed++) + suffix;
	}

	public void reset () {
		seed = 0;
	}

}
