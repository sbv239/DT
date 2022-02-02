package com.tsystems.javaschool.tasks.subsequence;

import java.util.List;

public class Subsequence {

    /**
     * Checks if it is possible to get a sequence which is equal to the first
     * one by removing some elements from the second one.
     *
     * @param x first sequence
     * @param y second sequence
     * @return <code>true</code> if possible, otherwise <code>false</code>
     */
    @SuppressWarnings("rawtypes")

    public boolean find(List x, List y) throws IllegalArgumentException {
        if (x == null || y == null) {
            throw new IllegalArgumentException();
        }
        for (Object objX : x) {
                if (y.contains(objX)) {
                    y.removeAll(y.subList(0, y.indexOf(objX)));
                    continue;
                }
                return false;
        }
        return true;
    }
}