package com.bookstore.backend.controller;

import com.bookstore.backend.dto.AuthorDTO;
import com.bookstore.backend.dto.AuthorRequest;
import com.bookstore.backend.service.AuthorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/authors")
@CrossOrigin(originPatterns = "*", allowCredentials = "true")
public class AuthorController {

    private final AuthorService authorService;

    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    // 1. GET ALL
    @GetMapping
    public ResponseEntity<List<AuthorDTO>> getAll() {
        return ResponseEntity.ok(authorService.getAll());
    }

    // 2. GET BY ID
    @GetMapping("/{id}")
    public ResponseEntity<AuthorDTO> getById(@PathVariable Long id) {
        AuthorDTO dto = authorService.getById(id);
        return dto != null ? ResponseEntity.ok(dto) : ResponseEntity.notFound().build();
    }

    // 3. CREATE
    @PostMapping
    public ResponseEntity<AuthorDTO> create(@RequestBody AuthorRequest request) {
        AuthorDTO dto = new AuthorDTO(null, request.getAuthorName());
        return ResponseEntity.ok(authorService.create(dto));
    }

    // 4. UPDATE
    @PutMapping("/{id}")
    public ResponseEntity<AuthorDTO> update(
            @PathVariable Long id,
            @RequestBody AuthorRequest request) {

        AuthorDTO dto = new AuthorDTO(id, request.getAuthorName());
        AuthorDTO updated = authorService.update(id, dto);

        return updated != null ? ResponseEntity.ok(updated) : ResponseEntity.notFound().build();
    }

    // 5. DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        authorService.delete(id);
        return ResponseEntity.noContent().build();
    }
}