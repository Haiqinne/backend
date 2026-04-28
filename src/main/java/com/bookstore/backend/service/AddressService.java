package com.bookstore.backend.service;

import com.bookstore.backend.dto.AddressDTO;
import com.bookstore.backend.dto.AddressRequest;

import java.util.List;

public interface AddressService {

    AddressDTO create(AddressRequest request);

    List<AddressDTO> getByUser(Long userId);

    AddressDTO getById(Long id);

    AddressDTO update(Long id, AddressRequest request);

    void delete(Long id);
}