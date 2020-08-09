package com.nkostiv.loadbalancer.model;

import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Host {

    private static Logger logger = LoggerFactory.getLogger(Host.class);

    private String hostName;
    private float load;

    public Host(String hostName) {
        this.hostName = hostName;
    }

    public Host(String hostName, float load) {
        this(hostName);
        this.load = load;
    }

    public float getLoad() {
        return load;
    }

    public void handleRequest(Request request) {
        Objects.requireNonNull(request);
        logger.debug("Host: {} received request {}, host load: {}", hostName, request.getBody(), load);
        if (load < 0.8f) {
            load += 0.2f;
        }
        logger.debug("Host: {} processed request, host load: {}", hostName, load);
    }

    @Override
    public String toString() {
        return "Host{" +
                "hostName='" + hostName + '\'' +
                ", load=" + load +
                '}';
    }
}
