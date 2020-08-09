package com.nkostiv.loadbalancer.model;

import java.util.Comparator;

public class LoadComparator implements Comparator<Host> {

    private static final float LOAD_FACTOR = 0.75f;

    @Override
    public int compare(Host h1, Host h2) {
        float h1Load = h1.getLoad();
        float h2Load = h2.getLoad();

        if (h1Load == h2Load || (h1Load < LOAD_FACTOR && h2Load < LOAD_FACTOR)) {
            return 0;
        }

        if (h1Load < LOAD_FACTOR) {
            return -1;
        }

        if (h2Load < LOAD_FACTOR) {
            return 1;
        }

        return Float.compare(h1Load, h2Load);
    }
}
