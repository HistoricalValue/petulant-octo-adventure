package isi.util;

public class Predicates {
	
	public static <T> Predicate<T> newEquality (final T value) {
		return new Predicate<T>() {
			@Override
			public boolean accept (final T obj) {
				return value.equals(obj);
			}
		};
	}
	
	
	private Predicates () {
	}
}
