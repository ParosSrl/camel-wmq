package org.apache.camel.component.wmq;

import org.apache.camel.CamelContext;
import org.apache.camel.RoutesBuilder;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Date;

public class WMQProducerTest extends CamelTestSupport {

    @Override
    protected CamelContext createCamelContext() throws Exception {
        CamelContext context = super.createCamelContext();
        context.addRoutes(route());
        return context;
    }

    @Test
    public void test() throws Exception {
        template().sendBody("direct:input", "aa2e23rwe");
    }

    private RoutesBuilder route() {
        return new RouteBuilder() {
            @Override
            public void configure() throws Exception {
                from("direct:input")
                    .log(body().toString())
                    .setHeader("JMS_IBM_MQMD_ApplIdentityData", constant("CAMELTESTAPPIDDATA"))
                    .setHeader("JMS_IBM_MQMD_PutApplName", constant("Wmq camel component"))
                    .setHeader("JMS_IBM_PutDate", constant("20160728"))
                    .setHeader("JMS_IBM_PutTime", constant("07004301"))
                    .setHeader("JMS_IBM_Character_Set", constant("UTF-8"))
                    .setHeader("JMS_IBM_Encoding", constant(273))
                    .setHeader("JMS_IBM_MsgType", constant(8))
                    .setHeader("JMS_IBM_PutApplType", constant(28))
                    .to("wmq:QRM_AWS_ENTE_OUT?username=mqm&password=mqm");
            }
        };
    }

    private Object toIbmPutDate(Date date) {
        return new SimpleDateFormat("yyyyMMdd").format(date);
    }

}