package com.openclassrooms.realestatemanager.data.gatewaysimpl;

import com.openclassrooms.realestatemanager.businesslogic.entities.Estate;
import com.openclassrooms.realestatemanager.businesslogic.enums.EstateType;
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

    @Override
    public List<Estate> searchByType(EstateType type) {
        List<Estate> found = new ArrayList<>();
        if(!this.estates.isEmpty()) {
            for(Estate e: this.estates) {
                if(e.getType() != null && e.getType().equals(type)) {
                    found.add(e);
                }
            }
        }
        return found;
    }

    @Override
    public List<Estate> searchByLocation(String location) {
        List<Estate> found = new ArrayList<>();
        if(!this.estates.isEmpty()) {
            for(Estate e: this.estates) {
                if(e.getLocation() != null && e.getLocation().equals(location)) {
                    found.add(e);
                }
            }
        }
        return found;
    }

    @Override
    public Estate getEstateById(long id) {
        if(!this.estates.isEmpty()) {
            for(Estate e: this.estates) {
                if(e.getId() == id) {
                    return e;
                }
            }
        }
        return null;
    }

}
