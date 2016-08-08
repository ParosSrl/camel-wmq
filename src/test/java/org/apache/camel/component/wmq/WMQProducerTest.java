package org.apache.camel.component.wmq;

import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.RoutesBuilder;
import org.apache.camel.builder.ExchangeBuilder;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.junit.Test;

public class WMQProducerTest extends CamelTestSupport {

    @Override
    protected CamelContext createCamelContext() throws Exception {
        CamelContext context = super.createCamelContext();
        context.addRoutes(route());
        return context;
    }

    @Test
    public void set_default_values_for_app_id_data() throws Exception {
        Exchange exchange = ExchangeBuilder.anExchange(context())
                .withBody("any")
                .build();

        template().send("direct:input", exchange);

        assertNotNull(exchange.getIn().getHeader("JMS_IBM_MQMD_ApplIdentityData"));
    }

    @Test
    public void set_application_id_data_over_default() throws Exception {
        Exchange exchange = ExchangeBuilder.anExchange(context())
                .withBody("any")
                .withHeader("JMS_IBM_MQMD_ApplIdentityData", "CUSTOMAPPIDDATA")
                .build();

        template().send("direct:input", exchange);

        assertEquals("CUSTOMAPPIDDATA", exchange.getIn().getHeader("JMS_IBM_MQMD_ApplIdentityData"));
    }

    private RoutesBuilder route() {
        return new RouteBuilder() {
            @Override
            public void configure() throws Exception {
                from("direct:input")
                    .log(body().toString())
                    .to("wmq:QRM_AWS_ENTE_OUT?username=mqm&password=mqm");
            }
        };
    }

}