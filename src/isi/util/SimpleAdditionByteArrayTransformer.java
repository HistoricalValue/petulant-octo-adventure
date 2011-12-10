package isi.util;

import java.util.logging.Logger;

public class SimpleAdditionByteArrayTransformer extends ByteArrayTransformer {

    public SimpleAdditionByteArrayTransformer(final byte adjuster) {
        adj = adjuster;
    }
    // State
    private final byte adj;

    @Override
    protected int transform(final byte[] inbuf, final int inbuf_off, final int inbuf_len, final byte[] outbuf, final int outbuf_off, final int outbuf_len) throws TransformationException {
        if (inbuf_len > outbuf_len)
            throw new OutputBufferTooShortException("inbuf_len(" +
                    inbuf_len + ") > outbuf_len(" + outbuf_len + ")");
        if (inbuf_off + inbuf_len > inbuf.length)
            throw new ArrayIndexOutOfBoundsException(
                    "inbuf_off(" + inbuf_off + ") + " +
                    "inbuf_len(" + inbuf_len + ") >= " +
                    "inbuf.length(" + inbuf.length + ")");
        if (outbuf_off + inbuf_len > outbuf.length)
            throw new OutputBufferTooShortException("outbuf_off" +
                    "(" + outbuf_off + ") + inbuf_len(" + inbuf_len + ") " +
                    "> outbuf.length(" + outbuf.length + ")");
        int i;
        for (i = 0; i < inbuf_len; ++i)
            outbuf[outbuf_off + i] = (byte)(inbuf[inbuf_off + i] + adj);
        assert i == inbuf_len;
        return inbuf_len;
    }

    @Override
    protected int transformFinal(final byte[] inbuf, final int inbuf_off, final int inbuf_len, final byte[] outbuf, final int outbuf_off, final int outbuf_len) throws TransformationException {
        return transform(inbuf, inbuf_off, inbuf_len, outbuf, outbuf_off, outbuf_len);
    }
    private static final Logger LOG = Logger.getLogger(SimpleAdditionByteArrayTransformer.class.getName());

}
