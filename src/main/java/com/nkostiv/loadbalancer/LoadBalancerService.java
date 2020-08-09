package com.nkostiv.loadbalancer;

import static com.nkostiv.loadbalancer.BalancerFactoryType.SEQUENTIAL;
import static com.nkostiv.loadbalancer.BalancerFactoryType.WEIGHT_BALANCED;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.nkostiv.loadbalancer.impl.LoadBalancer;
import com.nkostiv.loadbalancer.model.Host;
import com.nkostiv.loadbalancer.model.Request;

public class LoadBalancerService {

    private final Map<String, BalancerFactoryType> typeMap = new HashMap<>();
    private final LoadBalancer loadBalancer;


    public LoadBalancerService(List<Host> hostList, String lbAlgorithm) {
        typeMap.put("round", SEQUENTIAL);
        typeMap.put("weight", WEIGHT_BALANCED);

        loadBalancer = typeMap.get(lbAlgorithm).getLoadBalancer();
        loadBalancer.setHostList(hostList);
    }

    public void handleRequst(Request request) {
        loadBalancer.handleRequest(request);
    }
}
