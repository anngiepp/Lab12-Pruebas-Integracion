package com.tecsup.petclinic.mapper;

import com.tecsup.petclinic.dtos.VetDTO;
import com.tecsup.petclinic.entities.Vet;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class VetMapper {

    public VetDTO toDTO(Vet vet) {
        return VetDTO.builder()
                .id(vet.getId())
                .firstName(vet.getFirstName())
                .lastName(vet.getLastName())
                .build();
    }

    public Vet toEntity(VetDTO vetDTO) {
        Vet vet = new Vet();
        vet.setId(vetDTO.getId());
        vet.setFirstName(vetDTO.getFirstName());
        vet.setLastName(vetDTO.getLastName());
        return vet;
    }

    public List<VetDTO> toDTOList(List<Vet> vets) {
        return vets.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }
}