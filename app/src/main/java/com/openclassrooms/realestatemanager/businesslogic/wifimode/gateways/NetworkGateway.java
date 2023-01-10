package com.openclassrooms.realestatemanager.businesslogic.wifimode.gateways;

import io.reactivex.Observable;

public interface NetworkGateway {

    Observable<Boolean> isNetworkAvailable();
    Observable<Boolean> isNetworkConnected();
    Observable<String> getMessage();

}
