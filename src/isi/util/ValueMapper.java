package isi.util;

public interface ValueMapper<FromType, ToType> {

    ToType map (FromType v);
}
