package com.openclassrooms.realestatemanager.data.gatewaysimpl;

import com.openclassrooms.realestatemanager.businesslogic.entities.Estate;
import com.openclassrooms.realestatemanager.businesslogic.entities.Media;
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
        return this.convertListDtoToEstate(estatesDto);
    }

    @Override
    public List<Estate> searchByType(EstateType type) {
        List<com.openclassrooms.realestatemanager.data.database.dtoentities.Estate> estatesDto
                = this.estateDAO.searchByType(type.toString());
        return this.convertListDtoToEstate(estatesDto);
    }

    @Override
    public List<Estate> searchByLocation(String location) {
        List<com.openclassrooms.realestatemanager.data.database.dtoentities.Estate> estatesDto
                = this.estateDAO.searchByLocation(location);
        return this.convertListDtoToEstate(estatesDto);
    }

    @Override
    public List<Estate> searchUnderMaxPrice(Float maxPrice) {
        List<com.openclassrooms.realestatemanager.data.database.dtoentities.Estate> estatesDto
                = this.estateDAO.searchUnderMaxPrice(maxPrice);
        return this.convertListDtoToEstate(estatesDto);
    }

    @Override
    public Estate getEstateById(long id) {
        com.openclassrooms.realestatemanager.data.database.dtoentities.Estate estateDto
                = this.estateDAO.findEstateById(id);
        return this.convertDtoToEstate(estateDto);
    }

    private List<Estate> convertListDtoToEstate(List<com.openclassrooms.realestatemanager.data.database.dtoentities.Estate> estatesDto) {
        List<Estate> estates = new ArrayList<>();
        if(!estatesDto.isEmpty()) {
            for (com.openclassrooms.realestatemanager.data.database.dtoentities.Estate dto: estatesDto) {
                Estate estate = this.convertDtoToEstate(dto);
                estates.add(estate);
            }
        }
        return estates;
    }

    private Estate convertDtoToEstate(com.openclassrooms.realestatemanager.data.database.dtoentities.Estate dto) {
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

        estate.setStreetNumberAndStreetName(dto.getStreetNumberAndName());
        estate.setAddressComplements(dto.getAddressComplements());
        estate.setZipCode(dto.getZipcode());
        estate.setCountry(dto.getCountry());

        estate.setDescription(dto.getDescription());

        if(!dto.getMedia().isEmpty()) {
            List<Media> mediaList = new ArrayList<>();
            for(com.openclassrooms.realestatemanager.data.database.dtoentities.Media m: dto.getMedia()) {
                Media media = new Media();
                media.setPath(m.getPath());
                media.setName(m.getName());
                mediaList.add(media);
            }
            estate.setMedia(mediaList);
        }

        estate.setLatitude(dto.getLatitude());
        estate.setLongitude(dto.getLongitude());

        return estate;
    }
}
