package com.nkostiv.loadbalancer.impl;

import java.util.List;
import java.util.PriorityQueue;

import com.nkostiv.loadbalancer.model.LoadComparator;
import com.nkostiv.loadbalancer.model.Host;
import com.nkostiv.loadbalancer.model.Request;

public class WeightLoadBalancer implements LoadBalancer {

    private final PriorityQueue<Host> hostPriorityQueue;

    public WeightLoadBalancer() {
        hostPriorityQueue = new PriorityQueue<>(new LoadComparator());
    }

    @Override
    public void handleRequest(Request request) {
        if (hostPriorityQueue == null || hostPriorityQueue.isEmpty()) {
            throw new IllegalArgumentException("Hosts are empty");
        }

        Host host = hostPriorityQueue.remove();
        host.handleRequest(request);
        hostPriorityQueue.add(host);
    }

    @Override
    public void setHostList(List<Host> hostList) {
        hostPriorityQueue.addAll(hostList);
    }
}
