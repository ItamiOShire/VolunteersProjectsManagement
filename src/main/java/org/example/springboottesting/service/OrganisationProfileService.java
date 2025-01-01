package org.example.springboottesting.service;

import org.example.springboottesting.model.OrganisationProfile;
import org.example.springboottesting.repository.OrganisationProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrganisationProfileService {

    private final OrganisationProfileRepository organisationProfileRepository;

    @Autowired
    public OrganisationProfileService(OrganisationProfileRepository organisationProfileRepository) {
        this.organisationProfileRepository = organisationProfileRepository;
    }

    public OrganisationProfile saveOrganisationProfile(String id, String desc, String imgPath) {

        OrganisationProfile organisationProfile = new OrganisationProfile();

        organisationProfile.setOrganisationId(Integer.parseInt(id));
        organisationProfile.setDesc(desc);
        organisationProfile.setImgPath(imgPath);

        return organisationProfileRepository.save(organisationProfile);
    }

    public OrganisationProfile getOrganisationProfile(String id) {

        return organisationProfileRepository.findById(Long.parseLong(id)).orElse(null);

    }


}
