package com.openclassrooms.realestatemanager.data.gatewaysimpl;

import com.openclassrooms.realestatemanager.businesslogic.entities.Estate;
import com.openclassrooms.realestatemanager.businesslogic.enums.Devise;
import com.openclassrooms.realestatemanager.businesslogic.enums.EstateType;
import com.openclassrooms.realestatemanager.businesslogic.gateways.EstateGateway;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EstateGatewayImpl implements EstateGateway {

    private List<Estate> estates;

    public EstateGatewayImpl() {
        Estate estate1 = new Estate();
        estate1.setType(EstateType.DUPLEX);
        estate1.setLocation("London");
        estate1.setPrice(10000.0f);
        estate1.setDevise(Devise.DOLLAR);

        Estate estate2 = new Estate();
        estate2.setType(EstateType.FLAT);
        estate2.setLocation("Manchester");
        estate2.setPrice(123000.0f);
        estate2.setDevise(Devise.DOLLAR);

        this.estates = new ArrayList<>();
        this.estates.add(estate1);
        this.estates.add(estate2);
    }

    @Override
    public List<Estate> getEstates() {
        return this.estates;
    }
}
