package io.github.thanospapapetrou.nefeli.oai.pmh;

import java.lang.reflect.ParameterizedType;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlSchema;
import jakarta.xml.bind.annotation.adapters.XmlAdapter;

import org.openarchives.oai._2.XmlContent;
import org.w3c.dom.Element;

public interface ContentProvider<T extends XmlContent> {
    static Class<?> getContentClass(final Class<? extends ContentProvider<?>> provider) {
        return Arrays.stream(provider.getGenericInterfaces()).filter(ParameterizedType.class::isInstance)
                .map(ParameterizedType.class::cast).filter(t -> t.getRawType() == ContentProvider.class)
                .map(ParameterizedType::getActualTypeArguments).map(Arrays::asList).map(List::getFirst)
                .map(Class.class::cast).findFirst().orElse(null);
    }

    static String getNamespace(final Class<? extends ContentProvider<?>> provider) {
        return getContentClass(provider).getAnnotation(XmlRootElement.class).namespace();
    }

    static String getLocalName(final Class<? extends ContentProvider<?>> provider) {
        return getContentClass(provider).getAnnotation(XmlRootElement.class).name();
    }

    static String getPrefix(final Class<? extends ContentProvider<?>> provider) {
        return getContentClass(provider).getPackage().getAnnotation(XmlSchema.class).xmlns()[0].prefix();
    }

    static URL getSchema(final Class<? extends ContentProvider<?>> provider) {
        try {
            return new URI(getContentClass(provider).getPackage().getAnnotation(XmlSchema.class).location()).toURL();
        } catch (final MalformedURLException | URISyntaxException e) {
            return null;
        }
    }

    XmlAdapter<Element, T> getAdapter();
}
