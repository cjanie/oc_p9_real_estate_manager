package com.openclassrooms.realestatemanager.data.gatewaysimpl;

import com.openclassrooms.realestatemanager.businesslogic.entities.Estate;
import com.openclassrooms.realestatemanager.businesslogic.enums.Devise;
import com.openclassrooms.realestatemanager.businesslogic.enums.EstateType;
import com.openclassrooms.realestatemanager.businesslogic.gateways.EstateGateway;
import com.openclassrooms.realestatemanager.data.database.dao.EstateDAO;

import java.util.ArrayList;
import java.util.List;

public class EstateGatewayImpl implements EstateGateway {

    private EstateDAO estateDAO;

    public EstateGatewayImpl(EstateDAO estateDAO) {
        this.estateDAO = estateDAO;
    }

    @Override
    public List<Estate> getEstates() {

        List<com.openclassrooms.realestatemanager.data.database.dtoentities.Estate> estatesDto
                = this.estateDAO.findAll();
        return this.convertDtoToEstate(estatesDto);
    }

    @Override
    public List<Estate> searchByType(EstateType type) {
        List<com.openclassrooms.realestatemanager.data.database.dtoentities.Estate> estatesDto
                = this.estateDAO.searchByType(type.toString());
        return this.convertDtoToEstate(estatesDto);
    }

    @Override
    public List<Estate> searchByLocation(String location) {
        List<com.openclassrooms.realestatemanager.data.database.dtoentities.Estate> estatesDto
                = this.estateDAO.searchByLocation(location);
        return this.convertDtoToEstate(estatesDto);
    }

    private List<Estate> convertDtoToEstate(List<com.openclassrooms.realestatemanager.data.database.dtoentities.Estate> estatesDto) {
        List<Estate> estates = new ArrayList<>();
        if(!estatesDto.isEmpty()) {
            for (com.openclassrooms.realestatemanager.data.database.dtoentities.Estate dto: estatesDto) {
                Estate estate = new Estate();
                estate.setId(dto.getId());
                estate.setType(dto.getType());
                estate.setLocation(dto.getLocation());
                estate.setPrice(dto.getPriceInDollars());
                estate.setDevise(Devise.DOLLAR);
                estate.setSurface(dto.getSurfaceInMeterSquare());
                estate.setNumberOfRooms(dto.getNumberOfRooms());
                estate.setNumberOfBathrooms(dto.getNumberOfBathrooms());
                estate.setNumberOfBedrooms(dto.getNumberOfBedrooms());
                estates.add(estate);
            }
        }
        return estates;
    }
}
