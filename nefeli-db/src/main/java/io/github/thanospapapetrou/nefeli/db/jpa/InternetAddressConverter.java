package io.github.thanospapapetrou.nefeli.db.jpa;

import jakarta.mail.internet.AddressException;
import jakarta.mail.internet.InternetAddress;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import jakarta.persistence.PersistenceException;

@Converter
public class InternetAddressConverter implements AttributeConverter<InternetAddress, String> {
    private static final String ERROR_CONVERTING = "Error converting %1$s to %2$s";

    @Override
    public String convertToDatabaseColumn(final InternetAddress internetAddress) {
        return (internetAddress == null) ? null : internetAddress.getAddress();
    }

    @Override
    public InternetAddress convertToEntityAttribute(final String string) {
        try {
            return (string == null) ? null : new InternetAddress(string);
        } catch (AddressException e) {
            throw new PersistenceException(String.format(ERROR_CONVERTING, string, InternetAddress.class.getName()), e);
        }
    }
}
