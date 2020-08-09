package com.nkostiv.loadbalancer;

import com.nkostiv.loadbalancer.impl.LoadBalancer;
import com.nkostiv.loadbalancer.impl.SequentialLoadBalancer;
import com.nkostiv.loadbalancer.impl.WeightLoadBalancer;

public enum BalancerFactoryType {

    SEQUENTIAL() {
        @Override
        public LoadBalancer getLoadBalancer() {
            return new SequentialLoadBalancer();
        }
    },

    WEIGHT_BALANCED() {
        @Override
        public LoadBalancer getLoadBalancer() {
            return new WeightLoadBalancer();
        }
    };

    public abstract LoadBalancer getLoadBalancer();
}
