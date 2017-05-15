package srl.paros.camel.component.wmq;

import com.ibm.mq.jms.MQDestination;
import org.springframework.jms.support.destination.DynamicDestinationResolver;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Session;

import static com.ibm.msg.client.wmq.common.CommonConstants.*;

public class WmqDestinationResolver extends DynamicDestinationResolver {

    private final boolean excludeRFHeaders;

    public WmqDestinationResolver(boolean excludeRFHeaders) {
        this.excludeRFHeaders = excludeRFHeaders;
    }

    public Destination resolveDestinationName(Session session, String destinationName, boolean pubSubDomain) throws JMSException {
        MQDestination destination = (MQDestination) super.resolveDestinationName(session, destinationName, pubSubDomain);
        destination.setBooleanProperty(WMQ_MQMD_WRITE_ENABLED, true);
        destination.setBooleanProperty(WMQ_MQMD_READ_ENABLED, true);
        destination.setIntProperty(WMQ_MQMD_MESSAGE_CONTEXT, WMQ_MDCTX_SET_ALL_CONTEXT);
        if (excludeRFHeaders) {
            destination.setTargetClient(1);
        }
        return destination;
    }

}
