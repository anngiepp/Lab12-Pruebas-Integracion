package com.tecsup.petclinic.services;

import com.tecsup.petclinic.dtos.VisitDTO;
import com.tecsup.petclinic.entities.Visit;
import com.tecsup.petclinic.exceptions.VisitNotFoundException;
import com.tecsup.petclinic.mapper.VisitMapper;
import com.tecsup.petclinic.repositories.VisitRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VisitServiceImpl implements VisitService {

    private final VisitRepository visitRepository;
    private final VisitMapper visitMapper;

    public VisitServiceImpl(VisitRepository visitRepository, VisitMapper visitMapper) {
        this.visitRepository = visitRepository;
        this.visitMapper = visitMapper;
    }

    @Override
    public List<VisitDTO> findAll() {
        return visitMapper.toDTOList(visitRepository.findAll());
    }

    @Override
    public VisitDTO findById(Long id) throws VisitNotFoundException {
        Visit visit = visitRepository.findById(id)
                .orElseThrow(() -> new VisitNotFoundException("Visit not found with id: " + id));

        return visitMapper.toDTO(visit);
    }

    @Override
    public VisitDTO create(VisitDTO visitDTO) {
        Visit visit = visitMapper.toEntity(visitDTO);
        Visit savedVisit = visitRepository.save(visit);
        return visitMapper.toDTO(savedVisit);
    }

    @Override
    public VisitDTO update(VisitDTO visitDTO) throws VisitNotFoundException {
        Visit visit = visitRepository.findById(visitDTO.getId())
                .orElseThrow(() -> new VisitNotFoundException("Visit not found with id: " + visitDTO.getId()));

        Visit updateData = visitMapper.toEntity(visitDTO);

        visit.setVisitDate(updateData.getVisitDate());
        visit.setDescription(updateData.getDescription());
        visit.setPet(updateData.getPet());

        Visit updatedVisit = visitRepository.save(visit);
        return visitMapper.toDTO(updatedVisit);
    }

    @Override
    public void delete(Long id) throws VisitNotFoundException {
        Visit visit = visitRepository.findById(id)
                .orElseThrow(() -> new VisitNotFoundException("Visit not found with id: " + id));

        visitRepository.delete(visit);
    }
}