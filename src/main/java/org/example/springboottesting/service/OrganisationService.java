package org.example.springboottesting.service;

import org.example.springboottesting.model.Organisation;
import org.example.springboottesting.model.OrganisationDTO;
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

        organisationDTO.setEmail(organisation.getEmail());
        organisationDTO.setfName(organisation.getOwnerFirstName());
        organisationDTO.setlName(organisation.getOwnerLastName());
        organisationDTO.setApartmentNumber(organisation.getApartmentNumber());
        organisationDTO.setName(organisation.getOrgName());
        organisationDTO.setKrsNumber(organisation.getKRSNumber());
        organisationDTO.setPhone(organisation.getPhoneNumber());
        organisationDTO.setZipCode(organisation.getZipCode());
        organisationDTO.setTown(organisation.getTown());
        organisationDTO.setType(organisation.getType());
        organisationDTO.setStreet(organisation.getStreet());

        return organisationDTO;
    }
}
