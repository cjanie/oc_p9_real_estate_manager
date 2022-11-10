package com.openclassrooms.realestatemanager.businesslogic.usecases;

import com.openclassrooms.realestatemanager.businesslogic.MapSearchEstatesParamsConfig;
import com.openclassrooms.realestatemanager.businesslogic.entities.Estate;
import com.openclassrooms.realestatemanager.businesslogic.enums.EstateType;
import com.openclassrooms.realestatemanager.businesslogic.enums.SearchParameter;
import com.openclassrooms.realestatemanager.businesslogic.enums.SearchParameterVisitor;
import com.openclassrooms.realestatemanager.businesslogic.gateways.EstateGateway;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SearchEstatesUseCase {

    private EstateGateway estateGateway;

    public SearchEstatesUseCase(EstateGateway estateGateway) {
        this.estateGateway = estateGateway;
    }

    public List<Estate> handle(Map<SearchParameter, Object> params) {

        List<Estate> found = new ArrayList<>();

        if(!params.isEmpty()) {
            SearchParameter firstParam = new ArrayList<>(params.keySet()).get(0);
            List<Estate> results = this.find(firstParam, params.get(firstParam));
            if(!results.isEmpty()) {
                found.addAll(results);
            }
            if(params.size() > 1) {
                for(int i=1; i<params.size(); i++) {
                    SearchParameter nextParam = new ArrayList<>(params.keySet()).get(i);
                    if(found.isEmpty()) {
                        found = this.find(nextParam, params.get(nextParam));
                    } else {
                        found = this.filter(nextParam, params.get(nextParam), found);
                    }
                }
            }
        }

        return found;
    }

    private List<Estate> find(SearchParameter firstParam, Object value) {

        return firstParam.accept(new SearchParameterVisitor<List<Estate>>() {
            @Override
            public List<Estate> visitType() {
                return find((EstateType) value);
            }

            @Override
            public List<Estate> visitLocation() {
                return find((String) value);
            }

            @Override
            public List<Estate> visitMaxPriceInDollars() {
                return find((Float) value);
            }
        });
    }

    private List<Estate> find(EstateType type) {
        return this.estateGateway.searchByType(type);
    }

    private List<Estate> find(String location) {
        return this.estateGateway.searchByLocation(location);
    }

    private List<Estate> find(Float maxPrice) {
        return this.estateGateway.searchUnderMaxPrice(maxPrice);
    }

    private List<Estate> filter(SearchParameter param, Object paramValue, List<Estate> estates) {
        return param.accept(new SearchParameterVisitor<List<Estate>>() {
            @Override
            public List<Estate> visitType() {
                List<Estate> filtered = new ArrayList<>();
                if((EstateType) paramValue != null) {
                    for(int i=0; i<estates.size(); i++) {
                        if(estates.get(i).getType() != null && estates.get(i).getType().equals(paramValue)) {
                            filtered.add(estates.get(i));
                        }
                    }
                }
                return filtered;
            }

            @Override
            public List<Estate> visitLocation() {
                List<Estate> filtered = new ArrayList<>();
                if(paramValue != null) {
                    for(int i=0; i<estates.size(); i++) {
                        if(estates.get(i).getLocation() != null && estates.get(i).getLocation().equals(paramValue)) {
                            filtered.add(estates.get(i));
                        }
                    }
                }
                return filtered;
            }

            @Override
            public List<Estate> visitMaxPriceInDollars() {
                List<Estate> filtered = new ArrayList<>();
                if((Float) paramValue != null) {
                    for(int i=0; i<estates.size(); i++) {
                        if(estates.get(i).getPrice() <= (Float) paramValue) {
                            filtered.add(estates.get(i));
                        }
                    }
                }
                return filtered;
            }
        });
    }

}
