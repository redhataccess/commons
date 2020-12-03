package com.redhat.common.jee.jms;

import com.redhat.common.AbstractBase;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import org.apache.commons.lang.exception.ExceptionUtils;

/**
 * Abstract base class for JMS listeners.
 *
 * @author Scot P. Floess
 */
@TransactionManagement(TransactionManagementType.CONTAINER)
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
public abstract class AbstractMessageListener extends AbstractBase implements MessageListener {
    void processStringMessage(final String message) {
        final long startTime = System.currentTimeMillis();

        logInfo("Received string message to process:  [", message, "]");

        try {
            onStringMessage(message);
        } catch (final RuntimeException runtimeException) {
            logError("Trouble processing a [", getMessageType(), "] message: [", message, "]  Exception:\n", ExceptionUtils.getFullStackTrace(runtimeException));
            throw runtimeException;
        } catch (Exception exception) {
            logError("Trouble processing a [", getMessageType(), "] message: [", message, "]  Exception:\n", ExceptionUtils.getFullStackTrace(exception));
            throw new RuntimeException(exception);
        } finally {
            logInfo("Processed a [", getMessageType(), "] in [", System.currentTimeMillis() - startTime, "ms]");
        }
    }

    /**
     * Default constructor.
     */
    protected AbstractMessageListener() {
    }

    /**
     * Return the message type being processed.
     */
    protected abstract String getMessageType();

    /**
     * Called to process a Message as a string.
     */
    protected abstract void onStringMessage(String stringMessage);

    /**
     * {@inheritDoc}
     */
    @Override
    public final void onMessage(final Message message) {
        logDebug("Current thread: ", Thread.currentThread());

        try {
            AndreasJBossJmsLogger.setupReqIdForLogging();

            processStringMessage(MessageUtils.messageToString(message));
        } catch (final JMSException exception) {
            logError("Trouble processing JMS message type: [", getMessageType(), "]");

            throw new AndreasException(exception);
        } finally {
            AndreasJBossJmsLogger.tearDownReqIdForLogging();
        }
    }
}
