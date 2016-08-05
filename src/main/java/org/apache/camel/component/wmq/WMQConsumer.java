package org.apache.camel.component.wmq;

import org.apache.camel.Processor;
import org.apache.camel.component.jms.JmsConsumer;

public class WMQConsumer extends JmsConsumer {

    public WMQConsumer(WMQEndpoint endpoint, Processor processor) {
        super(endpoint, processor, null);
    }

}
