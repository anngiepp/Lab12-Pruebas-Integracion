package com.tecsup.petclinic.webs;

import com.tecsup.petclinic.dtos.SpecialtyDTO;
import com.tecsup.petclinic.exceptions.SpecialtyNotFoundException;
import com.tecsup.petclinic.services.SpecialtyService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class SpecialtyController {

    private final SpecialtyService specialtyService;

    public SpecialtyController(SpecialtyService specialtyService) {
        this.specialtyService = specialtyService;
    }

    @GetMapping("/specialties")
    public ResponseEntity<?> findAllSpecialties() {
        return ResponseEntity.ok(specialtyService.findAll());
    }

    @GetMapping("/specialties/{id}")
    public ResponseEntity<SpecialtyDTO> findById(@PathVariable Integer id) {
        try {
            return ResponseEntity.ok(specialtyService.findById(id));
        } catch (SpecialtyNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/specialties")
    public ResponseEntity<SpecialtyDTO> create(@RequestBody SpecialtyDTO specialtyDTO) {
        SpecialtyDTO newSpecialty = specialtyService.create(specialtyDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(newSpecialty);
    }

    @PutMapping("/specialties/{id}")
    public ResponseEntity<SpecialtyDTO> update(@RequestBody SpecialtyDTO specialtyDTO, @PathVariable Integer id) {
        try {
            specialtyDTO.setId(id);
            return ResponseEntity.ok(specialtyService.update(specialtyDTO));
        } catch (SpecialtyNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/specialties/{id}")
    public ResponseEntity<String> delete(@PathVariable Integer id) {
        try {
            specialtyService.delete(id);
            return ResponseEntity.ok("Delete ID: " + id);
        } catch (SpecialtyNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}