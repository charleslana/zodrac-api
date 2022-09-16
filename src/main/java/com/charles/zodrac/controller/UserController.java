package com.charles.zodrac.controller;

import com.charles.zodrac.model.dto.ResponseDTO;
import com.charles.zodrac.model.dto.UserBasicDTO;
import com.charles.zodrac.model.dto.UserDTO;
import com.charles.zodrac.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService service;

    @PostMapping
    public ResponseEntity<ResponseDTO> save(@RequestBody @Valid UserDTO dto) {
        log.info("REST request to create user: {}", dto);
        return ResponseEntity.ok(service.save(dto));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<UserBasicDTO> get(@PathVariable("id") Long id) {
        log.info("REST request to get user: {}", id);
        return ResponseEntity.ok(service.get(id));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') OR hasRole('ROLE_USER')")
    @GetMapping("/detail")
    public ResponseEntity<UserBasicDTO> getDetail() {
        log.info("REST request to get user detail");
        return ResponseEntity.ok(service.getDetail());
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping
    public ResponseEntity<Page<UserBasicDTO>> getAll(
            @RequestParam(value = "page", required = false, defaultValue = "0") int page,
            @RequestParam(value = "size", required = false, defaultValue = "10") int size) {
        log.info("REST request to get all users");
        return ResponseEntity.ok(service.getAll(page, size));
    }
}
