package com.openclassrooms.realestatemanager.ui.utils;

import java.util.Set;

public class WorkStore {

    private WorkStoreGateway workStoreGateway;

    public WorkStore(WorkStoreGateway workStoreGateway) {

        this.workStoreGateway = workStoreGateway;

    }

    public void clearBacklog() {
        this.workStoreGateway.getBacklog().clear();
    }

    public Set<String> getBackLog() {
        return workStoreGateway.getBacklog();
    }

    public void addTask(String task) {
        this.workStoreGateway.getBacklog().add(task);
    }
}
