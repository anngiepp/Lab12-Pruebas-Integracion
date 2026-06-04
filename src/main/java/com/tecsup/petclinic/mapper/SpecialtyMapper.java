package com.tecsup.petclinic.mapper;

import com.tecsup.petclinic.dtos.SpecialtyDTO;
import com.tecsup.petclinic.entities.Specialty;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class SpecialtyMapper {

    public SpecialtyDTO toDTO(Specialty specialty) {
        return SpecialtyDTO.builder()
                .id(specialty.getId())
                .name(specialty.getName())
                .build();
    }

    public Specialty toEntity(SpecialtyDTO specialtyDTO) {
        Specialty specialty = new Specialty();
        specialty.setId(specialtyDTO.getId());
        specialty.setName(specialtyDTO.getName());
        return specialty;
    }

    public List<SpecialtyDTO> toDTOList(List<Specialty> specialties) {
        return specialties.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }
}