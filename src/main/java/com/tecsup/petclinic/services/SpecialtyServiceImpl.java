package com.tecsup.petclinic.services;

import com.tecsup.petclinic.dtos.SpecialtyDTO;
import com.tecsup.petclinic.entities.Specialty;
import com.tecsup.petclinic.exceptions.SpecialtyNotFoundException;
import com.tecsup.petclinic.mapper.SpecialtyMapper;
import com.tecsup.petclinic.repositories.SpecialtyRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SpecialtyServiceImpl implements SpecialtyService {

    private final SpecialtyRepository specialtyRepository;
    private final SpecialtyMapper specialtyMapper;

    public SpecialtyServiceImpl(SpecialtyRepository specialtyRepository, SpecialtyMapper specialtyMapper) {
        this.specialtyRepository = specialtyRepository;
        this.specialtyMapper = specialtyMapper;
    }

    @Override
    public List<SpecialtyDTO> findAll() {
        return specialtyMapper.toDTOList(specialtyRepository.findAll());
    }

    @Override
    public SpecialtyDTO findById(Integer id) throws SpecialtyNotFoundException {
        Specialty specialty = specialtyRepository.findById(id)
                .orElseThrow(() -> new SpecialtyNotFoundException("Specialty not found with id: " + id));

        return specialtyMapper.toDTO(specialty);
    }

    @Override
    public SpecialtyDTO create(SpecialtyDTO specialtyDTO) {
        Specialty specialty = specialtyMapper.toEntity(specialtyDTO);
        Specialty savedSpecialty = specialtyRepository.save(specialty);
        return specialtyMapper.toDTO(savedSpecialty);
    }

    @Override
    public SpecialtyDTO update(SpecialtyDTO specialtyDTO) throws SpecialtyNotFoundException {
        Specialty specialty = specialtyRepository.findById(specialtyDTO.getId())
                .orElseThrow(() -> new SpecialtyNotFoundException("Specialty not found with id: " + specialtyDTO.getId()));

        specialty.setName(specialtyDTO.getName());

        Specialty updatedSpecialty = specialtyRepository.save(specialty);
        return specialtyMapper.toDTO(updatedSpecialty);
    }

    @Override
    public void delete(Integer id) throws SpecialtyNotFoundException {
        Specialty specialty = specialtyRepository.findById(id)
                .orElseThrow(() -> new SpecialtyNotFoundException("Specialty not found with id: " + id));

        specialtyRepository.delete(specialty);
    }
}