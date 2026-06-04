package com.tecsup.petclinic.webs;

import com.tecsup.petclinic.dtos.OwnerDTO;
import com.tecsup.petclinic.exceptions.OwnerNotFoundException;
import com.tecsup.petclinic.services.OwnerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class OwnerController {

    private final OwnerService ownerService;

    public OwnerController(OwnerService ownerService) {
        this.ownerService = ownerService;
    }

    @GetMapping("/owners")
    public ResponseEntity<?> findAllOwners() {
        return ResponseEntity.ok(ownerService.findAll());
    }

    @GetMapping("/owners/{id}")
    public ResponseEntity<OwnerDTO> findById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(ownerService.findById(id));
        } catch (OwnerNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/owners")
    public ResponseEntity<OwnerDTO> create(@RequestBody OwnerDTO ownerDTO) {
        OwnerDTO newOwner = ownerService.create(ownerDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(newOwner);
    }

    @PutMapping("/owners/{id}")
    public ResponseEntity<OwnerDTO> update(@RequestBody OwnerDTO ownerDTO, @PathVariable Long id) {
        try {
            ownerDTO.setId(id);
            return ResponseEntity.ok(ownerService.update(ownerDTO));
        } catch (OwnerNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/owners/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        try {
            ownerService.delete(id);
            return ResponseEntity.ok("Delete ID: " + id);
        } catch (OwnerNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}