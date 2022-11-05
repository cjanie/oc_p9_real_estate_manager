package com.openclassrooms.realestatemanager.data.gatewaysimpl;

import com.openclassrooms.realestatemanager.businesslogic.gateways.MediaGateway;
import com.openclassrooms.realestatemanager.data.database.dao.EstateWithMediaDAO;
import com.openclassrooms.realestatemanager.data.database.dtoentities.EstateWithMedia;
import com.openclassrooms.realestatemanager.data.database.dtoentities.Media;

import java.util.ArrayList;
import java.util.List;

public class MediaGatewayImpl implements MediaGateway {

    EstateWithMediaDAO estateWithMediaDAO;

    public MediaGatewayImpl(EstateWithMediaDAO estateWithMediaDAO) {
        this.estateWithMediaDAO = estateWithMediaDAO;
    }

    @Override
    public List<String> getMediaByEstateId(int estateId) {
        List<String> mediaPaths = new ArrayList<>();
        EstateWithMedia estateWithMedia = this.estateWithMediaDAO.findById(estateId);
        List<Media> media = estateWithMedia.getMedia();
        if(!media.isEmpty()) {
            for (Media m: media) {
                mediaPaths.add(m.getPath());
            }
        }
        return mediaPaths;
    }

}
