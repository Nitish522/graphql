package com.example.demo.models;

public class PageInfo {
    private final String startCursor;
    private final String endCursor;
    private final boolean hasPreviousPage;
    private final boolean hasNextPage;
    private final Integer offSet;
    private final Integer item;

    public PageInfo(String startCursor, String endCursor, Integer totalItems, Integer offSet, Integer item) {
        this.startCursor = startCursor;
        this.endCursor = endCursor;
        this.hasPreviousPage = offSet > 1;
        this.hasNextPage = totalItems > offSet + item;
        this.offSet = offSet;
        this.item = item;
    }

    public Integer getOffSet() {
        return offSet;
    }

    public Integer getItem() {
        return item;
    }

    public String getStartCursor() {
        return startCursor;
    }

    public String getEndCursor() {
        return endCursor;
    }

    public boolean isHasPreviousPage() {
        return hasPreviousPage;
    }

    public boolean isHasNextPage() {
        return hasNextPage;
    }
}
