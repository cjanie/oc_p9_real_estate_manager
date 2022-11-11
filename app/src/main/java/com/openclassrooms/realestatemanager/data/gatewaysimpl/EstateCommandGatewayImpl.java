package com.openclassrooms.realestatemanager.data.gatewaysimpl;

import com.openclassrooms.realestatemanager.businesslogic.entities.Media;
import com.openclassrooms.realestatemanager.ui.utils.Utils;
import com.openclassrooms.realestatemanager.businesslogic.entities.Estate;
import com.openclassrooms.realestatemanager.businesslogic.enums.Devise;
import com.openclassrooms.realestatemanager.businesslogic.gateways.EstateCommandGateway;
import com.openclassrooms.realestatemanager.data.database.dao.EstateDAO;

import java.util.ArrayList;
import java.util.List;

public class EstateCommandGatewayImpl implements EstateCommandGateway {

    private final EstateDAO estateDAO;

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

        // Media
        if(!estate.getMedia().isEmpty()) {
            List<com.openclassrooms.realestatemanager.data.database.dtoentities.Media> mediaListDto = new ArrayList<>();
            for(Media m: estate.getMedia()) {
                com.openclassrooms.realestatemanager.data.database.dtoentities.Media mDto
                        = new com.openclassrooms.realestatemanager.data.database.dtoentities.Media(m.getPath());
                mDto.setName(m.getName());
                mediaListDto.add(mDto);
            }
            dto.setMedia(mediaListDto);
        }

        dto.setLatitude(estate.getLatitude());
        dto.setLongitude(estate.getLongitude());

        return this.estateDAO.save(dto);
    }
}
