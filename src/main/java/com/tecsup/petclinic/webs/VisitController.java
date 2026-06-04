package com.tecsup.petclinic.webs;

import com.tecsup.petclinic.dtos.VisitDTO;
import com.tecsup.petclinic.exceptions.VisitNotFoundException;
import com.tecsup.petclinic.services.VisitService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class VisitController {

    private final VisitService visitService;

    public VisitController(VisitService visitService) {
        this.visitService = visitService;
    }

    @GetMapping("/visits")
    public ResponseEntity<?> findAllVisits() {
        return ResponseEntity.ok(visitService.findAll());
    }

    @GetMapping("/visits/{id}")
    public ResponseEntity<VisitDTO> findById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(visitService.findById(id));
        } catch (VisitNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/visits")
    public ResponseEntity<VisitDTO> create(@RequestBody VisitDTO visitDTO) {
        VisitDTO newVisit = visitService.create(visitDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(newVisit);
    }

    @PutMapping("/visits/{id}")
    public ResponseEntity<VisitDTO> update(@RequestBody VisitDTO visitDTO, @PathVariable Long id) {
        try {
            visitDTO.setId(id);
            return ResponseEntity.ok(visitService.update(visitDTO));
        } catch (VisitNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/visits/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        try {
            visitService.delete(id);
            return ResponseEntity.ok("Delete ID: " + id);
        } catch (VisitNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}