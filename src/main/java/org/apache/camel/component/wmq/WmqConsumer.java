package org.apache.camel.component.wmq;

import org.apache.camel.Processor;
import org.apache.camel.component.jms.JmsConsumer;

public class WmqConsumer extends JmsConsumer {

    public WmqConsumer(WmqEndpoint endpoint, Processor processor) {
        super(endpoint, processor, new WmqMessageListenerContainer(endpoint));
    }

}
