package org.apache.camel.component.wmq;

import org.apache.camel.component.jms.DefaultJmsMessageListenerContainer;
import org.apache.camel.component.jms.JmsEndpoint;

public class WmqMessageListenerContainer extends DefaultJmsMessageListenerContainer {

    public WmqMessageListenerContainer(JmsEndpoint endpoint) {
        super(endpoint);
        setConnectionFactory(endpoint.getConnectionFactory());
        setDestinationName(endpoint.getDestinationName());
    }

}
