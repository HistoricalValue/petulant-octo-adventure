package isi.util;

import java.util.logging.Logger;

public class NativeTypeWrappers {

    public static byte[] toNativeByte(final Byte[] bytes) {
        final byte[] result = new byte[bytes.length];
        for (Integer i = 0; i < bytes.length; ++i)
            result[i] = bytes[i];
        return result;
    }

    public static Byte[] toObjectBytes(final byte[] bytes) {
        final Byte[] result = new Byte[bytes.length];
        for (Integer i = 0; i < bytes.length; ++i)
            result[i] = bytes[i];
        return result;
    }

    private NativeTypeWrappers() {
    }
    private static final Logger LOG = Logger.getLogger(NativeTypeWrappers.class.getName());
}
