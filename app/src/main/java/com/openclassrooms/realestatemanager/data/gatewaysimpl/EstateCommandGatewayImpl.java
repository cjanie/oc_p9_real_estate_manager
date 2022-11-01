package com.openclassrooms.realestatemanager.data.gatewaysimpl;

import com.openclassrooms.realestatemanager.Utils;
import com.openclassrooms.realestatemanager.businesslogic.entities.Estate;
import com.openclassrooms.realestatemanager.businesslogic.enums.Devise;
import com.openclassrooms.realestatemanager.businesslogic.gateways.EstateCommandGateway;
import com.openclassrooms.realestatemanager.data.database.dao.EstateDAO;

public class EstateCommandGatewayImpl implements EstateCommandGateway {

    private EstateDAO estateDAO;

    public EstateCommandGatewayImpl(EstateDAO estateDAO) {
        this.estateDAO = estateDAO;
    }

    @Override
    public Long save(Estate estate) {
        com.openclassrooms.realestatemanager.data.database.dtoentities.Estate dto = new com.openclassrooms.realestatemanager.data.database.dtoentities.Estate();
        dto.setId(estate.getId());
        dto.setType(estate.getType());
        dto.setLocation(estate.getLocation());
        if(estate.getDevise().equals(Devise.DOLLAR)) {
            dto.setPriceInDollars(estate.getPrice());
        } else {
            float priceInDollar = Utils.convertEuroToDollar(Math.round(estate.getPrice()));
            dto.setPriceInDollars(priceInDollar);
        }

        dto.setSurfaceInMeterSquare(estate.getSurface());
        dto.setNumberOfRooms(estate.getNumberOfRooms());
        dto.setNumberOfBathrooms(estate.getNumberOfBathrooms());
        dto.setNumberOfBedrooms(estate.getNumberOfBedrooms());

        dto.setStreetNumberAndName(estate.getStreetNumberAndStreetName());
        dto.setAddressComplements(estate.getAddressComplements());
        dto.setZipcode(estate.getZipCode());
        dto.setCountry(estate.getCountry());

        dto.setDescription(estate.getDescription());

        return this.estateDAO.save(dto);
    }
}
