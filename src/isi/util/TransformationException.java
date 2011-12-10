package isi.util;

import java.util.logging.Logger;

public class TransformationException extends Exception {

    public TransformationException (final Throwable cause) {
        super(cause);
    }

    public TransformationException (final String message, final Throwable cause) {
        super(message, cause);
    }

    public TransformationException (final String message) {
        super(message);
    }

    public TransformationException () {
    }
    private static final long serialVersionUID = 1L;
    private static final Logger LOG = Logger.getLogger(TransformationException.class.getName());
}
