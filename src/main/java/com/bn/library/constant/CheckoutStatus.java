package com.bn.library.constant;

public enum CheckoutStatus implements Comparable<CheckoutStatus> {
    WAITING(10), IN_PROGRESS(20), CANCELED(35), DONE(40);

    private final int weight;

    CheckoutStatus(int weight) {
        this.weight = weight;
    }

    public int getWeight() {
        return weight;
    }
}
