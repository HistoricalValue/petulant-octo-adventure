package isi.util;

import java.util.logging.Logger;

/**
 * @date 2010-06-13 17.18
 * @author TURBO_X
 */
public class Random {
    private Random() {}

    /**
     * fills the given byte array with random values from the given
     * Random instance.
     * @param r
     * @param arr
     * @return the provided _arr_ array
     */
    public static byte[] fillWithRandomBytes(java.util.Random r, byte[] arr) {
        r.nextBytes(arr);
        return arr;
    }
    /**
     * fill the given array with random bytes and returns it.
     * @param arr
     * @return the given _arr_
     */
    public static byte[] fillWithRandomBytes(byte[] arr) {
        return fillWithRandomBytes(
                new java.util.Random(System.currentTimeMillis()),
                arr
        );
    }
    private static final Logger LOG = Logger.getLogger(Random.class.getName());

}
