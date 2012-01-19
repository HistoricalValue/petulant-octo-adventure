package isi.util;

public interface Factory<T> {

	T Create () throws FactoryException;

}
