package isi.util.zip;

import isi.data_structures.ExpandableByteCyclicBuffer;
import isi.util.ByteArrayTransformer;
import isi.util.OutputBufferTooShortException;
import isi.util.TransformationException;
import java.util.logging.Logger;
import java.util.zip.Deflater;

public class DeflaterByteArrayTransformer extends ByteArrayTransformer {

    // State
    private boolean finalised = false;
    private final ExpandableByteCyclicBuffer compressed_data_buffer = new ExpandableByteCyclicBuffer(8*1024);
    private final Deflater deflator;
    public DeflaterByteArrayTransformer(final Deflater _deflator) {
        deflator = _deflator;
    }
    public DeflaterByteArrayTransformer() {
        this(new Deflater(Deflater.BEST_COMPRESSION, false));
    }
    
    @Override
    protected int transform(final byte[] inbuf, final int inbuf_off,
            final int len, final byte[] outbuf, final int outbuf_off,
            final int outbuf_len)
    {
        assert deflator.needsInput();
        assert !deflator.finished();
        final int _outbuf_len = Math.min(outbuf_len, outbuf.length-1 -outbuf_off +1);

        int result;
        if (len > 0) {
            p_compress_data(inbuf, inbuf_off, len, outbuf, outbuf_off, _outbuf_len);
            result = compressed_data_buffer.read(outbuf, outbuf_off, _outbuf_len);
            assert result >= 0;
            // nothing till return()
        }
        else
            result = 0;
            // nothing till return()
        
        // deflator.needsInput()
        // result >= 0
        return result;
    }

    @Override
    protected int transformFinal(final byte[] inbuf, final int inbuf_off,
            final int len, final byte[] outbuf, final int outbuf_off,
            final int outbuf_len)
    throws TransformationException
    {
        assert deflator.needsInput();
        final int _outbuf_len = Math.min(outbuf_len, outbuf.length-1 -outbuf_off +1);
        int result;
        
        if (!finalised) {
            assert !deflator.finished();
            deflator.finish();
            p_compress_data(inbuf, inbuf_off, len, outbuf, outbuf_off, _outbuf_len);
            finalised = true;
        }
        
        assert deflator.finished();
        assert deflator.needsInput();
        result = p_writeAllToOut(outbuf, outbuf_off, _outbuf_len);
        // nothing till return()

        // deflator.needsInput()
        // result >= 0
        return result;
    }

    private void p_compress_data(
              final byte[] inbuf
            , final int inbuf_off
            , final int len
            , final byte[] outbuf
            , final int outbuf_off
            , final int outbuf_len // checked
    ) throws
            ArrayIndexOutOfBoundsException
    {
        assert outbuf_len <= outbuf.length-1 -outbuf_off +1;
        if (inbuf_off < 0)
            throw new ArrayIndexOutOfBoundsException("inbuf_off(" + inbuf_off + ") < 0");
        if (inbuf_off + len > inbuf.length)
            throw new ArrayIndexOutOfBoundsException("inbuf_off(" + inbuf_off + ") + len(" + len + ") > inbuf.length(" + inbuf.length + ")");
        if (outbuf_off + outbuf_len > outbuf.length)
            throw new ArrayIndexOutOfBoundsException("outbuf_off(" + outbuf_off + ") + outbuf_len(" + outbuf_len + ") > " + "outbuf.length(" + outbuf.length + ")");
        deflator.setInput(inbuf, inbuf_off, len);
        p_deflate(outbuf, outbuf_off, outbuf_len);
        assert deflator.needsInput();
    }

    private void p_deflate(final byte[] outbuf, final int outbuf_off, int len) {
        assert len <= outbuf.length-1 -outbuf_off +1;
        int written = 0;
        do {
            written = deflator.deflate(outbuf, outbuf_off, len);
            compressed_data_buffer.append(outbuf, outbuf_off, written);
        } while (written != 0 || !deflator.needsInput());
    }

    private int p_writeAllToOut(
              final byte[] outbuf
            , final int outbuf_off
            , final int outbuf_len
    ) throws OutputBufferTooShortException {
        assert outbuf_len <= outbuf.length-1 -outbuf_off +1;
        int result;
        final int required_space = compressed_data_buffer.available();
        final int available_space = outbuf_len;
        if (available_space < required_space)
            throw new OutputBufferTooShortException();

        result = compressed_data_buffer.read(outbuf, outbuf_off, outbuf_len);
        assert compressed_data_buffer.available() == 0;

        return result;
    }
    private static final Logger LOG = Logger.getLogger(DeflaterByteArrayTransformer.class.getName());

}
