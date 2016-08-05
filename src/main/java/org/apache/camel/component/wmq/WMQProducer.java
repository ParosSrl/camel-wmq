package org.apache.camel.component.wmq;

import org.apache.camel.component.jms.JmsProducer;

public class WMQProducer extends JmsProducer {

    public WMQProducer(WMQEndpoint endpoint) {
        super(endpoint);
    }

}
