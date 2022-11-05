package com.openclassrooms.realestatemanager.data.gatewaysimpl;

import com.openclassrooms.realestatemanager.businesslogic.gateways.MediaGateway;

import java.util.ArrayList;
import java.util.List;

public class InMemoryMediaGateway implements MediaGateway {

    List<String> media;

    public InMemoryMediaGateway() {
        this.media = new ArrayList<>();
    }

    @Override
    public List<String> getMediaByEstateId(int estateId) {
        return this.media;
    }

    public void setMedia(List<String> media) {
        this.media = media;
    }
}
