package com.nkostiv.loadbalancer;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.nkostiv.loadbalancer.impl.LoadBalancer;
import com.nkostiv.loadbalancer.impl.WeightLoadBalancer;
import com.nkostiv.loadbalancer.model.Host;
import com.nkostiv.loadbalancer.model.Request;

@ExtendWith(MockitoExtension.class)
public class WeightLoadBalancerTest {

    private LoadBalancer loadBalancer;

    private List<Host> hostList;

    @Mock
    Host firstHost;

    @Mock
    Host secondHost;

    @BeforeEach
    public void setup() {
        loadBalancer = new WeightLoadBalancer();
        hostList = Stream.of(
                firstHost,
                secondHost
        ).collect(Collectors.toList());
    }

    @Test
    public void whenOneHostHasLoadLowerThresholdShouldHandleRequestFirst() {
        //given
        when(firstHost.getLoad()).thenReturn(0.8f);
        when(secondHost.getLoad()).thenReturn(0.6f);

        loadBalancer.setHostList(hostList);

        Request request1 = new Request("1");

        //when
        loadBalancer.handleRequest(request1);

        //then
        verify(secondHost).handleRequest(request1);
    }

    @Test
    public void whenBothHostsHasLoadUpperThresholdHostWithLowestLoadShouldHandleRequestFirst() {
        //given
        when(firstHost.getLoad()).thenReturn(0.9f);
        when(secondHost.getLoad()).thenReturn(0.8f);

        loadBalancer.setHostList(hostList);

        Request request1 = new Request("1");

        //when
        loadBalancer.handleRequest(request1);

        //then
        verify(secondHost).handleRequest(request1);
    }

    @Test
    public void whenLoadTheSameShouldHandleRequestsOneByOne() {
        //given
        loadBalancer.setHostList(hostList);

        Request request1 = new Request("1");

        //when
        loadBalancer.handleRequest(request1);

        //then
        verify(firstHost).handleRequest(request1);
    }
}
