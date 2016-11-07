package org.apache.camel.component.wmq;

import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.junit.Test;

import static org.apache.camel.component.wmq.WmqComponent.newWmqComponent;

public class ComponentTest extends CamelTestSupport {

    @Override
    protected CamelContext createCamelContext() throws Exception {
        CamelContext context = super.createCamelContext();
        context.addComponent("wmq", newWmqComponent("itts16.intra.infotn.it", 1514, "TEST1", "SYSTEM.DEF.SVRCONN"));
        return context;
    }

    @Test
    public void send_and_receive() throws Exception {
        template().sendBodyAndHeader("wmq:PROVA.TEST1?username=mqm&password=mqm", "aMessage", "JMS_IBM_MQMD_ApplIdentityData", "anyIdData");

        Exchange received = consumer().receive("wmq:PROVA.TEST1?username=mqm&password=mqm");
        Message in = received.getIn();

        assertEquals("aMessage", in.getBody());
        assertStartsWith(in.getHeader("JMS_IBM_MQMD_ApplIdentityData").toString(), "anyIdData");
    }

    private void assertStartsWith(String string, String starts) {
        assertTrue(string.startsWith(starts));
    }

}
