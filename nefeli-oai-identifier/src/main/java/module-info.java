module io.github.thanospapapetrou.nefeli.oai.identifier {
    requires jakarta.xml.bind;
    requires io.github.thanospapapetrou.nefeli.oai.pmh;
    provides io.github.thanospapapetrou.nefeli.oai.pmh.ContainerProvider with io.github.thanospapapetrou.nefeli.oai.identifier.OaiIdentifierProvider;
}
