package com.nkostiv.loadbalancer.impl;

import java.util.List;

import com.nkostiv.loadbalancer.model.Host;
import com.nkostiv.loadbalancer.model.Request;

public interface LoadBalancer {

    public void handleRequest(Request request);

    public void setHostList(List<Host> hostList);
}
