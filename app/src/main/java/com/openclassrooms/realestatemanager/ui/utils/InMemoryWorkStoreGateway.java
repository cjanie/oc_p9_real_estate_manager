package com.openclassrooms.realestatemanager.ui.utils;

import java.util.HashSet;
import java.util.Set;

public class InMemoryWorkStoreGateway implements WorkStoreGateway {

    private Set<String> backlog;

    public InMemoryWorkStoreGateway() {
        this.backlog = new HashSet<>();
    }

    public void setBacklog(Set<String> backlog) {
        this.backlog = backlog;
    }

    @Override
    public Set<String> getBacklog() {
        return this.backlog;
    }
}

