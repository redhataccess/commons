package com.redhat.common.jee.jms;

import com.redhat.gss.andreas.AndreasException;
import com.redhat.gss.calaveras.NamedConnection;
import com.redhat.gss.common.AbstractBase;
import com.redhat.gss.common.props.PropertyUtil;
import java.nio.charset.Charset;
import javax.jms.BytesMessage;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.TextMessage;

/**
 * Message utility class.
 *
 * @author Scot P. Floess
 */
public final class MessageUtils extends AbstractBase {
    private static final MessageUtils instance = new MessageUtils();

    private static MessageUtils getInstance() {
        return instance;
    }

    /**
     * Ensures a message is not null. If it is, raise an AndreasException.
     */
    public static Message ensureMessage(final Message message) {
        if (null == message) {
            getInstance().logError("Received null JMS Message!");

            throw new AndreasException("JMS Message is null!");
        }

        return message;
    }

    /**
     * Determine if a message is a BytesMessage.
     */
    public static boolean isBytesMessage(final Message message) {
        return ensureMessage(message) instanceof BytesMessage;
    }

    /**
     * Determine if a message is a TextMessage.
     */
    public static boolean isTextMessage(final Message message) {
        return ensureMessage(message) instanceof TextMessage;
    }

    /**
     * Converts message to a byte array.
     */
    public static String bytesMessageToString(final BytesMessage message, final byte[] messageBody) throws JMSException {
        getInstance().logInfo("Received BytesMessage");

        message.readBytes(messageBody);
        message.reset();
        return new String(messageBody, Charset.forName("UTF-8"));
    }

    /**
     * Converts the bytes message to a string.
     */
    public static String bytesMessageToString(final BytesMessage message) throws JMSException {
        return bytesMessageToString(message, new byte[(int) message.getBodyLength()]);
    }

    /**
     * Converts the text message to a string.
     */
    public static String textMessageToString(final TextMessage message) throws JMSException {
        getInstance().logInfo("Received TextMessage");

        return message.getText();
    }

    /**
     * Converts a message to a string.
     */
    public static String messageToString(final Message message) throws JMSException {
        return (isBytesMessage(message)) ? bytesMessageToString((BytesMessage) message) : textMessageToString((TextMessage) message);
    }

    /**
     * Compute which named connection to used based upon whether fusion is enabled or disabled.
     *
     * @param fusionEnabled  return this if fusion enabled.
     * @param fusionDisabled return this if fusion disabled.
     *
     * @return fusionEnabled if fusion is enabled, otherwise return fusionDisabled.
     */
    public static NamedConnection computeNamedConnection(final NamedConnection fusionEnabled, final NamedConnection fusionDisabled) {
        return PropertyUtil.getInstance().getBool(PropertyUtil.AndreasProperty.ANDREAS_FUSION_ENABLE, false) ? fusionEnabled : fusionDisabled;
    }

    /**
     * Returns the named connection for a case depending on fusion enabled or not.
     */
    public static NamedConnection computeCaseNamedConnection() {
        return computeNamedConnection(NamedConnection.fusionCase, NamedConnection.solrCase);
    }

    /**
     * Default constructor not allowed.
     */
    private MessageUtils() {
    }
}
