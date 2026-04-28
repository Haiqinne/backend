package com.bookstore.backend.controller;

import com.bookstore.backend.dto.AddressDTO;
import com.bookstore.backend.dto.AddressRequest;
import com.bookstore.backend.service.AddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/addresses")
@RequiredArgsConstructor
public class AddressController {

    private final AddressService addressService;

    // CREATE
    @PostMapping
    public AddressDTO create(@RequestBody AddressRequest request) {
        return addressService.create(request);
    }

    // GET BY USER
    @GetMapping("/user/{userId}")
    public List<AddressDTO> getByUser(@PathVariable Long userId) {
        return addressService.getByUser(userId);
    }

    // GET BY ID
    @GetMapping("/{id}")
    public AddressDTO getById(@PathVariable Long id) {
        return addressService.getById(id);
    }

    // UPDATE
    @PutMapping("/{id}")
    public AddressDTO update(@PathVariable Long id,
                             @RequestBody AddressRequest request) {
        return addressService.update(id, request);
    }

    // DELETE
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        addressService.delete(id);
    }
}