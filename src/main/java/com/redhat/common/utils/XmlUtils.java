package com.redhat.common.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.util.LinkedList;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.apache.commons.io.IOUtils;
import org.apache.solr.common.SolrInputDocument;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 *
 * @author Scot P. Floess
 */
public final class XmlUtils {
    /**
     * Our logger.
     */
    static final Logger LOGGER = org.jboss.logging.Logger.getLogger(XmlUtils.class);

    /**
     * Builds WC3 docs...
     */
    static final DocumentBuilderFactory DOCUMENT_BUILDER_FACTORY = DocumentBuilderFactory.newInstance();

    static final String NAME_ATTRIBUTE = "name";

    static final String BOOST_ATTRIBUTE = "boost";

    static final String DOC_ELEMENT_TAG_NAME = "doc";

    public static final SolrInputDocumentFieldFactory DEFAULT_FIELD_FACTORY = new DefaultSolrInputDocumentFieldFactory();

    /**
     * Return our logger.
     */
    static Logger getLogger() {
        return LOGGER;
    }

    /**
     * Return a document builder factory to create document builders.
     */
    static DocumentBuilderFactory getDocumentBuilderFactory() {
        return DOCUMENT_BUILDER_FACTORY;
    }

    /**
     * Return the document builder...if it was never constructed, an AndreasException will be raised.
     */
    public static DocumentBuilder getDocumentBuilder() {
        try {
            return getDocumentBuilderFactory().newDocumentBuilder();
        } catch (ParserConfigurationException ex) {
            throw new AndreasException(500, "Trouble creating a DocumentBuilder", ex);
        }
    }

    /**
     * Using inputSource, we will create a Document.
     */
    public static Document toDocument(final InputSource inputSource) {
        try {
            return getDocumentBuilder().parse(ObjectUtils.ensureObject(inputSource, "InputSource cannot be null!"));
        } catch (final SAXException | IOException ex) {
            getLogger().error("Could not convert InputSource to a Document", ex);

            throw new AndreasException(400, "Could not convert InputSource to a Document", ex);
        }
    }

    /**
     * Using stringReader, we will create a Document.
     */
    public static Document toDocument(final StringReader stringReader) {
        return toDocument(new InputSource(ObjectUtils.ensureObject(stringReader, "StringReader cannot be null!")));
    }

    /**
     * Using xmlStr, we will create a Document.
     */
    public static Document toDocument(final String xmlStr) {
        try (final StringReader stringReader = new StringReader(StringUtils.ensureString(xmlStr))) {
            return toDocument(stringReader);
        }
    }

    /**
     * Using xmlStr, we will create a Document.
     */
    public static Document loadXmlFromResource(final String resource) throws IOException {
        try (final InputStream is = XmlUtils.class.getClassLoader().getResourceAsStream(resource)) {
            return toDocument(IOUtils.toString(is));
        }
    }

    /**
     * Computes document boost returning null if boost is empty.
     */
    static Float computeDocumentBoost(final String boost) {
        return StringUtils.isNotEmpty(boost) ? Float.parseFloat(boost) : null;
    }

    /**
     * Computes document boost from the boost attribute on element returning null if boost is empty.
     */
    static Float computeDocumentBoost(final Element element) {
        return computeDocumentBoost(element.getAttribute(BOOST_ATTRIBUTE));
    }

    /**
     * Sets the document boost on solrInputDocument to boost if not null.
     */
    static SolrInputDocument setDocumentBoost(final SolrInputDocument solrInputDocument, final Float boost) {
        if (null != boost) {
            solrInputDocument.setDocumentBoost(boost.floatValue());
        }

        return solrInputDocument;
    }

    /**
     * Sets the document boost on solrInputDocument if the boost attribute on element is not empty.
     */
    static SolrInputDocument setDocumentBoost(final SolrInputDocument solrInputDocument, final Element element) {
        return setDocumentBoost(solrInputDocument, computeDocumentBoost(element));
    }

    /**
     * Sets the document boost on solrInputDocument if the boost attribute on node is not empty.
     */
    static SolrInputDocument setDocumentBoost(final SolrInputDocument solrInputDocument, final Node node) {
        return setDocumentBoost(solrInputDocument, (Element) node);
    }

    /**
     * Adds a field fieldName using fieldValue to solrInputDocument.
     */
    static SolrInputDocument addFieldToSolrInputDocument(final SolrInputDocument solrInputDocument, final String fieldName, final Object value) {
        if (null != value) {
            solrInputDocument.addField(fieldName, value);
        }

        return solrInputDocument;
    }

    /**
     * Adds a field fieldName using fieldValue to solrInputDocument.
     */
    static SolrInputDocument addFieldToSolrInputDocument(final SolrInputDocument solrInputDocument, final String fieldName, final String fieldValue, final SolrInputDocumentFieldFactory fieldFactory) {
        return addFieldToSolrInputDocument(solrInputDocument, fieldName, fieldFactory.computeFieldValue(fieldName, fieldValue));
    }

    /**
     * Adds a field contained in fieldElement to solrInputDocument.
     */
    static SolrInputDocument addFieldToSolrInputDocument(final SolrInputDocument solrInputDocument, final Element fieldElement, final SolrInputDocumentFieldFactory fieldFactory) {
        return XmlUtils.addFieldToSolrInputDocument(solrInputDocument, fieldElement.getAttribute(NAME_ATTRIBUTE), fieldElement.getTextContent(), fieldFactory);
    }

    /**
     * Adds a field contained in fieldNode to solrInputDocument.
     */
    static SolrInputDocument addFieldToSolrInputDocument(final SolrInputDocument solrInputDocument, final Node fieldNode, final SolrInputDocumentFieldFactory fieldFactory) {
        return Node.ELEMENT_NODE == fieldNode.getNodeType() ? XmlUtils.addFieldToSolrInputDocument(solrInputDocument, (Element) fieldNode, fieldFactory) : solrInputDocument;
    }

    /**
     * Adds all fields contained in fieldsList to solrInputDocument.
     */
    static SolrInputDocument addFieldsToSolrInputDocument(final SolrInputDocument solrInputDocument, final NodeList fieldsList, final SolrInputDocumentFieldFactory fieldFactory) {
        for (int fieldIdx = 0; fieldIdx < fieldsList.getLength(); fieldIdx++) {
            addFieldToSolrInputDocument(solrInputDocument, fieldsList.item(fieldIdx), fieldFactory);
        }

        return solrInputDocument;
    }

    /**
     * Populates the solrInputDocument with values from element.
     */
    static SolrInputDocument populateSolrInputDocument(final SolrInputDocument solrInputDocument, final Element element, final SolrInputDocumentFieldFactory fieldFactory) {
        return addFieldsToSolrInputDocument(solrInputDocument, element.getChildNodes(), fieldFactory);
    }

    /**
     * Converts element to a SolrInputDocument.
     */
    static SolrInputDocument toSolrInputDocument(final Element element, final SolrInputDocumentFieldFactory fieldFactory) {
        return populateSolrInputDocument(new SolrInputDocument(), element, fieldFactory);
    }

    /**
     * Converts node to a SolrInputDocument.
     */
    static SolrInputDocument toSolrInputDocument(final Node node, final SolrInputDocumentFieldFactory fieldFactory) {
        return setDocumentBoost(toSolrInputDocument((Element) node, fieldFactory), node);
    }

    /**
     * Using node, append all values to the solrInputDocumentList.
     */
    static List<SolrInputDocument> appendSolrInputDocument(final List<SolrInputDocument> solrInputDocumentList, final Node node, final SolrInputDocumentFieldFactory fieldFactory) {
        if (Node.ELEMENT_NODE == node.getNodeType()) {
            solrInputDocumentList.add(toSolrInputDocument(node, fieldFactory));
        }

        return solrInputDocumentList;
    }

    /**
     * Using nodeList, append all values to the solrInputDocumentList.
     */
    static List<SolrInputDocument> appendSolrInputDocuments(final List<SolrInputDocument> solrInputDocumentList, final NodeList nodeList, final SolrInputDocumentFieldFactory fieldFactory) {
        for (int docIdx = 0; docIdx < nodeList.getLength(); docIdx++) {
            appendSolrInputDocument(solrInputDocumentList, nodeList.item(docIdx), fieldFactory);
        }

        return solrInputDocumentList;
    }

    /**
     * Using document, append all values to the solrInputDocumentList.
     */
    static List<SolrInputDocument> appendSolrInputDocuments(final List<SolrInputDocument> solrInputDocumentList, final Document document, final SolrInputDocumentFieldFactory fieldFactory) {
        return appendSolrInputDocuments(solrInputDocumentList, document.getElementsByTagName(DOC_ELEMENT_TAG_NAME), fieldFactory);
    }

    /**
     * Converts document to a list of SolrInputDocuments.
     */
    public static List<SolrInputDocument> toSolrInputDocuments(final Document document, final SolrInputDocumentFieldFactory fieldFactory) {
        return appendSolrInputDocuments(new LinkedList<SolrInputDocument>(), document, fieldFactory);
    }

    /**
     * Converts document to a list of SolrInputDocuments.
     */
    public static List<SolrInputDocument> toSolrInputDocuments(final Document document) {
        return toSolrInputDocuments(document, DEFAULT_FIELD_FACTORY);
    }

    /**
     * Converts xmlString to a list of SolrInputDocuments.
     */
    public static List<SolrInputDocument> toSolrInputDocuments(final String xmlString, final SolrInputDocumentFieldFactory fieldFactory) {
        return toSolrInputDocuments(toDocument(xmlString), fieldFactory);
    }

    /**
     * Converts xmlString to a list of SolrInputDocuments.
     */
    public static List<SolrInputDocument> toSolrInputDocuments(final String xmlString) {
        return toSolrInputDocuments(xmlString, DEFAULT_FIELD_FACTORY);
    }

    /**
     * Converts xmlString to a list of SolrInputDocuments.
     */
    public static List<SolrInputDocument> toSolrInputDocumentsFromResource(final String resource, final SolrInputDocumentFieldFactory fieldFactory) throws IOException {
        try (final InputStream is = XmlUtils.class.getClassLoader().getResourceAsStream(resource)) {
            return toSolrInputDocuments(IOUtils.toString(is), fieldFactory);
        }
    }

    /**
     * Converts xmlString to a list of SolrInputDocuments.
     */
    public static List<SolrInputDocument> toSolrInputDocumentsFromResource(final String resource) throws IOException {
        return toSolrInputDocumentsFromResource(resource, DEFAULT_FIELD_FACTORY);
    }

    /**
     * Default constructor not allowed.
     */
    private XmlUtils() {
    }
}
