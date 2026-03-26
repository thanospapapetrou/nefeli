package io.github.thanospapapetrou.nefeli.oai.pmh;

import jakarta.activation.UnsupportedDataTypeException;
import jakarta.ws.rs.core.MediaType;

public class UnsupportedMediaTypeException extends UnsupportedDataTypeException {
    private static final String MESSAGE = "Invalid media type %1$s";

    private final MediaType mediaType;

    public UnsupportedMediaTypeException(final MediaType mediaType) {
        super(String.format(MESSAGE, mediaType));
        this.mediaType = mediaType;
    }

    public MediaType getMediaType() {
        return mediaType;
    }
}
