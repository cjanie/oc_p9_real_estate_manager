package com.openclassrooms.realestatemanager;

import android.app.Application;

import com.openclassrooms.realestatemanager.businesslogic.gateways.EstateCommandGateway;
import com.openclassrooms.realestatemanager.businesslogic.gateways.EstateGateway;
import com.openclassrooms.realestatemanager.businesslogic.usecases.GetEstateByIdUseCase;
import com.openclassrooms.realestatemanager.businesslogic.usecases.GetEstatesUseCase;
import com.openclassrooms.realestatemanager.businesslogic.usecases.SaveEstateUseCase;
import com.openclassrooms.realestatemanager.businesslogic.usecases.SearchEstatesUseCase;
import com.openclassrooms.realestatemanager.businesslogic.wifimode.gateways.GeolocationGateway;
import com.openclassrooms.realestatemanager.businesslogic.wifimode.usecases.GeolocalizeFromAddressUseCase;
import com.openclassrooms.realestatemanager.data.database.AppDataBase;
import com.openclassrooms.realestatemanager.data.database.dao.EstateDAO;
import com.openclassrooms.realestatemanager.data.gatewaysimpl.EstateCommandGatewayImpl;
import com.openclassrooms.realestatemanager.data.gatewaysimpl.EstateGatewayImpl;
import com.openclassrooms.realestatemanager.data.gatewaysimpl.GeolocationGatewayImpl;
import com.openclassrooms.realestatemanager.dateprovider.DateProvider;
import com.openclassrooms.realestatemanager.dateprovider.RealDateProvider;
import com.openclassrooms.realestatemanager.ui.viewmodels.factories.DetailsViewModelFactory;
import com.openclassrooms.realestatemanager.ui.viewmodels.factories.EstatesViewModelFactory;
import com.openclassrooms.realestatemanager.ui.viewmodels.factories.FormViewModelFactory;
import com.openclassrooms.realestatemanager.ui.viewmodels.factories.GeolocationViewModelFactory;
import com.openclassrooms.realestatemanager.ui.viewmodels.factories.SearchViewModelFactory;

public class Launch extends Application {

    // Dependencies
    private AppDataBase appDataBase;

    private EstateDAO estateDAO;

    // Gateways
    private EstateGateway estateGateway;
    private EstateCommandGateway estateCommandGateway;
    private GeolocationGateway geolocationGateway;

    // Providers
    private DateProvider dateProvider;

    // Use Cases
    private GetEstatesUseCase getEstatesUseCase;
    private SaveEstateUseCase saveEstateUseCase;
    private SearchEstatesUseCase searchEstatesUseCase;
    private GetEstateByIdUseCase getEstateByIdUseCase;
    private GeolocalizeFromAddressUseCase geolocalizeFromAddressUseCase;

    // View Model Factory
    private EstatesViewModelFactory estatesViewModelFactory;
    private FormViewModelFactory formViewModelFactory;
    private SearchViewModelFactory searchViewModelFactory;
    private DetailsViewModelFactory detailsViewModelFactory;
    private GeolocationViewModelFactory geolocationViewModelFactory;


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

    private synchronized GeolocationGateway geolocationGateway() {
        if(this.geolocationGateway == null) {
            this.geolocationGateway = new GeolocationGatewayImpl();
        }
        return this.geolocationGateway;
    }

    // Providers
    private synchronized DateProvider dateProvider() {
        if(this.dateProvider == null) {
            this.dateProvider = new RealDateProvider();
        }
        return this.dateProvider;
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
            this.saveEstateUseCase = new SaveEstateUseCase(
                    this.estateCommandGateway(),
                    this.dateProvider()
            );
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
            this.getEstateByIdUseCase = new GetEstateByIdUseCase(
                    this.estateGateway()
            );
        }
        return this.getEstateByIdUseCase;
    }

    private synchronized GeolocalizeFromAddressUseCase geolocalizeFromAddressUseCase() {
        if(this.geolocalizeFromAddressUseCase == null) {
            this.geolocalizeFromAddressUseCase = new GeolocalizeFromAddressUseCase(
                    this.geolocationGateway()
            );
        }
        return this.geolocalizeFromAddressUseCase;
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

    public synchronized GeolocationViewModelFactory geolocationViewModelFactory() {
        if(this.geolocationViewModelFactory == null) {
            this.geolocationViewModelFactory = new GeolocationViewModelFactory(
                    this.geolocalizeFromAddressUseCase()
            );
        }
        return this.geolocationViewModelFactory;
    }
}
