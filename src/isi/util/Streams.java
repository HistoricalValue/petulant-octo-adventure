package isi.util;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.logging.Logger;

/**
 *
 * @author TURBO_X
 */
public class Streams {
    @SuppressWarnings("NestedAssignment")
    public static void transfuse(final InputStream ins, final OutputStream outs) throws IOException {
        final byte buf[] = new byte[1 << 20];
        int read = -1;
        while ((read = ins.read(buf)) != -1)
            outs.write(buf, 0, read);
        outs.flush();
    }

    public static InputStream ensureMarkable(final InputStream ins) {
        InputStream result;
        if (ins.markSupported())
            result = ins;
        else
            result = new BufferedInputStream(ins);
        assert result.markSupported();
        return result;
    }

    public static InputStream ensureMarkableAndMark(final InputStream ins) {
        return ensureMarkableAndMark(ins, Integer.MAX_VALUE);
    }

    public static InputStream ensureMarkableAndMark(final InputStream ins, int readlength) {
        final InputStream result = ensureMarkable(ins);
        result.mark(readlength);
        return result;
    }

    public static int cmp(InputStream ins1, InputStream ins2) throws IOException {
        boolean done = false;
        int result = 0;
        int counter = 0, total1 = 0, total2 = 0; // for debugging
        byte[] buf1 = new byte[1 << 15], buf2 = new byte[buf1.length];
        int i1 = 0, i2 = 0;
        while (! done) {
            final int
                    r1 = tryToFill(ins1, buf1, i1, buf1.length - i1, 0),
                    r2 = tryToFill(ins2, buf2, i2, buf2.length - i2, 0);
            i1 += r1 == -1 ? 0 : r1;
            assert i1 <= buf1.length;
            i2 += r2 == -1 ? 0 : r2;
            assert i2 <= buf2.length;
            total1 += r1 == -1 ? 0 : r1; total2 += r2 == -1 ? 0 : r2;
            final int cmp_len = java.lang.Math.min(i1, i2);
            if (cmp_len == 0) {
                assert r1 == -1 || r2 == -1;
                result = Math.cmp(r1, r2);
                done = true;
            }
            else {
                result = isi.util.Arrays.cmp(buf1, 0, buf2, 0, cmp_len);
                if (result != 0)
                    done = true;
                else {
                    i1 -= cmp_len;
                    assert i1 >= 0;
                    i2 -= cmp_len;
                    assert i2 >= 0;
                    System.arraycopy(buf1, cmp_len, buf1, 0, i1);
                    System.arraycopy(buf2, cmp_len, buf2, 0, i2);
                }
            }
            ++counter;
        }
        return result;
    }

    public static byte[] readAll(final InputStream ins) throws IOException {
        final ByteArrayOutputStream baouts = new ByteArrayOutputStream(ins.available());
        transfuse(ins, baouts);
        return baouts.toByteArray();
    }
    public static byte[] readAllFromFile(final String filepath) throws IOException {
//        @SuppressWarnings("static-access")
        final byte[] result = readAllFromFile(new File(filepath));
        return result;
    }
    public static byte[] readAllFromFile(final File file) throws FileNotFoundException, IOException {
//        @SuppressWarnings("static-access")
        final byte[] result = readAll(new BufferedInputStream(new FileInputStream(file)));
        return result;
    }

    public static byte[] read(final InputStream ins, final int len) throws IOException {
        final int buflen = java.lang.Math.min(ins.available(), len);
        final ByteArrayOutputStream baouts = new ByteArrayOutputStream(buflen);
        final byte[] buf = new byte[buflen];

        int copied = 0;
        boolean eof = false;
        while (copied < len && !eof) {
            int read = ins.read(buf, 0, len - copied);
            if (read != -1) {
                baouts.write(buf, 0, read);
                copied += read;
            }
            else
                eof = true;
        }

        baouts.flush();
        return baouts.toByteArray();
    }
    public static byte[] read(final File file, final int len) throws FileNotFoundException, IOException {
//        @SuppressWarnings("static-access")
        final byte[] result = read(new BufferedInputStream(new FileInputStream(file)), len);
        return result;
    }
    public static byte[] read(final String filepath, final int len) throws FileNotFoundException, IOException {
//        @SuppressWarnings("static-access")
        final byte[] result = read(new BufferedInputStream(new FileInputStream(filepath)), len);
        return result;
    }

    public static int MaxCountAllTries () {
        return 5;
    }

    /** WARNING: depletes the stream
     * @param ins
     * @return
     * @throws IOException
     */
    public static long countAll(final InputStream ins) throws IOException {
        final long skip_len = java.lang.Math.max(ins.available(), 1);
        long result = 0;
        boolean done = false;
        int tries = MaxCountAllTries();
        for (long r = ins.skip(skip_len); ! done; r = ins.skip(skip_len)) {
            result += r;
            if (r == 0) {
                long extra_r = ins.read(new byte[1 << 16]);
                if (extra_r == -1)
                    done = true;
                else {
                    result += extra_r;
                    extra_r = ins.skip(skip_len);
                    --tries;
                    if (extra_r == 0 && tries == 0)
                        done = true;
                }
            }
        }
        return result;
    }

    public static int tryToFill(final InputStream ins, final byte[] buf, final int off, final int len, final int number_of_tries) throws IOException {
        if (off + len > buf.length)
            throw new IndexOutOfBoundsException("off("+off+") + len("+len+") >= buf.length("+buf.length+")");

        int tries = 0;
        int offset = off;
        boolean eof = false;
        boolean haveMoreTries = number_of_tries == 0 || tries < number_of_tries;
        boolean haveMoreSpace = offset-off < len;
        while (haveMoreTries && haveMoreSpace && !eof) {
            int r = ins.read(buf, offset, len - (offset-off));
            if (r == -1)
                eof = true;
            else
                offset += r;
            ++tries;
            
            haveMoreTries = number_of_tries == 0 || tries < number_of_tries;
            haveMoreSpace = offset-off < len;
        }
        return offset == off && eof? -1 : offset-off;
    }
    private static final Logger LOG = Logger.getLogger(Streams.class.getName());

    private Streams () {
    }
}
