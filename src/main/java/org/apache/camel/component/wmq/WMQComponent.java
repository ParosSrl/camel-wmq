package org.apache.camel.component.wmq;

import org.apache.camel.Endpoint;
import org.apache.camel.component.jms.JmsComponent;
import org.apache.camel.component.jms.JmsConfiguration;
import org.springframework.jms.connection.UserCredentialsConnectionFactoryAdapter;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Properties;

public class WMQComponent extends JmsComponent {

    public WMQComponent() {
        super(WMQEndpoint.class);
    }

    @Override
    protected JmsConfiguration createConfiguration() {
        ConnectionFactoryParameters connectionFactoryParameters = readParametersFrom("mq.properties");
        JmsConfiguration jmsConfiguration = new JmsConfiguration(new WmqConnectionFactory(connectionFactoryParameters));
        jmsConfiguration.setDestinationResolver(new WmqDestinationResolver());
        return jmsConfiguration;
    }

    @Override
    public Endpoint createEndpoint(String uri, String remaining, Map<String, Object> parameters) throws Exception {
        WMQEndpoint endpoint = new WMQEndpoint(this, uri, remaining);
        String username = this.getAndRemoveParameter(parameters, "username", String.class);
        String password = this.getAndRemoveParameter(parameters, "password", String.class);
        if(username != null && password != null) {
            JmsConfiguration configuration = endpoint.getConfiguration();
            UserCredentialsConnectionFactoryAdapter strategyVal = new UserCredentialsConnectionFactoryAdapter();
            strategyVal.setTargetConnectionFactory(configuration.getConnectionFactory());
            strategyVal.setPassword(password);
            strategyVal.setUsername(username);
            configuration.setConnectionFactory(strategyVal);
        } else if(username != null || password != null) {
            throw new IllegalArgumentException("The JmsComponent\'s username or password is null");
        }
        return endpoint;
    }

    private ConnectionFactoryParameters readParametersFrom(String resource) {
        Properties properties = new Properties();
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(resource);
        ConnectionFactoryParameters connectionFactoryParameters;
        try {
            properties.load(inputStream);
            String queueManager = "TEST1";
            String hostname = properties.getProperty(queueManager + ".hostname");
            String port = properties.getProperty(queueManager + ".port");
            String channel = properties.getProperty(queueManager + ".channel");

            connectionFactoryParameters = new ConnectionFactoryParameters(queueManager, hostname, Integer.valueOf(port), channel);

        } catch (IOException e) {
            throw new RuntimeException("Cannot load mq.properties file", e);
        }
        finally {
            closeQuietly(inputStream);
        }
        return connectionFactoryParameters;
    }

    private void closeQuietly(InputStream inputStream) {
        try {
            if (inputStream != null) {
                inputStream.close();
            }
        } catch (IOException e) {
            throw new RuntimeException("Cannot close stream", e);
        }
    }

}
