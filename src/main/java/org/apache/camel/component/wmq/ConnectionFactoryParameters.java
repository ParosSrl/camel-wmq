package org.apache.camel.component.wmq;

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
}
