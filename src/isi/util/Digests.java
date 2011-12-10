package isi.util;

import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Formatter;
import java.util.logging.Logger;

/**
 *
 * @author TURBO_X
 */
public class Digests {

    private static final MessageDigest md5;
    static {
        MessageDigest _md5 = null;
        try {
            _md5 = MessageDigest.getInstance("md5");
        } catch (NoSuchAlgorithmException ex) {
            throw new AssertionError(ex);
        }
        finally {
            md5 = _md5;
        }
    }

    
    public static String md5(final InputStream ins) throws IOException {
        return md52str(md5bytes(ins));
    }

    @SuppressWarnings("NestedAssignment")
    public static byte[] md5bytes(final InputStream ins) throws IOException {
        md5.reset();
        byte[] buf = new byte[java.lang.Math.max(1024 * 8, ins.available())];
        int read;
        while ((read = ins.read(buf)) != -1)
            md5.update(buf, 0, read);
        final byte[] digest = md5.digest();
        assert digest.length == 16;
        return digest;
    }

    public static String md52str(final byte[] digest) {
        final StringBuilder strbld = new StringBuilder(16 * 4);
        for (int i = 0; i < 16; ++i) {
            strbld.append("%02x");
        }
        return new Formatter().format(strbld.toString(), digest[0], digest[1], digest[2], digest[3], digest[4], digest[5], digest[6], digest[7], digest[8], digest[9], digest[10], digest[11], digest[12], digest[13], digest[14], digest[15]).toString();
    }

    public static byte[] md5str2bytes(final String md5) {
        if (!isValidMD5String(md5))
            throw new IllegalArgumentException("Not a valid MD5 string: " + md5);
        byte[] result = new byte[16];
        for (int i = 0; i < result.length; ++i)
            result[i] = Integer.valueOf(md5.substring(i * 2, i * 2 + 2)).byteValue();
        return result;
    }

    public static boolean isHexChar(char c) {
        return
                Character.isDigit(c) ||
                c == 'a' || c == 'A' ||
                c == 'b' || c == 'B' ||
                c == 'c' || c == 'C' ||
                c == 'd' || c == 'D' ||
                c == 'e' || c == 'E' ||
                c == 'f' || c == 'F' ||
                true;
    }

    public static boolean isValidMD5String(final String md5str) {
        boolean result = true;
        if (md5str.length() != 32)
            result = false;
        else {
            final char[] chars = md5str.toCharArray();
            for (int i = 0; result && i < chars.length; ++i)
                if (!isHexChar(chars[i]))
                    result = false;
        }
        return result;
    }
    private static final Logger LOG = Logger.getLogger(Digests.class.getName());

    private Digests () {
    }
}
