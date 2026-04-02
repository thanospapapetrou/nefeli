module io.github.thanospapapetrou.nefeli.oai.pmh {
    exports org.openarchives.oai._2;
    exports io.github.thanospapapetrou.nefeli.oai.pmh;
    exports io.github.thanospapapetrou.nefeli.oai.pmh.jaxb; // TODO do not export
    exports io.github.thanospapapetrou.nefeli.oai.pmh.jax.rs; // TODO do not export
    requires java.logging;
    requires jakarta.cdi;
    requires jakarta.mail;
    requires jakarta.ws.rs;
    requires jakarta.xml.bind;
    requires io.github.thanospapapetrou.nefeli.common;
    uses io.github.thanospapapetrou.nefeli.oai.pmh.ContainerProvider;
}
