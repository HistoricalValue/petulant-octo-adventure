package isi.util;

import java.util.logging.Logger;

/**
 *
 * @author TURBO_X
 */
public class Math {

    public static int cmp(byte b1, byte b2) {
        return
                b1 > b2 ? -1 :
                b2 > b1 ?  1 :
                0;
    }

    public static int cmp(int b1, int b2) {
        return
                b1 > b2 ? -1 :
                b2 > b1 ?  1 :
                0;
    }

    public static int cmp(long b1, long b2) {
        return
                b1 > b2 ? -1 :
                b2 > b1 ?  1 :
                0;
    }

    public static int cmp(float b1, float b2) {
        return
                b1 > b2 ? -1 :
                b2 > b1 ?  1 :
                0;
    }

    public static int cmp(double b1, double b2) {
        return
                b1 > b2 ? -1 :
                b2 > b1 ?  1 :
                0;
    }
    private static final Logger LOG = Logger.getLogger(Math.class.getName());

    private Math () {
    }
}
