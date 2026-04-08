package com.edstrom.WigellMcRental.controller;


import com.edstrom.WigellMcRental.dto.McCreateDto;
import com.edstrom.WigellMcRental.dto.McDto;
import com.edstrom.WigellMcRental.service.McService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/bikes")
public class McController {

    private final McService mcService;

    public McController(McService mcService){
        this.mcService = mcService;
    }
    @PostMapping
    public ResponseEntity<McDto> createMc(@RequestBody @Valid McCreateDto dto){
        McDto createdMc = mcService.create(dto);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(createdMc.id()).toUri();

        return ResponseEntity
                .created(location)
                .body(createdMc);
    }
    @GetMapping
    public List<McDto> listAll(){
        return mcService.findAll();
    }
    @GetMapping
    public McDto get(@PathVariable Long id){
        return mcService.findById(id);
    }


    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        mcService.delete(id);
        return ResponseEntity.noContent().build();
    }

}


