package com.charles.zodrac.controller;

import com.charles.zodrac.model.dto.CharacterBasicDTO;
import com.charles.zodrac.model.dto.CharacterDTO;
import com.charles.zodrac.model.dto.ResponseDTO;
import com.charles.zodrac.service.CharacterService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/character")
@RequiredArgsConstructor
@Slf4j
public class CharacterController {

    private final CharacterService service;

    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Create character")
    @PostMapping
    public ResponseEntity<ResponseDTO> save(@RequestBody @Valid CharacterDTO dto) {
        log.info("REST request to create character: {}", dto);
        return ResponseEntity.ok(service.save(dto));
    }

    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Get character")
    @GetMapping("/detail")
    public ResponseEntity<CharacterBasicDTO> get() {
        log.info("REST request to get character");
        return ResponseEntity.ok(service.get());
    }

    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Get all characters")
    @GetMapping
    public ResponseEntity<List<CharacterBasicDTO>> getAll() {
        log.info("REST request to get all characters");
        return ResponseEntity.ok(service.getAll());
    }

    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Search character name")
    @GetMapping("/search")
    public ResponseEntity<Page<CharacterBasicDTO>> search(
            @RequestParam("searchTerm") String searchTerm,
            @RequestParam(value = "page", required = false, defaultValue = "0") int page,
            @RequestParam(value = "size", required = false, defaultValue = "10") int size) {
        log.info("REST to get search characters");
        return ResponseEntity.ok(service.search(searchTerm, page, size));
    }

    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Select character")
    @PostMapping("/{id}")
    public ResponseEntity<Void> select(@PathVariable("id") Long id) {
        log.info("REST request to select character: {}", id);
        service.select(id);
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Logout character")
    @PostMapping("/logout")
    public ResponseEntity<Void> logout() {
        log.info("REST request to logout character");
        service.logout();
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Delete character")
    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDTO> delete(@PathVariable("id") Long id) {
        log.info("REST request to delete character: {}", id);
        return ResponseEntity.ok(service.delete(id));
    }
}
