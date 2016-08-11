package org.apache.camel.component.wmq;

import org.apache.camel.Processor;
import org.apache.camel.Producer;
import org.apache.camel.api.management.ManagedResource;
import org.apache.camel.component.jms.JmsConsumer;
import org.apache.camel.component.jms.JmsEndpoint;
import org.apache.camel.spi.UriEndpoint;

@ManagedResource(description = "Managed WMQ Endpoint")
@UriEndpoint(scheme = "wmq", syntax = "", consumerClass = WmqConsumer.class, title = "WMQ Endpoint")
public class WmqEndpoint extends JmsEndpoint {

    public WmqEndpoint(WmqComponent component, String uri, String destinationName) {
        super(uri, destinationName, false);
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

}
