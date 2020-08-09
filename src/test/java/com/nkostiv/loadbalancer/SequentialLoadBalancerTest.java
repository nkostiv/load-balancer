package com.nkostiv.loadbalancer;

import static org.mockito.Mockito.verify;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.nkostiv.loadbalancer.impl.LoadBalancer;
import com.nkostiv.loadbalancer.impl.SequentialLoadBalancer;
import com.nkostiv.loadbalancer.model.Host;
import com.nkostiv.loadbalancer.model.Request;

@ExtendWith(MockitoExtension.class)
public class SequentialLoadBalancerTest {

    private LoadBalancer loadBalancer;

    @Mock
    Host firstHost;

    @Mock
    Host secondHost;

    @BeforeEach
    public void setup() {
        List<Host> hostList = Stream.of(
                firstHost,
                secondHost
        ).collect(Collectors.toList());

        loadBalancer = new SequentialLoadBalancer(hostList);
    }

    @Test
    public void whenHandlingMultipleRequestsShouldHandleThemSequentially() {
        //given
        Request request1 = new Request("1");
        Request request2 = new Request("2");
        Request request3 = new Request("3");
        Request request4 = new Request("4");

        //when
        //then
        loadBalancer.handleRequest(request1);
        verify(firstHost).handleRequest(request1);

        loadBalancer.handleRequest(request2);
        verify(secondHost).handleRequest(request2);

        loadBalancer.handleRequest(request3);
        verify(firstHost).handleRequest(request3);

        loadBalancer.handleRequest(request4);
        verify(secondHost).handleRequest(request4);
    }
}
