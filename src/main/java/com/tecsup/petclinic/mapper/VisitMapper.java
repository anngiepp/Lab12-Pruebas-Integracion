package com.tecsup.petclinic.mapper;

import com.tecsup.petclinic.dtos.VisitDTO;
import com.tecsup.petclinic.entities.Pet;
import com.tecsup.petclinic.entities.Visit;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class VisitMapper {

    public VisitDTO toDTO(Visit visit) {
        Integer petId = null;

        if (visit.getPet() != null) {
            petId = visit.getPet().getId();
        }

        return VisitDTO.builder()
                .id(visit.getId())
                .visitDate(visit.getVisitDate() != null ? visit.getVisitDate().toString() : null)
                .description(visit.getDescription())
                .petId(petId)
                .build();
    }

    public Visit toEntity(VisitDTO visitDTO) {
        Visit visit = new Visit();
        visit.setId(visitDTO.getId());
        visit.setVisitDate(LocalDate.parse(visitDTO.getVisitDate()));
        visit.setDescription(visitDTO.getDescription());

        if (visitDTO.getPetId() != null) {
            Pet pet = new Pet();
            pet.setId(visitDTO.getPetId());
            visit.setPet(pet);
        }

        return visit;
    }

    public List<VisitDTO> toDTOList(List<Visit> visits) {
        return visits.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }
}