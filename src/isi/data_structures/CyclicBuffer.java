package isi.data_structures;

import java.util.logging.Logger;

public class CyclicBuffer<T> implements Cloneable  {

    public CyclicBuffer (int buffer_length) {
        this(new Object[buffer_length]);
    }
    // State
    // --- protected
        @SuppressWarnings("ProtectedField")
    protected Object[] buffer;
    private int read_index;
    private int store_index;
    private int free_space;
    private boolean store_index_wrapped; // has store_index wrapped?
    // --- private
    private int snap_read_index, snap_store_index, snap_free_space;
    private boolean snap_store_index_wrapped, snapshot_available;

//    @Override
//    public CyclicBuffer<T> appendva (final byte... block) throws StorageException {
//        return append(block);
//    }

    public CyclicBuffer<T> appendva (final T o0) throws StorageException {
        return _append(new Object[]{o0}, 0, 1);
    }

    public CyclicBuffer<T> append (final T[] block) throws StorageException {
        return _append(block, 0, block.length);
    }

    public CyclicBuffer<T> append (final T[] block, final int off, final int len) throws StorageException {
        return _append(block, off, len);
    }

    private CyclicBuffer<T> _append (   final Object[] block, final int off,
                                        final int len)
            throws  StorageException
    {
        snapshot_available = false;
        final int _len = Math.min(len, block.length);
        if (p_lengthFits(_len))
            p_appendBlock(block, off, _len);
        else
            throw new StorageException("Not enough space to append a block of " +
                    "length of " + _len);
        return this;
    }

    public int capacity () {
        assert p_invariablesHold();
        return buffer.length;
    }

    public int freeSpace () {
        assert p_invariablesHold();
        return getFree_space();
    }

    public int available () {
        assert p_invariablesHold();
        return isStore_index_wrapped()?
                (buffer.length - getRead_index()) + (getStore_index()) :
                getStore_index() - getRead_index()
        ;
    }

    public int read (final T[] dest, final int off, final int len) {
        if (off + len > dest.length)
            throw new ArrayIndexOutOfBoundsException("off(" + off + ") + len(" +
                    len + ") > dest.length(" + dest.length + ")");
        assert p_invariablesHold();
        final int read_length = Math.min(len, available());
        int result = 0;
        if (isStore_index_wrapped()) {
            final int old_read_index = getRead_index(); // for debugging/asserting purposes
            final int to_read = Math.min(read_length, buffer.length - getRead_index());
            System.arraycopy(buffer, getRead_index(), dest, off, to_read);
            setRead_index(getRead_index() + to_read);
            setFree_space(getFree_space() + to_read);
            result += to_read;
            assert p_invariablesHold();
            assert getRead_index() <= buffer.length;
            if (getRead_index() == buffer.length) {
                setRead_index(0);
                setStore_index_wrapped(false);
                assert p_invariablesHold();
            }
            final int to_read2 = read_length - to_read;
            if (to_read2 > 0) {
                System.arraycopy(buffer, getRead_index(), dest, off + to_read, to_read2);
                setRead_index(getRead_index() + to_read2);
                setFree_space(getFree_space() + to_read2);
                result += to_read2;
                assert p_invariablesHold();
            }
            assert to_read + to_read2 == read_length;
            assert (old_read_index + read_length) % buffer.length == getRead_index();
        }
        else {
            System.arraycopy(buffer, getRead_index(), dest, off, read_length);
            setRead_index(getRead_index() + read_length);
            setFree_space(getFree_space() + read_length);
            result += read_length;
            assert p_invariablesHold();
        }
        return result;
    }
    private boolean p_lengthFits (final int length) {
        assert p_invariablesHold();
        return length <= getFree_space();
    }
    private void p_appendBlock (final Object[] block, final int off, final int len) {
        assert p_invariablesHold();
        assert p_lengthFits(len);
        if (isStore_index_wrapped()) {
            final int length_to_copy = len;
            System.arraycopy(block, off, buffer, getStore_index(), length_to_copy);
            setFree_space(getFree_space() - length_to_copy);
            setStore_index(getStore_index() + length_to_copy);
            assert p_invariablesHold();
        }
        else {
            int copy_length = len;
            final int to_copy = Math.min(copy_length, buffer.length - getStore_index());
            final int old_free_space = getFree_space(), old_store_index = getStore_index();
            System.arraycopy(block, off, buffer, getStore_index(), to_copy);
            setFree_space(getFree_space() - to_copy);
            setStore_index(getStore_index() + to_copy);
            assert p_invariablesHold();
            assert getStore_index() <= buffer.length;
            copy_length -= to_copy;
            assert copy_length >= 0;
            if (getStore_index() == buffer.length) {
                setStore_index_wrapped(true);
                setStore_index(0);
                assert p_invariablesHold();
            }
            if (copy_length > 0) {
                System.arraycopy(block, off + to_copy, buffer, getStore_index(), copy_length);
                setStore_index(getStore_index() + copy_length);
                setFree_space(getFree_space() - copy_length);
                assert p_invariablesHold();
            }
            assert len == copy_length + to_copy;
            assert old_free_space - len == getFree_space();
            assert (old_store_index + len) % buffer.length == getStore_index();
        }
    }
    protected boolean p_invariablesHold () {
        final boolean
                  correct_ordering_of_indeces = isStore_index_wrapped()?
                        getStore_index() <= getRead_index() :
                        getRead_index()  <= getStore_index()
                , correct_free_length_and_index_distance = isStore_index_wrapped()?
                        (getStore_index() + getFree_space()) == getRead_index() :
                        (getStore_index() + getFree_space()) % buffer.length == getRead_index()
                , all_less_than_buffer_length =
                        getStore_index() < buffer.length     &&
                        getRead_index()  < buffer.length     &&
                        true
        ;

        final boolean result =
                correct_ordering_of_indeces             &&
                correct_free_length_and_index_distance  &&
                true
        ;

        if (!result) {
            final boolean _result = result; // debugging purposes
        }

        return result;
    }

    @Override
    public String toString () {
        String result;
        int available = available();
        if (available > 0) {
            final String firstString = buffer[0].toString();
            --available;
            // initialise the string builder's capacity according to the
            // length of the string representation of the first element
            // times the buffer size (in theory it's more than needed,
            // but string_length times available() will almost definitely
            // result in the builder being expanded internally, so let's
            // avoid that)
            final StringBuilder strbld = new StringBuilder(
                    firstString.length() * buffer.length);

            strbld.append('{').append(firstString);

            int i = (getRead_index() + 1) % buffer.length;
            while (available > 0) {
                strbld.append(", ").append(buffer[i].toString());
                --available;
                i = (i+1) % buffer.length;
            }

            if (false)
                result = strbld.append('}').toString();
            else
                result = "<" + getRead_index() + ':' + getStore_index() +
                        (isStore_index_wrapped()? "(wrapped) ":" ")+
                        java.util.Arrays.toString(buffer) + '>';
        }
        else
            result = "{}";

        return result;
    }

    public CyclicBuffer<T> reset () {
        setRead_index(0);
        setStore_index(0);
        setFree_space(buffer.length);
        setStore_index_wrapped(false);
        assert p_invariablesHold();
        return this;
    }

    public CyclicBuffer<T> snapshot () {
        snap_read_index = getRead_index();
        snap_store_index = getStore_index();
        snap_free_space = getFree_space();
        snap_store_index_wrapped = isStore_index_wrapped();
        snapshot_available = true;
        return this;
    }

    public CyclicBuffer<T> restore () {
        if (snapshot_available) {
            setRead_index(snap_read_index);
            setStore_index(snap_store_index);
            setFree_space(snap_free_space);
            setStore_index_wrapped(snap_store_index_wrapped);
        }
        else
            throw new IllegalStateException("no snapshot available or previous" +
                    "snapshot has been invalidated after an append() method" +
                    "call");
        return this;
    }

    public boolean restoreAvailable () {
        return snapshot_available;
    }

    public int skip (final int l) {
        assert p_invariablesHold();
        final int _l = Math.min(available(), l);
        int skip_remaining = _l;
        final int previous_free_space = getFree_space();
        final int previous_available = available();
        if (isStore_index_wrapped()) {
            final int read_to_buffer_end = buffer.length-1 - getRead_index() +1;
            final int to_skip = Math.min(_l, read_to_buffer_end);
            setRead_index(getRead_index() + to_skip);
            setFree_space(getFree_space() + to_skip);
            skip_remaining -= to_skip;
            assert p_invariablesHold();

            if (getRead_index() == buffer.length) {
                setRead_index(0);
                setStore_index_wrapped(false);
                assert p_invariablesHold();
            }

            if (skip_remaining > 0) {
                final int read_to_store = getStore_index()-1 -getRead_index() +1;
                assert skip_remaining <= read_to_store;
                final int to_skip2 = skip_remaining;
                setRead_index(getRead_index() + to_skip2);
                setFree_space(getFree_space() + to_skip2);
                skip_remaining -= to_skip2;
                assert p_invariablesHold();
            }

        }
        else {
            final int read_to_store = getStore_index()-1 -getRead_index() +1;
            assert _l <= read_to_store;
            final int to_skip = _l;
            setRead_index(getRead_index() + to_skip);
            setFree_space(getFree_space() + to_skip);
            skip_remaining -= to_skip;
            assert p_invariablesHold();
        }

        assert previous_available - _l == available();
        assert previous_free_space + _l == getFree_space();
        assert skip_remaining == 0;
        return _l;
    }

    /**
     * used for cloning.
     * @param buffer
     */
    private CyclicBuffer (final Object[] _buffer) {
        buffer = _buffer;
        read_index = 0;
        store_index = 0;
        free_space = buffer.length;
        store_index_wrapped = false;
        snapshot_available = false;
    }
    @Override
    public ByteCyclicBuffer clone() throws CloneNotSupportedException {
        ByteCyclicBuffer result = (ByteCyclicBuffer) super.clone();
        final byte[] buffa = new byte[buffer.length];
        System.arraycopy(buffa, 0, buffer, 0, buffa.length);
        result.buffer = (buffa);
        return result;
    }
    private static final Logger LOG = Logger.getLogger(ByteCyclicBuffer.class.getName());

    /**
     * @return the read_index
     */
    protected int getRead_index () {
        return read_index;
    }

    /**
     * @param read_index the read_index to set
     */
    protected void setRead_index (int read_index) {
        this.read_index = read_index;
    }

    /**
     * @return the store_index
     */
    protected int getStore_index () {
        return store_index;
    }

    /**
     * @param store_index the store_index to set
     */
    protected void setStore_index (int store_index) {
        this.store_index = store_index;
    }

    /**
     * @return the free_space
     */
    protected int getFree_space () {
        return free_space;
    }

    /**
     * @param free_space the free_space to set
     */
    protected void setFree_space (int free_space) {
        this.free_space = free_space;
    }

    /**
     * @return the store_index_wrapped
     */
    protected boolean isStore_index_wrapped () {
        return store_index_wrapped;
    }

    /**
     * @param store_index_wrapped the store_index_wrapped to set
     */
    protected void setStore_index_wrapped (boolean store_index_wrapped) {
        this.store_index_wrapped = store_index_wrapped;
    }
}
