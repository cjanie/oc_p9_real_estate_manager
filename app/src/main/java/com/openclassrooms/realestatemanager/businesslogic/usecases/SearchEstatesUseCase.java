package com.openclassrooms.realestatemanager.businesslogic.usecases;

import com.openclassrooms.realestatemanager.businesslogic.MapSearchEstatesParamsConfig;
import com.openclassrooms.realestatemanager.businesslogic.entities.Estate;
import com.openclassrooms.realestatemanager.businesslogic.enums.EstateType;
import com.openclassrooms.realestatemanager.businesslogic.gateways.EstateGateway;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SearchEstatesUseCase {

    private EstateGateway estateGateway;

    public SearchEstatesUseCase(EstateGateway estateGateway) {
        this.estateGateway = estateGateway;
    }

    public List<Estate> find(EstateType type) {
        return this.estateGateway.searchByType(type);
    }

    public List<Estate> find(String location) {
        return this.estateGateway.searchByLocation(location);
    }

    public List<Estate> find(Map<String, Object> searchParameters) {
        List<Estate> found = new ArrayList<>();

        EstateType type = (EstateType) searchParameters.get(MapSearchEstatesParamsConfig.TYPE);
        String location = (String) searchParameters.get(MapSearchEstatesParamsConfig.LOCATION);

        if(type != null) {
            found.addAll(this.find(type));
            if(location != null && !found.isEmpty()) {
                for(int i=0; i<found.size(); i++) {
                    if((found.get(i).getLocation() == null)
                            || (found.get(i).getLocation() != null && !found.get(i).getLocation().equals(location))
                    ) {
                        found.remove(i);
                    }
                }
            }
            return found;
        }

        if(location != null) {
            found.addAll(this.find(location));
            return found;
        }

        return found;
    }

}
