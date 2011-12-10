package isi.util;

import java.util.logging.Logger;

public class OutputBufferTooShortException extends TransformationException {

    private static final long serialVersionUID = 1L;

    public OutputBufferTooShortException () {
    }

    public OutputBufferTooShortException (final String message) {
        super(message);
    }

    public OutputBufferTooShortException (final String message, final Throwable cause) {
        super(message, cause);
    }

    public OutputBufferTooShortException (final Throwable cause) {
        super(cause);
    }
    private static final Logger LOG = Logger.getLogger(OutputBufferTooShortException.class.getName());
}
