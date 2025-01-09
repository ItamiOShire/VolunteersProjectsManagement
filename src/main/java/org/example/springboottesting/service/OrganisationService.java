package org.example.springboottesting.service;

import org.example.springboottesting.model.Organisation;
import org.example.springboottesting.DTO.OrganisationDTO;
import org.example.springboottesting.model.OrganisationProfile;
import org.example.springboottesting.repository.OrganisationProfileRepository;
import org.example.springboottesting.repository.OrganisationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrganisationService {

    private final OrganisationRepository organisationRepository;
    private final OrganisationProfileRepository organisationProfileRepository;

    @Autowired
    public OrganisationService(OrganisationRepository organisationRepository, OrganisationProfileRepository organisationProfileRepository) {
        this.organisationRepository = organisationRepository;
        this.organisationProfileRepository = organisationProfileRepository;
    }

    public OrganisationDTO getOrganisationDetails(Long id) {
        OrganisationDTO organisationDTO = new OrganisationDTO();

        Organisation organisation = organisationRepository.findById(id).orElse(null);

        if (organisation == null) {
            return null;
        }

        String apartmentNumber = (organisation.getApartmentNumber() != 0) ? String.valueOf(organisation.getApartmentNumber()):"-";

        organisationDTO.setEmail(organisation.getEmail());
        organisationDTO.setName(organisation.getOrgName());
        organisationDTO.setKrsNumber(organisation.getKRSNumber());
        organisationDTO.setPhone(organisation.getPhoneNumber());
        organisationDTO.setType(organisation.getType());
        organisationDTO.setFnameAndlname(organisation.getOwnerFirstName() + " " + organisation.getOwnerLastName());
        organisationDTO.setAdress(
                organisation.getStreet() + "/" +
                apartmentNumber + " " +
                organisation.getZipCode() + ", " +
                organisation.getTown());

        return organisationDTO;
    }

    public List<OrganisationDTO> getOrganisationSDetails() {
        List<Organisation> organisations = organisationRepository.findAll();

        List<OrganisationProfile> organisationProfiles = organisationProfileRepository.findAll();

        List<OrganisationDTO> organisationDTOS = new ArrayList<>();

        for (Organisation organisation : organisations) {
            OrganisationDTO organisationDTO = new OrganisationDTO();

            organisationDTO.setName(organisation.getOrgName());
            organisationDTO.setType(organisation.getType());

            for (OrganisationProfile organisationProfile : organisationProfiles) {
                if(organisation.getId() == organisationProfile.getOrganisationId()) {
                    organisationDTO.setId(organisationProfile.getOrganisationId());
                    organisationDTO.setImgPath(organisationProfile.getImgPath());
                    organisationDTO.setDesc(organisationProfile.getDesc());
                    break;
                }
            }
            organisationDTOS.add(organisationDTO);
        }

        return organisationDTOS;
    }
}
