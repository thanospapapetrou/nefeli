package io.github.thanospapapetrou.nefeli.common.schema;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.Reader;
import java.io.StringReader;
import java.net.URI;
import java.net.URL;
import java.nio.charset.Charset;

import org.w3c.dom.ls.LSInput;

public class ReadOnlyLSInput implements LSInput {
    private static final String SET_BASE_URI = "Setting base URI is not supported";
    private static final String SET_BYTE_STREAM = "Setting byte stream is not supported";
    private static final String SET_CERTIFIED_TEXT = "Setting certified text is not supported";
    private static final String SET_CHARACTER_STREAM = "Setting character stream is not supported";
    private static final String SET_ENCODING = "Setting encoding is not supported";
    private static final String SET_PUBLIC_ID = "Setting public ID is not supported";
    private static final String SET_STRING_DATA = "Setting string data is not supported";
    private static final String SET_SYSTEM_ID = "Setting system ID is not supported";

    private final byte[] data;
    private final Charset charset;
    private final URI publicId;
    private final URL systemId;
    private final URL baseUri;
    private final boolean certified;

    public ReadOnlyLSInput(final byte[] data, final Charset charset, final URL url) {
        this(data, charset, null, url, null, false);
    }

    public ReadOnlyLSInput(final byte[] data, final Charset charset, final URI publicId, final URL systemId,
            final URL baseUri, final boolean certified) {
        this.data = data;
        this.charset = charset;
        this.publicId = publicId;
        this.systemId = systemId;
        this.baseUri = baseUri;
        this.certified = certified;
    }

    @Override
    public Reader getCharacterStream() {
        return new StringReader(getStringData());
    }

    @Override
    public void setCharacterStream(final Reader reader) {
        throw new UnsupportedOperationException(SET_CHARACTER_STREAM);
    }

    @Override
    public InputStream getByteStream() {
        return new ByteArrayInputStream(data);
    }

    @Override
    public void setByteStream(final InputStream input) {
        throw new UnsupportedOperationException(SET_BYTE_STREAM);
    }

    @Override
    public String getStringData() {
        return new String(data, charset);
    }

    @Override
    public void setStringData(final String string) {
        throw new UnsupportedOperationException(SET_STRING_DATA);
    }

    @Override
    public String getSystemId() {
        return systemId.toString();
    }

    @Override
    public void setSystemId(final String systemId) {
        throw new UnsupportedOperationException(SET_SYSTEM_ID);
    }

    @Override
    public String getPublicId() {
        return (publicId == null) ? null : publicId.toString();
    }

    @Override
    public void setPublicId(final String publicId) {
        throw new UnsupportedOperationException(SET_PUBLIC_ID);
    }

    @Override
    public String getBaseURI() {
        return (baseUri == null) ? null : baseUri.toString();
    }

    @Override
    public void setBaseURI(final String baseUri) {
        throw new UnsupportedOperationException(SET_BASE_URI);
    }

    @Override
    public String getEncoding() {
        return charset.name();
    }

    @Override
    public void setEncoding(final String encoding) {
        throw new UnsupportedOperationException(SET_ENCODING);
    }

    @Override
    public boolean getCertifiedText() {
        return certified;
    }

    @Override
    public void setCertifiedText(final boolean certifiedText) {
        throw new UnsupportedOperationException(SET_CERTIFIED_TEXT);
    }
}
