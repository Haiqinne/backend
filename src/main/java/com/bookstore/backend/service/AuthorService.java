package com.bookstore.backend.service;

import com.bookstore.backend.dto.AuthorDTO;

import java.util.List;

public interface AuthorService {

    List<AuthorDTO> getAll();

    AuthorDTO getById(Long id);

    AuthorDTO create(AuthorDTO dto);

    AuthorDTO update(Long id, AuthorDTO dto);

    void delete(Long id);
}