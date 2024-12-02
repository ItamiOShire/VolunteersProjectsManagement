package org.example.springboottesting.service;

import org.example.springboottesting.model.Organisation;
import org.example.springboottesting.DTO.OrganisationDTO;
import org.example.springboottesting.repository.OrganisationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrganisationService {

    private final OrganisationRepository organisationRepository;

    @Autowired
    public OrganisationService(OrganisationRepository organisationRepository) {
        this.organisationRepository = organisationRepository;
    }

    public OrganisationDTO getOrganisationDetails(Long id) {
        OrganisationDTO organisationDTO = new OrganisationDTO();

        Organisation organisation = organisationRepository.findById(id).orElse(null);

        if (organisation == null) {
            return null;
        }

        String apartmentNumber = (organisation.getApartmentNumber() == 0) ? String.valueOf(organisation.getApartmentNumber()):"-";

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
}
