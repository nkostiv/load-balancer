package com.nkostiv.loadbalancer;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.nkostiv.loadbalancer.model.Host;
import com.nkostiv.loadbalancer.model.LoadComparator;

public class LoadComparatorTest {

    private LoadComparator loadComparator;

    @BeforeEach
    public void setup() {
        loadComparator = new LoadComparator();
    }

    @Test
    public void whenCompareHostsWithLoadFactorLowerThresholdShouldBeEquals() {
        //given
        Host host1 = new Host("1", 0.5F);
        Host host2 = new Host("2", 0.7F);

        //when
        int result = loadComparator.compare(host1, host2);

        //then
        assertEquals(0, result);
    }

    @Test
    public void whenCompareHostsWithLoadFactorHigherThresholdShouldCompareByHostLoadFactor() {
        //given
        Host host1 = new Host("1", 0.8F);
        Host host2 = new Host("2", 0.8F);

        Host host3 = new Host("3", 0.8F);
        Host host4 = new Host("4", 0.9F);

        Host host5 = new Host("5", 0.9F);
        Host host6 = new Host("6", 0.8F);

        //when
        int sameLoadFactorResult = loadComparator.compare(host1, host2);
        int firstHostLowerLoadResult = loadComparator.compare(host3, host4);
        int secondHostLowerLoadResult = loadComparator.compare(host5, host6);

        //then
        assertAll(
                () -> assertEquals(0, sameLoadFactorResult),
                () -> assertEquals(-1, firstHostLowerLoadResult),
                () -> assertEquals(1, secondHostLowerLoadResult)
        );
    }
}
