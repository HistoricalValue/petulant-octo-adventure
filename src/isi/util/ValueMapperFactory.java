package isi.util;

public class ValueMapperFactory {

	public static <F> ValueMapper<F, String> newStringifier () {
		return new ValueMapper<F, String>() {
			@Override
			public String map (final F v) {
				return v.toString();
			}
		};
	}

	///////////////////////////////////////////////////////

	private ValueMapperFactory () {
	}
}
