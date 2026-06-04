package com.tecsup.petclinic.services;

import com.tecsup.petclinic.dtos.VetDTO;
import com.tecsup.petclinic.entities.Vet;
import com.tecsup.petclinic.exceptions.VetNotFoundException;
import com.tecsup.petclinic.mapper.VetMapper;
import com.tecsup.petclinic.repositories.VetRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VetServiceImpl implements VetService {

    private final VetRepository vetRepository;
    private final VetMapper vetMapper;

    public VetServiceImpl(VetRepository vetRepository, VetMapper vetMapper) {
        this.vetRepository = vetRepository;
        this.vetMapper = vetMapper;
    }

    @Override
    public List<VetDTO> findAll() {
        return vetMapper.toDTOList(vetRepository.findAll());
    }

    @Override
    public VetDTO findById(Integer id) throws VetNotFoundException {
        Vet vet = vetRepository.findById(id)
                .orElseThrow(() -> new VetNotFoundException("Vet not found with id: " + id));

        return vetMapper.toDTO(vet);
    }

    @Override
    public VetDTO create(VetDTO vetDTO) {
        Vet vet = vetMapper.toEntity(vetDTO);
        Vet savedVet = vetRepository.save(vet);
        return vetMapper.toDTO(savedVet);
    }

    @Override
    public VetDTO update(VetDTO vetDTO) throws VetNotFoundException {
        Vet vet = vetRepository.findById(vetDTO.getId())
                .orElseThrow(() -> new VetNotFoundException("Vet not found with id: " + vetDTO.getId()));

        vet.setFirstName(vetDTO.getFirstName());
        vet.setLastName(vetDTO.getLastName());

        Vet updatedVet = vetRepository.save(vet);
        return vetMapper.toDTO(updatedVet);
    }

    @Override
    public void delete(Integer id) throws VetNotFoundException {
        Vet vet = vetRepository.findById(id)
                .orElseThrow(() -> new VetNotFoundException("Vet not found with id: " + id));

        vetRepository.delete(vet);
    }
}