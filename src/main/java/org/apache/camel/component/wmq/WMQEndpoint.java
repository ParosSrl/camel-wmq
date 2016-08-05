package org.apache.camel.component.wmq;

import com.ibm.mq.jms.MQQueue;
import org.apache.camel.Processor;
import org.apache.camel.Producer;
import org.apache.camel.api.management.ManagedResource;
import org.apache.camel.component.jms.JmsConsumer;
import org.apache.camel.component.jms.JmsEndpoint;
import org.apache.camel.spi.UriEndpoint;

import javax.jms.Destination;
import javax.jms.JMSException;

import static com.ibm.mq.constants.CMQC.WMQ_MDCTX_SET_ALL_CONTEXT;
import static com.ibm.msg.client.wmq.common.CommonConstants.*;

@ManagedResource(description = "Managed WMQ Endpoint")
@UriEndpoint(scheme = "wmq", syntax = "", consumerClass = WMQConsumer.class, title = "WMQ Endpoint")
public class WMQEndpoint extends JmsEndpoint {

    public WMQEndpoint(WMQComponent wmqComponent, String uri, String destinationName) {
        super(uri, wmqComponent, destinationName, true, wmqComponent.createConfiguration());
    }

    @Override
    public Producer createProducer() throws Exception {
        return new WMQProducer(this);
    }

    @Override
    public JmsConsumer createConsumer(Processor processor) throws Exception {
        return new WMQConsumer(this, processor);
    }

    @Override
    public Destination getDestination() {
        try {
            MQQueue mqQueue = new MQQueue(getDestinationName());
            mqQueue.setBooleanProperty(WMQ_MQMD_WRITE_ENABLED, true);
            mqQueue.setBooleanProperty(WMQ_MQMD_READ_ENABLED, true);
            mqQueue.setIntProperty(WMQ_MQMD_MESSAGE_CONTEXT, WMQ_MDCTX_SET_ALL_CONTEXT);
            return mqQueue;
        } catch (JMSException e) {
            e.printStackTrace();
            return null;
        }
    }

}
