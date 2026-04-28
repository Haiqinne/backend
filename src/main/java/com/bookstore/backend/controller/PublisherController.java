package com.bookstore.backend.controller;

import com.bookstore.backend.dto.PublisherDTO;
import com.bookstore.backend.dto.PublisherRequest;
import com.bookstore.backend.service.PublisherService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/publishers")
@CrossOrigin(originPatterns = "*", allowCredentials = "true")
public class PublisherController {

    private final PublisherService publisherService;

    public PublisherController(PublisherService publisherService) {
        this.publisherService = publisherService;
    }

    // 1. GET ALL
    @GetMapping
    public ResponseEntity<List<PublisherDTO>> getAll() {
        return ResponseEntity.ok(publisherService.getAll());
    }

    // 2. GET BY ID
    @GetMapping("/{id}")
    public ResponseEntity<PublisherDTO> getById(@PathVariable Long id) {
        PublisherDTO dto = publisherService.getById(id);
        return dto != null ? ResponseEntity.ok(dto) : ResponseEntity.notFound().build();
    }

    // 3. CREATE
    @PostMapping
    public ResponseEntity<PublisherDTO> create(@RequestBody PublisherRequest request) {
        PublisherDTO dto = new PublisherDTO(null, request.getPublisherName());
        return ResponseEntity.ok(publisherService.create(dto));
    }

    // 4. UPDATE
    @PutMapping("/{id}")
    public ResponseEntity<PublisherDTO> update(
            @PathVariable Long id,
            @RequestBody PublisherRequest request) {

        PublisherDTO dto = new PublisherDTO(id, request.getPublisherName());
        PublisherDTO updated = publisherService.update(id, dto);

        return updated != null ? ResponseEntity.ok(updated) : ResponseEntity.notFound().build();
    }

    // 5. DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        publisherService.delete(id);
        return ResponseEntity.noContent().build();
    }
}