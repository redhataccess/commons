package com.redhat.common.jee.jms;

import com.redhat.gss.common.util.XmlUtils;
import org.w3c.dom.Document;

/**
 * Abstract base class for JSON listeners.
 *
 * @author Scot P. Floess
 */
public abstract class AbstractXmlMessageListener extends AbstractMessageListener {
    /**
     * Default constructor.
     */
    protected AbstractXmlMessageListener() {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void onStringMessage(final String stringMessage) {
        onXmlMessage(XmlUtils.toDocument(stringMessage));
    }

    /**
     * Process the JSON object.
     *
     * @param xmlMessage is the message to process.
     */
    protected abstract void onXmlMessage(final Document xmlMessage);
}
