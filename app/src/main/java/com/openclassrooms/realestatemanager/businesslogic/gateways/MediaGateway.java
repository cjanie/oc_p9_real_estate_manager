package com.openclassrooms.realestatemanager.businesslogic.gateways;

import java.util.List;

public interface MediaGateway {

    List<String> getMediaByEstateId(int estateId);
}
