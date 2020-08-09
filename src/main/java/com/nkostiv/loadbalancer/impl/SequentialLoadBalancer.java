package com.nkostiv.loadbalancer.impl;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;

import com.nkostiv.loadbalancer.model.Host;
import com.nkostiv.loadbalancer.model.Request;

public class SequentialLoadBalancer implements LoadBalancer {

    private Deque<Host> hostDeque;

    public SequentialLoadBalancer() {
    }

    public SequentialLoadBalancer(List<Host> hostList) {
        hostDeque = new ArrayDeque<>(hostList);
    }

    @Override
    public void handleRequest(Request request) {
        if (hostDeque == null || hostDeque.isEmpty()) {
            throw new IllegalArgumentException("Hosts are empty");
        }

        Host host = hostDeque.removeFirst();
        host.handleRequest(request);
        hostDeque.addLast(host);
    }

    @Override
    public void setHostList(List<Host> hostList) {
        hostDeque = new ArrayDeque<>(hostList);
    }
}
