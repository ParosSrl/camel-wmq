package org.apache.camel.component.wmq;

import org.apache.camel.Processor;
import org.apache.camel.Producer;
import org.apache.camel.api.management.ManagedResource;
import org.apache.camel.component.jms.JmsConsumer;
import org.apache.camel.component.jms.JmsEndpoint;
import org.apache.camel.spi.UriEndpoint;

@ManagedResource(description = "Managed WMQ Endpoint")
@UriEndpoint(
        scheme = "wmq",
        title = "WMQ",
        syntax = "jms:destinationType:destinationName",
        consumerClass = WmqConsumer.class,
        label = "messaging"
)
public class WmqEndpoint extends JmsEndpoint {

    public WmqEndpoint(WmqComponent component, String uri, String destination) {
        super(uri, extractName(destination), isTopic(destination));
        setConfiguration(component.createConfiguration());
    }

    @Override
    public Producer createProducer() throws Exception {
        return new WmqProducer(this);
    }

    @Override
    public JmsConsumer createConsumer(Processor processor) throws Exception {
        return new WmqConsumer(this, processor);
    }

    private static String extractName(String destination) {
        return destination.contains(":")
                ? destination.split(":")[1]
                : destination;
    }

    private static boolean isTopic(String destination) {
        return destination.startsWith("topic:");
    }

}
