package org.apache.camel.component.wmq;

import com.ibm.mq.jms.MQConnectionFactory;

import javax.jms.JMSException;

public class WmqConnectionFactory extends MQConnectionFactory {

    public WmqConnectionFactory(ConnectionFactoryParameters parameters) {
        try {
            setQueueManager(parameters.getQueueManager());
            setHostName(parameters.getHostname());
            setChannel(parameters.getChannel());
            setPort(parameters.getPort());
            setTransportType(1);
        } catch (JMSException e) {
            throw new RuntimeException("Cannot create connection factory", e);
        }
    }

}
