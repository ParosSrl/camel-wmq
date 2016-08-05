package org.apache.camel.component.wmq;

import org.apache.camel.Processor;
import org.apache.camel.Producer;
import org.apache.camel.api.management.ManagedResource;
import org.apache.camel.component.jms.JmsConsumer;
import org.apache.camel.component.jms.JmsEndpoint;
import org.apache.camel.spi.UriEndpoint;

@ManagedResource(description = "Managed WMQ Endpoint")
@UriEndpoint(scheme = "wmq", syntax = "", consumerClass = WMQConsumer.class, title = "WMQ Endpoint")
public class WMQEndpoint extends JmsEndpoint {

    public WMQEndpoint(WMQComponent component, String uri, String destinationName) {
        super(uri, destinationName, false);
        setConfiguration(component.createConfiguration());
    }

    @Override
    public Producer createProducer() throws Exception {
        return new WMQProducer(this);
    }

    @Override
    public JmsConsumer createConsumer(Processor processor) throws Exception {
        return new WMQConsumer(this, processor);
    }

}
