package isi.util;

import java.util.logging.Logger;

public class Bytes {

    public static byte[] fromInt(final int i) {
        final byte[] result = new byte[] {
            (byte)((i       ) & 0xff),
            (byte)((i >> 010) & 0xff),
            (byte)((i >> 020) & 0xff),
            (byte)((i >> 030) & 0xff)
        };
        assert (
                ( (result [0] & 0xff       )) |
                ( (result [1] & 0xff) << 010) |
                ( (result [2] & 0xff) << 020) |
                ( (result [3] & 0xff) << 030)
                ) == i;
        return result;
    }

    public static byte[] fromUShort(final int h) {
        if ((h & 0x0000ffff) != h)
            throw new IllegalArgumentException("not an unsigned short: " + h);
        final byte[] result = new byte[] {
            (byte)((h & 0x000000ff)       ),
            (byte)((h & 0x0000ff00) >> 010)
        };
        assert (
                ( (result [0] & 0xff)       ) |
                ( (result [1] & 0xff) << 010)
                ) == h;
        return result;
    }

    private Bytes(){}
    private static final Logger LOG = Logger.getLogger(Bytes.class.getName());
}
