package org.apache.camel.component.wmq;

import com.ibm.mq.jms.MQConnectionFactory;
import org.apache.camel.CamelContext;
import org.apache.camel.Endpoint;
import org.apache.camel.component.jms.JmsComponent;
import org.apache.camel.component.jms.JmsConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.connection.UserCredentialsConnectionFactoryAdapter;

import javax.jms.JMSException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Properties;

public class WMQComponent extends JmsComponent {

    private final static Logger LOGGER = LoggerFactory.getLogger(WMQComponent.class);


    public WMQComponent() {
        super(WMQEndpoint.class);
    }

    public WMQComponent(CamelContext camelContext) {
        super(camelContext, WMQEndpoint.class);
    }

    public WMQComponent(JmsConfiguration configuration) {
        super(configuration);
    }

    @Override
    protected JmsConfiguration createConfiguration() {
        Properties properties = new Properties();
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("mq.properties");
        try {
            properties.load(inputStream);
            String queueManager = "TEST1";
            String hostname = properties.getProperty(queueManager + ".hostname");
            String port = properties.getProperty(queueManager + ".port");
            String channel = properties.getProperty(queueManager + ".channel");

            MQConnectionFactory connectionFactory = createConnectionFactory(queueManager, hostname, port, channel);
            return new JmsConfiguration(connectionFactory);

        } catch (IOException e) {
            throw new RuntimeException("Cannot load mq.properties file", e);
        }
        finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (IOException e) {
                throw new RuntimeException("Cannot close stream", e);
            }
        }

    }

    private MQConnectionFactory createConnectionFactory(String queueManager, String hostname, String port, String channel) {
        MQConnectionFactory connectionFactory = new MQConnectionFactory();
        try {
            connectionFactory.setQueueManager(queueManager);
            connectionFactory.setHostName(hostname);
            connectionFactory.setChannel(channel);
            connectionFactory.setPort(Integer.valueOf(port));
            connectionFactory.setTransportType(1);
        } catch (JMSException e) {
            throw new RuntimeException("Cannot create connection factory", e);
        }
        return connectionFactory;
    }

    @Override
    public Endpoint createEndpoint(String uri, String remaining, Map<String, Object> parameters) throws Exception {
        WMQEndpoint endpoint = new WMQEndpoint(this, uri, remaining);
        String username = this.getAndRemoveParameter(parameters, "username", String.class);
        String password = this.getAndRemoveParameter(parameters, "password", String.class);
        if(username != null && password != null) {
            UserCredentialsConnectionFactoryAdapter strategyVal = new UserCredentialsConnectionFactoryAdapter();
            strategyVal.setTargetConnectionFactory(endpoint.getConfiguration().getConnectionFactory());
            strategyVal.setPassword(password);
            strategyVal.setUsername(username);
            endpoint.getConfiguration().setConnectionFactory(strategyVal);
        } else if(username != null || password != null) {
            throw new IllegalArgumentException("The JmsComponent\'s username or password is null");
        }
        return endpoint;
    }

}
