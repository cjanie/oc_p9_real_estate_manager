package com.openclassrooms.realestatemanager;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.openclassrooms.realestatemanager.ui.utils.InMemoryWorkStoreGateway;
import com.openclassrooms.realestatemanager.ui.utils.WorkStore;
import com.openclassrooms.realestatemanager.ui.utils.WorkStoreGateway;
import com.openclassrooms.realestatemanager.ui.utils.WorkStoreGatewayConfig;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

public class WorkStoreTest {

    @Test
    public void whenAllWorkIsDoneBacklogShouldBeCleared() {

        InMemoryWorkStoreGateway workStoreGateway = new InMemoryWorkStoreGateway();
        Set<String> backlog = new HashSet<>();
        backlog.add("task");
        workStoreGateway.setBacklog(backlog);

        WorkStore workStore = new WorkStore(workStoreGateway);

        Assertions.assertEquals(1, workStore.getBackLog().size());

        workStore.clearBacklog();

        Assertions.assertTrue(workStoreGateway.getBacklog().isEmpty());
    }
}
