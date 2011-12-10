package isi.data_structures;

import java.util.logging.Logger;

public class StorageException extends Exception {
    private static final long serialVersionUID = 7L;

    public StorageException(Throwable cause) {
        super(cause);
    }

    public StorageException(String message, Throwable cause) {
        super(message, cause);
    }

    public StorageException(String message) {
        super(message);
    }

    public StorageException() {
    }
    private static final Logger LOG = Logger.getLogger(StorageException.class.getName());

}
