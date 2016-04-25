package com.github.thanospapapetrou.nefeli.commons;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

import javax.xml.XMLConstants;

import org.w3c.dom.Attr;
import org.w3c.dom.CDATASection;
import org.w3c.dom.Comment;
import org.w3c.dom.Document;
import org.w3c.dom.DocumentFragment;
import org.w3c.dom.DocumentType;
import org.w3c.dom.Element;
import org.w3c.dom.Entity;
import org.w3c.dom.EntityReference;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Notation;
import org.w3c.dom.ProcessingInstruction;
import org.w3c.dom.Text;

/**
 * A comparator for DOM nodes.
 * 
 * @author thanos
 */
public class NodeComparator implements Comparator<Node> {
	/**
	 * Compare two DOM attributes.
	 * 
	 * @param attribute1
	 *            the first attribute to compare
	 * @param attribute2
	 *            the second attribute to compare
	 * @return a negative integer, zero or a positive integer if the first attribute is less than, equal or greater than the second attribute respectively
	 */
	public int compare(final Attr attribute1, final Attr attribute2) {
		Objects.requireNonNull(attribute1, "Attribute 1 must not be null");
		Objects.requireNonNull(attribute2, "Attribute 2 must not be null");
		final int namespaceComparison = compare(attribute1.getNamespaceURI(), attribute2.getNamespaceURI());
		if (namespaceComparison != 0) {
			return namespaceComparison;
		}
		final int localNameComparison = attribute1.getLocalName().compareTo(attribute2.getLocalName());
		if (localNameComparison != 0) {
			return localNameComparison;
		}
		return compare(attribute1.getChildNodes(), attribute2.getChildNodes());
	}

	/**
	 * Compare two DOM CDATA sections.
	 * 
	 * @param cdataSection1
	 *            the first CDATA section to compare
	 * @param cdataSection2
	 *            the second CDATA section to compare
	 * @return a negative integer, zero or a positive integer if the first CDATA section is less than, equal or greater than the second CDATA section respectively
	 */
	public int compare(final CDATASection cdataSection1, final CDATASection cdataSection2) {
		return Objects.requireNonNull(cdataSection1, "CDATA section 1 must not be null").getData().compareTo(Objects.requireNonNull(cdataSection2, "CDATA section 2 must not be null").getData());
	}

	/**
	 * Compare two DOM comments.
	 * 
	 * @param comment1
	 *            the first comment to compare
	 * @param comment2
	 *            the second comment to compare
	 * @return a negative integer, zero or a positive integer if the first comment is less than, equal or greater than the second comment respectively
	 */
	public int compare(final Comment comment1, final Comment comment2) {
		return Objects.requireNonNull(comment1, "Comment 1 must not be null").getData().compareTo(Objects.requireNonNull(comment2, "Comment 2 must not be null").getData());
	}

	/**
	 * Compare two DOM documents.
	 * 
	 * @param document1
	 *            the first document to compare
	 * @param document2
	 *            the second document to compare
	 * @return a negative integer, zero or a positive integer if the first document is less than, equal or greater than the second document respectively
	 */
	public int compare(final Document document1, final Document document2) {
		return compare(Objects.requireNonNull(document1, "Document 1 must not be null").getChildNodes(), Objects.requireNonNull(document2, "Document 2 must not be null").getChildNodes());
	}

	/**
	 * Compare two DOM document fragments.
	 * 
	 * @param documentFragment1
	 *            the first document fragment to compare
	 * @param documentFragment2
	 *            the second document fragment to compare
	 * @return a negative integer, zero or a positive integer if the first document fragment is less than, equal or greater than the second document fragment respectively
	 */
	public int compare(final DocumentFragment documentFragment1, final DocumentFragment documentFragment2) {
		return compare(Objects.requireNonNull(documentFragment1, "Document fragment 1 must not be null").getChildNodes(), Objects.requireNonNull(documentFragment2, "Document fragment 2 must not be null").getChildNodes());
	}

	/**
	 * Compare two DOM document types.
	 * 
	 * @param documentType1
	 *            the first document type to compare
	 * @param documentType2
	 *            the second document type to compare
	 * @return a negative integer, zero or a positive integer if the first document type is less than, equal or greater than the second document type respectively
	 */
	public int compare(final DocumentType documentType1, final DocumentType documentType2) {
		return Objects.requireNonNull(documentType1, "Document type 1 must not be null").getName().compareTo(Objects.requireNonNull(documentType2, "Document type 2 must not be null").getName());
	}

	/**
	 * Compare two DOM elements.
	 * 
	 * @param element1
	 *            the first element to compare
	 * @param element2
	 *            the second element to compare
	 * @return a negative integer, zero or a positive integer if the first element is less than, equal or greater than the second element respectively
	 */
	public int compare(final Element element1, final Element element2) {
		Objects.requireNonNull(element1, "Element 1 must not be null");
		Objects.requireNonNull(element2, "Element 2 must not be null");
		final int namespaceComparison = compare(element1.getNamespaceURI(), element2.getNamespaceURI());
		if (namespaceComparison != 0) {
			return namespaceComparison;
		}
		final int localNameComparison = element1.getLocalName().compareTo(element2.getLocalName());
		if (localNameComparison != 0) {
			return localNameComparison;
		}
		final int attributeComparison = compare(element1.getAttributes(), element2.getAttributes());
		if (attributeComparison != 0) {
			return 0;
		}
		return compare(element1.getChildNodes(), element2.getChildNodes());
	}

	/**
	 * Compare two DOM entities.
	 * 
	 * @param entity1
	 *            the first entity to compare
	 * @param entity2
	 *            the second entity to compare
	 * @return a negative integer, zero or a positive integer if the first entity is less than, equal or greater than the second entity respectively
	 */
	public int compare(final Entity entity1, final Entity entity2) {
		Objects.requireNonNull(entity1, "Entity 1 must not be null");
		Objects.requireNonNull(entity2, "Entity 2 must not be null");
		final int nameComparison = entity1.getNodeName().compareTo(entity2.getNodeName());
		if (nameComparison != 0) {
			return nameComparison;
		}
		return compare(entity1.getChildNodes(), entity2.getChildNodes());
	}

	/**
	 * Compare two DOM entity references.
	 * 
	 * @param entityReference1
	 *            the first entity reference to compare
	 * @param entityReference2
	 *            the second entity reference to compare
	 * @return a negative integer, zero or a positive integer if the first entity reference is less than, equal or greater than the second entity reference respectively
	 */
	public int compare(final EntityReference entityReference1, final EntityReference entityReference2) {
		Objects.requireNonNull(entityReference1, "Entity reference 1 must not be null");
		Objects.requireNonNull(entityReference2, "Entity reference 2 must not be null");
		final int nameComparison = entityReference1.getNodeName().compareTo(entityReference2.getNodeName());
		if (nameComparison != 0) {
			return nameComparison;
		}
		return compare(entityReference1.getChildNodes(), entityReference2.getChildNodes());
	}

	/**
	 * Compare two DOM notations.
	 * 
	 * @param notation1
	 *            the first notation to compare
	 * @param notation2
	 *            the second notation to compare
	 * @return a negative integer, zero or a positive integer if the first notation is less than, equal or greater than the second notation respectively
	 */
	public int compare(final Notation notation1, final Notation notation2) {
		Objects.requireNonNull(notation1, "Notation 1 must not be null");
		Objects.requireNonNull(notation2, "Notation 2 must not be null");
		final int nameComparison = notation1.getNodeName().compareTo(notation2.getNodeName());
		if (nameComparison != 0) {
			return nameComparison;
		}
		final int publicIdComparison = compare(notation1.getPublicId(), notation2.getPublicId());
		return (publicIdComparison == 0) ? compare(notation1.getSystemId(), notation2.getSystemId()) : publicIdComparison;
	}

	/**
	 * Compare two DOM processing instructions.
	 * 
	 * @param processingInstruction1
	 *            the first processing instruction to compare
	 * @param processingInstruction2
	 *            the second processing instruction to compare
	 * @return a negative integer, zero or a positive integer if the first processing instruction is less than, equal or greater than the second processing instruction respectively
	 */
	public int compare(final ProcessingInstruction processingInstruction1, final ProcessingInstruction processingInstruction2) {
		Objects.requireNonNull(processingInstruction1, "Processing instruction 1 must not be null");
		Objects.requireNonNull(processingInstruction2, "Processing instruction 2 must not be null");
		final int targetComparison = processingInstruction1.getTarget().compareTo(processingInstruction2.getTarget());
		return (targetComparison == 0) ? processingInstruction1.getData().compareTo(processingInstruction2.getData()) : targetComparison;
	}

	/**
	 * Compare two DOM text nodes.
	 * 
	 * @param text1
	 *            the first text node to compare
	 * @param text2
	 *            the second text node to compare
	 * @return a negative integer, zero or a positive integer if the first text node is less than, equal or greater than the second text ndoe respectively
	 */
	public int compare(final Text text1, final Text text2) {
		return Objects.requireNonNull(text1, "Text 1 must not be null").getData().compareTo(Objects.requireNonNull(text2, "Text 2 must not be null").getData());
	}

	/**
	 * Check two DOM nodes for equality.
	 * 
	 * @param node1
	 *            the first node to check
	 * @param node2
	 *            the second node to check
	 * @return <code>true</code> if the two nodes are equal, <code>false</code> otherwise
	 */
	public boolean equals(final Node node1, final Node node2) {
		return compare(node1, node2) == 0;
	}

	/**
	 * Calculate a hash code for an attribute.
	 * 
	 * @param attribute
	 *            the attribute to calculate hash code for
	 * @return a hash code for the given attribute
	 */
	public int hashCode(final Attr attribute) {
		Objects.requireNonNull(attribute, "Attribute must not be null");
		return Objects.hash(attribute.getNamespaceURI(), attribute.getLocalName(), hashCode(attribute.getChildNodes()));
	}

	/**
	 * Calculate a hash code for a CDATA section.
	 * 
	 * @param cdataSection
	 *            the CDATA section to calculate hash code for
	 * @return a hash code for the given CDATA section
	 */
	public int hashCode(final CDATASection cdataSection) {
		return Objects.hash(Objects.requireNonNull(cdataSection, "CDATA section must not be null").getData());
	}

	/**
	 * Calculate a hash code for a comment.
	 * 
	 * @param comment
	 *            the comment to calculate hash code for
	 * @return a hash code for the given comment
	 */
	public int hashCode(final Comment comment) {
		return Objects.hash(Objects.requireNonNull(comment, "Comment must not be null").getData());
	}

	/**
	 * Calculate a hash code for a document.
	 * 
	 * @param document
	 *            the document to calculate hash code for
	 * @return a hash code for the given document
	 */
	public int hashCode(final Document document) {
		return hashCode(Objects.requireNonNull(document, "Document must not be null").getChildNodes());
	}

	/**
	 * Calculate a hash code for a document fragment.
	 * 
	 * @param documentFragment
	 *            the document fragment to calculate hash code for
	 * @return a hash code for the given document fragment
	 */
	public int hashCode(final DocumentFragment documentFragment) {
		return hashCode(Objects.requireNonNull(documentFragment, "Document fragment must not be null").getChildNodes());
	}

	/**
	 * Calculate a hash code for a document type.
	 * 
	 * @param documentType
	 *            the document type to calculate hash code for
	 * @return a hash code for the given document type
	 */
	public int hashCode(final DocumentType documentType) {
		return Objects.requireNonNull(documentType, "Document type must not be null").getName().hashCode();
	}

	/**
	 * Calculate a hash code for an element.
	 * 
	 * @param element
	 *            the element to calculate hash code for
	 * @return a hash code for the given element
	 */
	public int hashCode(final Element element) {
		Objects.requireNonNull(element, "Element must not be null");
		return Objects.hash(element.getNamespaceURI(), element.getLocalName(), hashCode(element.getAttributes()), hashCode(element.getChildNodes()));
	}

	/**
	 * Calculate a hash code for an entity.
	 * 
	 * @param entity
	 *            the entity to calculate hash code for
	 * @return a hash code for the given entity
	 */
	public int hashCode(final Entity entity) {
		Objects.requireNonNull(entity, "Entity must not be null");
		return Objects.hash(entity.getNodeName(), hashCode(entity.getChildNodes()));
	}

	/**
	 * Calculate a hash code for an entity reference.
	 * 
	 * @param entityReference
	 *            the entity reference to calculate hash code for
	 * @return a hash code for the given entity reference
	 */
	public int hashCode(final EntityReference entityReference) {
		Objects.requireNonNull(entityReference, "Entity reference must not be null");
		return Objects.hash(entityReference.getNodeName(), hashCode(entityReference.getChildNodes()));
	}

	/**
	 * Calculate a hash code for a node.
	 * 
	 * @param node
	 *            the node to calculate hash code for
	 * @return a hash code for the given node
	 */
	public int hashCode(final Node node) {
		Objects.requireNonNull(node);
		switch (node.getNodeType()) {
		case Node.ATTRIBUTE_NODE:
			return hashCode((Attr) node);
		case Node.CDATA_SECTION_NODE:
			return hashCode((CDATASection) node);
		case Node.COMMENT_NODE:
			return hashCode((Comment) node);
		case Node.DOCUMENT_FRAGMENT_NODE:
			return hashCode((DocumentFragment) node);
		case Node.DOCUMENT_NODE:
			return hashCode((Document) node);
		case Node.DOCUMENT_TYPE_NODE:
			return hashCode((DocumentType) node);
		case Node.ELEMENT_NODE:
			return hashCode((Element) node);
		case Node.ENTITY_NODE:
			return hashCode((Entity) node);
		case Node.ENTITY_REFERENCE_NODE:
			return hashCode((EntityReference) node);
		case Node.NOTATION_NODE:
			return hashCode((Notation) node);
		case Node.PROCESSING_INSTRUCTION_NODE:
			return hashCode((ProcessingInstruction) node);
		case Node.TEXT_NODE:
			return hashCode((Text) node);
		default:
			throw new IllegalStateException(String.format("Unknown node type %1$d", node.getNodeType()));
		}
	}

	/**
	 * Calculate a hash code for a notation.
	 * 
	 * @param notation
	 *            the notation to calculate hash code for
	 * @return a hash code for the given notation
	 */
	public int hashCode(final Notation notation) {
		Objects.requireNonNull(notation, "Notation must not be null");
		return Objects.hash(notation.getNodeName(), notation.getPublicId(), notation.getSystemId());
	}

	/**
	 * Calculate a hash code for a processing instruction.
	 * 
	 * @param processingInstruction
	 *            the processing instruction to calculate hash code for
	 * @return a hash code for the given processing instruction
	 */
	public int hashCode(final ProcessingInstruction processingInstruction) {
		Objects.requireNonNull(processingInstruction, "Processing instruction must not be null");
		return Objects.hash(processingInstruction.getTarget(), processingInstruction.getData());
	}

	/**
	 * Calculate a hash code for a text node.
	 * 
	 * @param text
	 *            the text node to calculate hash code for
	 * @return a hash code for the given text node
	 */
	public int hashCode(final Text text) {
		return Objects.hash(Objects.requireNonNull(text, "Text must not be null").getData());
	}

	@Override
	public int compare(final Node node1, final Node node2) {
		final int typeComparison = Objects.requireNonNull(node1, "Node 1 must not be null").getNodeType() - Objects.requireNonNull(node2, "Node 2 must not be null").getNodeType();
		if (typeComparison != 0) {
			return typeComparison;
		}
		final short type = node1.getNodeType();
		switch (type) {
		case Node.ATTRIBUTE_NODE:
			return compare((Attr) node1, (Attr) node2);
		case Node.CDATA_SECTION_NODE:
			return compare((CDATASection) node1, (CDATASection) node2);
		case Node.COMMENT_NODE:
			return compare((Comment) node1, (Comment) node2);
		case Node.DOCUMENT_FRAGMENT_NODE:
		case Node.DOCUMENT_NODE:
		case Node.DOCUMENT_TYPE_NODE:
		case Node.ELEMENT_NODE:
		case Node.ENTITY_NODE:
		case Node.ENTITY_REFERENCE_NODE:
		case Node.NOTATION_NODE:
			return compare((Notation) node1, (Notation) node2);
		case Node.PROCESSING_INSTRUCTION_NODE:
			return compare((ProcessingInstruction) node1, (ProcessingInstruction) node2);
		case Node.TEXT_NODE:
			return compare((Text) node1, (Text) node2);
		default:
			throw new IllegalStateException(String.format("Unknown node type %1$d", type));
		}
	}

	private int compare(final String string1, final String string2) {
		return (string1 == null) ? ((string2 == null) ? 0 : -1) : ((string2 == null) ? 1 : string1.compareTo(string2));
	}

	private int compare(final NamedNodeMap namedNodeMap1, final NamedNodeMap namedNodeMap2) {
		int i = 0;
		int j = 0;
		while ((i < namedNodeMap1.getLength()) || (j < namedNodeMap2.getLength())) {
			while ((i < namedNodeMap1.getLength()) && XMLConstants.XMLNS_ATTRIBUTE.equals(namedNodeMap1.item(i).getPrefix())) {
				i++;
			}
			while ((j < namedNodeMap2.getLength()) && XMLConstants.XMLNS_ATTRIBUTE.equals(namedNodeMap2.item(j).getPrefix())) {
				j++;
			}
			final int nodeComparison = compare(namedNodeMap1.item(i), namedNodeMap2.item(j));
			if (nodeComparison != 0) {
				return nodeComparison;
			}
		}
		return (namedNodeMap1.getLength() - i) - (namedNodeMap2.getLength() - j);
	}

	private int compare(final NodeList nodeList1, final NodeList nodeList2) {
		for (int i = 0; (i < nodeList1.getLength()) && (i < nodeList2.getLength()); i++) {
			final int nodeComparison = compare(nodeList1.item(i), nodeList2.item(i));
			if (nodeComparison != 0) {
				return nodeComparison;
			}
		}
		return nodeList1.getLength() - nodeList2.getLength();
	}

	private int hashCode(final NamedNodeMap namedNodeMap) {
		final List<Integer> hashCodes = new ArrayList<Integer>();
		for (int i = 0; i < namedNodeMap.getLength(); i++) {
			if (!XMLConstants.XMLNS_ATTRIBUTE.equals(namedNodeMap.item(i).getPrefix())) {
				hashCodes.add(hashCode(namedNodeMap.item(i)));
			}
		}
		return Objects.hash(hashCodes);
	}

	private int hashCode(final NodeList nodeList) {
		final int[] hashCodes = new int[nodeList.getLength()];
		for (int i = 0; i < nodeList.getLength(); i++) {
			hashCodes[i] = hashCode(nodeList.item(i));
		}
		return Objects.hash(hashCodes);
	}
}
