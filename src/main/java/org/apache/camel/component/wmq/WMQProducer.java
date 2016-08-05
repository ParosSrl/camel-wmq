package org.apache.camel.component.wmq;

import com.ibm.mq.jms.MQDestination;
import org.apache.camel.component.jms.JmsProducer;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Session;

import static com.ibm.msg.client.wmq.common.CommonConstants.*;

public class WMQProducer extends JmsProducer {

    public WMQProducer(WMQEndpoint endpoint) {
        super(endpoint);
    }

    @Override
    protected Destination resolveOrCreateDestination(String destinationName, Session session) throws JMSException {
        MQDestination destination = (MQDestination)super.resolveOrCreateDestination(destinationName, session);
        destination.setBooleanProperty(WMQ_MQMD_WRITE_ENABLED, true);
        destination.setBooleanProperty(WMQ_MQMD_READ_ENABLED, true);
        destination.setIntProperty(WMQ_MQMD_MESSAGE_CONTEXT, WMQ_MDCTX_SET_ALL_CONTEXT);
        return destination;
    }


}
