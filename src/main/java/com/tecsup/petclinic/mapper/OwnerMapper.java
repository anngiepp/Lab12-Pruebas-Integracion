package com.tecsup.petclinic.mapper;

import com.tecsup.petclinic.dtos.OwnerDTO;
import com.tecsup.petclinic.entities.Owner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class OwnerMapper {

    public OwnerDTO toDTO(Owner owner) {
        return OwnerDTO.builder()
                .id(owner.getId())
                .firstName(owner.getFirstName())
                .lastName(owner.getLastName())
                .address(owner.getAddress())
                .city(owner.getCity())
                .telephone(owner.getTelephone())
                .build();
    }

    public Owner toEntity(OwnerDTO ownerDTO) {
        Owner owner = new Owner();
        owner.setId(ownerDTO.getId());
        owner.setFirstName(ownerDTO.getFirstName());
        owner.setLastName(ownerDTO.getLastName());
        owner.setAddress(ownerDTO.getAddress());
        owner.setCity(ownerDTO.getCity());
        owner.setTelephone(ownerDTO.getTelephone());
        return owner;
    }

    public List<OwnerDTO> toDTOList(List<Owner> owners) {
        return owners.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }
}