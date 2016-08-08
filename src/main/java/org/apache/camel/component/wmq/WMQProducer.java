package org.apache.camel.component.wmq;

import org.apache.camel.AsyncCallback;
import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.component.jms.JmsProducer;

import java.text.SimpleDateFormat;
import java.util.Date;

public class WMQProducer extends JmsProducer {

    public WMQProducer(WMQEndpoint endpoint) {
        super(endpoint);
    }

    @Override
    public boolean process(Exchange exchange, AsyncCallback callback) {
        Message in = exchange.getIn();
        setDefaultHeader(in, "JMS_IBM_MQMD_PutDate", toIbmPutDate(new Date()));
        setDefaultHeader(in, "JMS_IBM_MQMD_PutTime", toIbmPutTime(new Date()));
        setDefaultHeader(in, "JMS_IBM_MQMD_PutApplName", "Wmq camel component");
        setDefaultHeader(in, "JMS_IBM_Character_Set", "UTF-8");
        setDefaultHeader(in, "JMS_IBM_Encoding", 273);
        setDefaultHeader(in, "JMS_IBM_MsgType", 8);
        setDefaultHeader(in, "JMS_IBM_PutApplType", 28);
        setDefaultHeader(in, "JMS_IBM_PutApplType", 28);
        setDefaultHeader(in, "JMS_IBM_MQMD_ApplIdentityData", "DEFAULT");
        return super.process(exchange, callback);
    }

    private void setDefaultHeader(Message in, String headerName, Object value) {
        if (in.getHeader(headerName) == null) {
            in.setHeader(headerName, value);
        }
    }


    private Object toIbmPutDate(Date date) {
        return new SimpleDateFormat("yyyyMMdd").format(date);
    }

    private Object toIbmPutTime(Date date) {
        return new SimpleDateFormat("HHmmssSS").format(date);
    }
}
