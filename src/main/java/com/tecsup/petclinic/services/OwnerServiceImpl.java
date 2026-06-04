package com.tecsup.petclinic.services;

import com.tecsup.petclinic.dtos.OwnerDTO;
import com.tecsup.petclinic.entities.Owner;
import com.tecsup.petclinic.exceptions.OwnerNotFoundException;
import com.tecsup.petclinic.mapper.OwnerMapper;
import com.tecsup.petclinic.repositories.OwnerRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OwnerServiceImpl implements OwnerService {

    private final OwnerRepository ownerRepository;
    private final OwnerMapper ownerMapper;

    public OwnerServiceImpl(OwnerRepository ownerRepository, OwnerMapper ownerMapper) {
        this.ownerRepository = ownerRepository;
        this.ownerMapper = ownerMapper;
    }

    @Override
    public List<OwnerDTO> findAll() {
        return ownerMapper.toDTOList(ownerRepository.findAll());
    }

    @Override
    public OwnerDTO findById(Long id) throws OwnerNotFoundException {
        Owner owner = ownerRepository.findById(id)
                .orElseThrow(() -> new OwnerNotFoundException("Owner not found with id: " + id));

        return ownerMapper.toDTO(owner);
    }

    @Override
    public OwnerDTO create(OwnerDTO ownerDTO) {
        Owner owner = ownerMapper.toEntity(ownerDTO);
        Owner savedOwner = ownerRepository.save(owner);
        return ownerMapper.toDTO(savedOwner);
    }

    @Override
    public OwnerDTO update(OwnerDTO ownerDTO) throws OwnerNotFoundException {
        Owner owner = ownerRepository.findById(ownerDTO.getId())
                .orElseThrow(() -> new OwnerNotFoundException("Owner not found with id: " + ownerDTO.getId()));

        owner.setFirstName(ownerDTO.getFirstName());
        owner.setLastName(ownerDTO.getLastName());
        owner.setAddress(ownerDTO.getAddress());
        owner.setCity(ownerDTO.getCity());
        owner.setTelephone(ownerDTO.getTelephone());

        Owner updatedOwner = ownerRepository.save(owner);
        return ownerMapper.toDTO(updatedOwner);
    }

    @Override
    public void delete(Long id) throws OwnerNotFoundException {
        Owner owner = ownerRepository.findById(id)
                .orElseThrow(() -> new OwnerNotFoundException("Owner not found with id: " + id));

        ownerRepository.delete(owner);
    }
}