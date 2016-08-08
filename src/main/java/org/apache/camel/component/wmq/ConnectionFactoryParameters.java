package org.apache.camel.component.wmq;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConnectionFactoryParameters {
    private final String queueManager;
    private final String hostname;
    private final Integer port;
    private final String channel;

    public ConnectionFactoryParameters(String queueManager, String hostname, Integer port, String channel) {
        this.queueManager = queueManager;
        this.hostname = hostname;
        this.port = port;
        this.channel = channel;
    }

    public static ConnectionFactoryParameters readParametersFrom(String resource) {
        Properties properties = new Properties();
        InputStream inputStream = ConnectionFactoryParameters.class.getClassLoader().getResourceAsStream(resource);
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

    public String getQueueManager() {
        return queueManager;
    }

    public String getHostname() {
        return hostname;
    }

    public Integer getPort() {
        return port;
    }

    public String getChannel() {
        return channel;
    }

    private static void closeQuietly(InputStream inputStream) {
        try {
            if (inputStream != null) {
                inputStream.close();
            }
        } catch (IOException e) {
            throw new RuntimeException("Cannot close stream", e);
        }
    }
}
