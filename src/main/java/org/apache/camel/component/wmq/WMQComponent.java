package org.apache.camel.component.wmq;

import org.apache.camel.Endpoint;
import org.apache.camel.component.jms.JmsComponent;
import org.apache.camel.component.jms.JmsConfiguration;
import org.springframework.jms.connection.UserCredentialsConnectionFactoryAdapter;

import java.util.Map;

public class WMQComponent extends JmsComponent {

    private JmsConfiguration configuration;

    public WMQComponent() {
        super(WMQEndpoint.class);
    }

    public WMQComponent(JmsConfiguration configuration) {
        this.configuration = configuration;
    }

    @Override
    protected JmsConfiguration createConfiguration() {
        ConnectionFactoryParameters connectionFactoryParameters = ConnectionFactoryParameters.readParametersFrom("mq.properties", "TEST1");
        configuration.setConnectionFactory(new WmqConnectionFactory(connectionFactoryParameters));
        configuration.setDestinationResolver(new WmqDestinationResolver());
        return configuration;
    }

    @Override
    public Endpoint createEndpoint(String uri, String remaining, Map<String, Object> parameters) throws Exception {
        WMQEndpoint endpoint = new WMQEndpoint(this, uri, remaining);
        JmsConfiguration configuration = endpoint.getConfiguration();
        String username = this.getAndRemoveParameter(parameters, "username", String.class);
        String password = this.getAndRemoveParameter(parameters, "password", String.class);
        configuration.setConnectionFactory(createUserCredentialsConnectionFactoryAdapter(configuration, username, password));
        return endpoint;
    }

    private UserCredentialsConnectionFactoryAdapter createUserCredentialsConnectionFactoryAdapter(JmsConfiguration configuration, String username, String password) {
        UserCredentialsConnectionFactoryAdapter strategyVal = new UserCredentialsConnectionFactoryAdapter();
        if(username != null && password != null) {
            strategyVal.setTargetConnectionFactory(configuration.getConnectionFactory());
            strategyVal.setPassword(password);
            strategyVal.setUsername(username);
        } else if(username != null || password != null) {
            throw new IllegalArgumentException("The JmsComponent\'s username or password is null");
        }
        return strategyVal;
    }

}
