package org.example.resource;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.jms.JMSContext;
import jakarta.jms.Destination;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

/**
 * Simple REST resource to send a test message to IBM MQ queue.
 */
@ApplicationScoped
@Path("/mq")
public class MessageResource {

    @Inject
    @JMSConnectionFactory("myFactory")
    JMSContext jmsContext;

    @GET
    @Path("/send")
    @Produces(MediaType.TEXT_PLAIN)
    public String sendMessage() {
        String payload = "Hello from Quarkus!";
        Destination queue = jmsContext.createQueue("DEV.QUEUE.1");
        jmsContext.createProducer().send(queue, payload);
        return "Message sent: " + payload;
    }
}
