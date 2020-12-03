package com.redhat.common.jee.jms;

import com.redhat.gss.common.util.SolrInputDocumentFieldFactory;
import com.redhat.gss.common.util.XmlUtils;
import java.util.List;
import org.apache.solr.common.SolrInputDocument;
import org.w3c.dom.Document;

/**
 * Abstract base class for JSON listeners.
 *
 * @author Scot P. Floess
 */
public abstract class AbstractSolrInputXmlMessageListener extends AbstractXmlMessageListener {

    /**
     * Assists in converting fields correctly from XML to the solr input document.
     */
    final SolrInputDocumentFieldFactory fieldFactory;

    /**
     * Default constructor.
     */
    protected AbstractSolrInputXmlMessageListener(final SolrInputDocumentFieldFactory fieldFactory) {
        this.fieldFactory = fieldFactory;
    }

    /**
     * Return the field factory.
     */
    protected SolrInputDocumentFieldFactory getFieldFactory() {
        return fieldFactory;
    }

    /**
     * Called when we receive an XML solr input document list for insert.
     */
    protected final void onSolrInputMessage(final List<SolrInputDocument> solrMessage) {
        getCalaveras().indexSolrDocument(solrMessage, getNamedConnection());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void onXmlMessage(final Document xmlMessage) {
        onSolrInputMessage(XmlUtils.toSolrInputDocuments(xmlMessage, getFieldFactory()));
    }
}
