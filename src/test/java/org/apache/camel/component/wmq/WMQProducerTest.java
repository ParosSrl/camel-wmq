package org.apache.camel.component.wmq;

import org.apache.camel.CamelContext;
import org.apache.camel.RoutesBuilder;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.junit.Test;

public class WMQProducerTest extends CamelTestSupport {

    @Override
    protected CamelContext createCamelContext() throws Exception {
        CamelContext context = super.createCamelContext();
        context.addRoutes(route());
        System.out.println("creo!!!");
        return context;
    }

    @Test
    public void test() throws Exception {
        System.out.println("mando!");
        template().sendBody("direct:input", "aa2e23rwe");
    }

    private RoutesBuilder route() {
        return new RouteBuilder() {
            @Override
            public void configure() throws Exception {
                from("direct:input")
                    .log(body().toString())
                    .setHeader("JMS_IBM_MQMD_ApplIdentityData", constant("aarfe2234"))
                    .setHeader("JMS_IBM_MQMD_ApplIdentityData", constant("aarfe2234"))
                    .to("wmq:QRM_AWS_ENTE_OUT?username=mqm&password=mqm");
            }
        };
    }

}