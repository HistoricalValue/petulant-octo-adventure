package isi.data_structures;

import java.util.logging.Logger;

public class ExpandableByteCyclicBuffer extends ByteCyclicBuffer {

    public ExpandableByteCyclicBuffer(final int buffer_length) {
        super(buffer_length);
    }

    @Override
    public ExpandableByteCyclicBuffer append(byte[] block) {
        return append(block, 0, block.length);
    }

    @Override
    public ExpandableByteCyclicBuffer append(byte[] block, int off, int len) {
        boolean append_successful = false;
        while (!append_successful)
        try {
            super.append(block, off, len);
            append_successful = true;
        } catch (final StorageException ex) {
            p_expandBuffer();
        }
        return this;
    }

    private void p_expandBuffer() {
        final byte[] _buffer = new byte[(int)Math.ceil(Math.max(buffer.length, 1) * 1.2)];
        if (isStore_index_wrapped()) {
            final int read_to_buffer_end_length = buffer.length - 1 - getRead_index() + 1;
            final int buffer_start_to_store_length = getStore_index() - 1 - 0 + 1;
            System.arraycopy( buffer, getRead_index(),_buffer, 0, read_to_buffer_end_length);
            System.arraycopy(buffer, 0, _buffer, read_to_buffer_end_length, buffer_start_to_store_length);
            setRead_index(0);
            setStore_index(read_to_buffer_end_length + buffer_start_to_store_length);
        } else {
            final int read_to_store_length = getStore_index() - 1 - getRead_index() + 1;
            System.arraycopy( buffer, getRead_index(),_buffer, 0, read_to_store_length);
            setRead_index(0);
            setStore_index(read_to_store_length);
        }
        setStore_index_wrapped(false);
        setFree_space(getFree_space() + _buffer.length - buffer.length);
        buffer = (_buffer);
        assert p_invariablesHold();
    }

    @Override
    public ExpandableByteCyclicBuffer appendva(byte... block) throws StorageException {
        return append(block, 0, block.length);
    }

    @Override
    public ExpandableByteCyclicBuffer clone() throws CloneNotSupportedException {
        return (ExpandableByteCyclicBuffer)super.clone();
    }
    private static final Logger LOG = Logger.getLogger(ExpandableByteCyclicBuffer.class.getName());
}
