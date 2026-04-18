package io.github.thanospapapetrou.nefeli.oai.pmh;

import java.lang.reflect.ParameterizedType;
import java.util.Arrays;
import java.util.List;

import jakarta.enterprise.inject.spi.CDI;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.adapters.XmlAdapter;

import org.openarchives.oai._2.Container;
import org.w3c.dom.Element;

public abstract class ContainerProvider<T extends Container> {
    protected final Class<? extends XmlAdapter<Element, T>> adapter;

    public static Class<?> getXmlContentClass(final Class<? extends ContainerProvider<?>> provider) {
        return Arrays.stream(provider.getGenericInterfaces()).filter(ParameterizedType.class::isInstance)
                .map(ParameterizedType.class::cast).filter(t -> t.getRawType() == ContainerProvider.class)
                .map(ParameterizedType::getActualTypeArguments).map(Arrays::asList).map(List::getFirst)
                .map(Class.class::cast).findFirst().orElse(null);
    }

    public static String getNamespace(final Class<? extends ContainerProvider<?>> provider) {
        return getXmlContentClass(provider).getAnnotation(XmlRootElement.class).namespace();
    }

    public static String getLocalName(final Class<? extends ContainerProvider<?>> provider) {
        return getXmlContentClass(provider).getAnnotation(XmlRootElement.class).name();
    }

    protected ContainerProvider(final Class<? extends XmlAdapter<Element, T>> adapter) {
        this.adapter = adapter;
    }

    public XmlAdapter<Element, T> getAdapter() {
        return CDI.current().select(adapter).get();
    }
}
