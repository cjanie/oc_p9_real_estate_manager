package com.openclassrooms.realestatemanager;

import android.app.Application;

import com.openclassrooms.realestatemanager.businesslogic.gateways.EstateGateway;
import com.openclassrooms.realestatemanager.businesslogic.usecases.GetEstatesUseCase;
import com.openclassrooms.realestatemanager.data.gatewaysimpl.EstateGatewayImpl;
import com.openclassrooms.realestatemanager.ui.viewmodels.factories.EstatesViewModelFactory;

public class Launch extends Application {

    // Gateways
    private EstateGateway estateGateway;

    // Use Cases
    private GetEstatesUseCase getEstatesUseCase;

    // View Model Factory
    private EstatesViewModelFactory estatesViewModelFactory;

    private synchronized EstateGateway estateGateway() {
        if(this.estateGateway == null) {
            this.estateGateway = new EstateGatewayImpl();
        }
        return this.estateGateway;
    }

    private synchronized GetEstatesUseCase getEstatesUseCase() {
        if(this.getEstatesUseCase == null) {
            this.getEstatesUseCase = new GetEstatesUseCase(this.estateGateway());
        }
        return this.getEstatesUseCase;
    }

    public synchronized EstatesViewModelFactory estatesViewModelFactory() {
        if(this.estatesViewModelFactory == null) {
            this.estatesViewModelFactory = new EstatesViewModelFactory(getEstatesUseCase());
        }
        return this.estatesViewModelFactory;
    }
}
