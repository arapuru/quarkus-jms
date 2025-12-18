package org.example.consumer;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.jms.JMSException;
import jakarta.jms.Message;
import jakarta.jms.TextMessage;
import org.jboss.logging.Logger;
import io.quarkus.jms.JMSConnectionFactory;
import io.quarkus.jms.JMSListener;

/**
 * A simple JMS consumer that listens to messages from IBM MQ.
 * Adjust the destination name in the @JMSListener annotation to match your queue.
 */
@ApplicationScoped
public class MessageConsumer {

    private static final Logger LOG = Logger.getLogger(MessageConsumer.class);

    // The name of the JMS connection factory defined in application.properties
    @JMSConnectionFactory("myFactory")
    JMSListener listener;

    @JMSListener(destination = "DEV.QUEUE.1", connectionFactory = "myFactory")
    public void onMessage(Message message) {
        try {
            if (message instanceof TextMessage) {
                String body = ((TextMessage) message).getText();
                LOG.infof("Received TextMessage: %s", body);
            } else {
                LOG.info("Received non‑text JMS Message");
            }
        } catch (JMSException e) {
            LOG.error("Error processing JMS message", e);
        }
    }
}
