package isi.data_structures;

public interface ByteBuffer {
    public ByteCyclicBuffer appendva(final byte... block) throws StorageException;
    public ByteCyclicBuffer append(final byte[] block) throws StorageException;
    public ByteCyclicBuffer append(final byte[] block, final int off, final int _len) throws StorageException;
    public int capacity();
    public int freeSpace();
    public int available();
    public int read(final byte[] dest, final int off, final int len);
    public int skip(int n);
    /**
     * Resets the buffer, so that there are 0 bytes {@link #available available}
     * to read, and the {@link #freeSpace free space} is equal to the buffer's
     * {@link #capacity capacity}.
     * @return this
     */
    public ByteCyclicBuffer reset();

    /**
     * Saves the current availability of bytes of this buffer, so that if they
     * are read, they can be available again through a call to {@link restore}.
     *
     * Notice that if between a snapshot() and a restore(), any append() method
     * is called, the snalpshot() is lost and restore() will throw an exception.
     * @return this
     */
    public ByteCyclicBuffer snapshot();
    /**
     * If {@link snapshot} is called and not {@link append append} method
     * has been called after {@link snapshot snapshot}, then calling this method
     * will cause the bytes that were available for reading at the time {@link
     * snapshot snapshot} was called, to be available for reading again.
     *
     * However, if {@link snapshot snapshot} has not been called or if an append
     * method has been called between snapshot() and restore().
     *
     * Restore() does not cause the snapshot to be lost.
     * @return this
     */
    public ByteCyclicBuffer restore();
    public boolean restoreAvailable();
	
	public java.nio.ByteBuffer ToByteBuffer ();
}
