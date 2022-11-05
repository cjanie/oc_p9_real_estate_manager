package com.openclassrooms.realestatemanager;

import android.app.Application;

import com.openclassrooms.realestatemanager.businesslogic.gateways.EstateCommandGateway;
import com.openclassrooms.realestatemanager.businesslogic.gateways.EstateGateway;
import com.openclassrooms.realestatemanager.businesslogic.usecases.GetEstateByIdUseCase;
import com.openclassrooms.realestatemanager.businesslogic.usecases.GetEstatesUseCase;
import com.openclassrooms.realestatemanager.businesslogic.usecases.SaveEstateUseCase;
import com.openclassrooms.realestatemanager.businesslogic.usecases.SearchEstatesUseCase;
import com.openclassrooms.realestatemanager.data.database.AppDataBase;
import com.openclassrooms.realestatemanager.data.database.dao.EstateDAO;
import com.openclassrooms.realestatemanager.data.database.dao.EstateWithMediaDAO;
import com.openclassrooms.realestatemanager.data.gatewaysimpl.EstateCommandGatewayImpl;
import com.openclassrooms.realestatemanager.data.gatewaysimpl.EstateGatewayImpl;
import com.openclassrooms.realestatemanager.ui.viewmodels.factories.DetailsViewModelFactory;
import com.openclassrooms.realestatemanager.ui.viewmodels.factories.EstatesViewModelFactory;
import com.openclassrooms.realestatemanager.ui.viewmodels.factories.FormViewModelFactory;
import com.openclassrooms.realestatemanager.ui.viewmodels.factories.SearchViewModelFactory;

public class Launch extends Application {

    // Dependencies
    private AppDataBase appDataBase;

    private EstateDAO estateDAO;

    private EstateWithMediaDAO estateWithMediaDAO;

    // Gateways
    private EstateGateway estateGateway;
    private EstateCommandGateway estateCommandGateway;

    // Use Cases
    private GetEstatesUseCase getEstatesUseCase;
    private SaveEstateUseCase saveEstateUseCase;
    private SearchEstatesUseCase searchEstatesUseCase;
    private GetEstateByIdUseCase getEstateByIdUseCase;

    // View Model Factory
    private EstatesViewModelFactory estatesViewModelFactory;
    private FormViewModelFactory formViewModelFactory;
    private SearchViewModelFactory searchViewModelFactory;
    private DetailsViewModelFactory detailsViewModelFactory;


    /////

    //Dependencies
    private synchronized AppDataBase appDataBase() {
        this.appDataBase = AppDataBase.getInstance(this);
        return this.appDataBase;
    }

    private synchronized EstateDAO estateDAO() {
        if(this.estateDAO == null) {
            this.estateDAO = this.appDataBase().estateDAO();
        }
        return this.estateDAO;
    }

    private synchronized EstateWithMediaDAO estateWithMediaDAO() {
        if(this.estateWithMediaDAO == null) {
            this.estateWithMediaDAO = this.appDataBase().estateWithMediaDAO();
        }
        return this.estateWithMediaDAO;
    }

    // Gateways
    private synchronized EstateGateway estateGateway() {
        if(this.estateGateway == null) {
            this.estateGateway = new EstateGatewayImpl(this.estateDAO());
        }
        return this.estateGateway;
    }

    private synchronized EstateCommandGateway estateCommandGateway() {
        if(this.estateCommandGateway == null) {
            this.estateCommandGateway = new EstateCommandGatewayImpl(this.estateDAO());
        }
        return this.estateCommandGateway;
    }

    // Use Cases
    private synchronized GetEstatesUseCase getEstatesUseCase() {
        if(this.getEstatesUseCase == null) {
            this.getEstatesUseCase = new GetEstatesUseCase(this.estateGateway());
        }
        return this.getEstatesUseCase;
    }

    private synchronized SaveEstateUseCase saveEstateUseCase() {
        if(this.saveEstateUseCase == null) {
            this.saveEstateUseCase = new SaveEstateUseCase(this.estateCommandGateway());
        }
        return this.saveEstateUseCase;
    }

    private synchronized SearchEstatesUseCase searchEstatesUseCase() {
        if(this.searchEstatesUseCase == null) {
            this.searchEstatesUseCase = new SearchEstatesUseCase(this.estateGateway());
        }
        return this.searchEstatesUseCase;
    }

    private synchronized GetEstateByIdUseCase getEstateByIdUseCase() {
        if(this.getEstateByIdUseCase == null) {
            this.getEstateByIdUseCase = new GetEstateByIdUseCase(this.estateGateway());
        }
        return this.getEstateByIdUseCase;
    }

    // View Model Factories
    public synchronized EstatesViewModelFactory estatesViewModelFactory() {
        if(this.estatesViewModelFactory == null) {
            this.estatesViewModelFactory = new EstatesViewModelFactory(getEstatesUseCase());
        }
        return this.estatesViewModelFactory;
    }

    public synchronized FormViewModelFactory formViewModelFactory() {
        if(this.formViewModelFactory == null) {
            this.formViewModelFactory = new FormViewModelFactory(this.saveEstateUseCase());
        }
        return this.formViewModelFactory;
    }

    public synchronized SearchViewModelFactory searchViewModelFactory() {
        if(this.searchViewModelFactory == null) {
            this.searchViewModelFactory = new SearchViewModelFactory(this.searchEstatesUseCase());
        }
        return this.searchViewModelFactory;
    }

    public synchronized DetailsViewModelFactory detailsViewModelFactory() {
        if(this.detailsViewModelFactory == null) {
            this.detailsViewModelFactory = new DetailsViewModelFactory(this.getEstateByIdUseCase());
        }
        return this.detailsViewModelFactory;
    }
}
