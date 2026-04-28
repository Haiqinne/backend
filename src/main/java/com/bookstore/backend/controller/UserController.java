package com.bookstore.backend.controller;

import com.bookstore.backend.dto.UserDTO;
import com.bookstore.backend.dto.UserRequest;
import com.bookstore.backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    // ===== CREATE =====
    @PostMapping
    public UserDTO create(@RequestBody UserRequest request) {
        return userService.create(request);
    }

    // ===== UPDATE =====
    @PutMapping("/{id}")
    public UserDTO update(@PathVariable Long id, @RequestBody UserRequest request) {
        return userService.update(id, request);
    }

    // ===== GET ALL =====
    @GetMapping
    public List<UserDTO> getAll() {
        return userService.getAll();
    }

    // ===== GET BY ID =====
    @GetMapping("/{id}")
    public UserDTO getById(@PathVariable Long id) {
        return userService.getById(id);
    }

    // ===== DELETE =====
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        userService.delete(id);
    }
}