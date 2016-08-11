package org.apache.camel.component.wmq;

import org.apache.camel.Exchange;
import org.apache.camel.RoutesBuilder;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.junit.Test;

import java.util.List;

public class WmqConsumerTest extends CamelTestSupport {

    @Override
    protected RoutesBuilder createRouteBuilder() throws Exception {
        return new RouteBuilder() {
            @Override
            public void configure() throws Exception {
                from("wmq:QRM_AWS_QUERCIA_TIBCO?username=mqm&password=mqm")
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