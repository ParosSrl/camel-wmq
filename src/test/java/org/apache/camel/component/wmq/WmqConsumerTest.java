package org.apache.camel.component.wmq;

import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.junit.Test;

import java.util.List;

import static org.apache.camel.component.wmq.WmqComponent.newWmqComponent;

public class WmqConsumerTest extends CamelTestSupport {

    @Override
    protected CamelContext createCamelContext() throws Exception {
        CamelContext context = super.createCamelContext();
        context.addRoutes(route());
        context.addComponent("wmq", newWmqComponent("host", 1514, "TEST1", "SYSTEM.DEF.SVRCONN"));
        return context;
    }

    private RouteBuilder route() throws Exception {
        return new RouteBuilder() {
            @Override
            public void configure() throws Exception {
                from("wmq:PROVA.TEST1?username=mqm&password=mqm&concurrentConsumers=5")
                    .log("APPL_ID_DATA: ${header.JMS_IBM_MQMD_ApplIdentityData}")
                    .log(body().toString())
                    .to("mock:end");
            }
        };
    }

    @Test
    public void name() throws Exception {
        MockEndpoint mockEndpoint = getMockEndpoint("mock:end");

        Thread.sleep(100L);

        List<Exchange> exchanges = mockEndpoint.getExchanges();

        exchanges.forEach(e -> {
            System.out.println("Body: " + e.getIn().getBody());
            System.out.println("Headers: " + e.getIn().getHeaders());
        });
    }
}