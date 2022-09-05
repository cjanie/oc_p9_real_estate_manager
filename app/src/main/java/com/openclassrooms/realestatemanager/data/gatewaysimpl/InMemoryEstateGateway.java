package com.openclassrooms.realestatemanager.data.gatewaysimpl;

import com.openclassrooms.realestatemanager.businesslogic.entities.Estate;
import com.openclassrooms.realestatemanager.businesslogic.gateways.EstateGateway;

import java.util.ArrayList;
import java.util.List;

public class InMemoryEstateGateway implements EstateGateway {

    private List<Estate> estates;

    public InMemoryEstateGateway() {
        this.estates = new ArrayList<>();
    }

    public void setEstates(List<Estate> estates) {
        this.estates = estates;
    }

    @Override
    public List<Estate> getEstates() {
        return this.estates;
    }
}
