package com.bookstore.backend.service;

import com.bookstore.backend.dto.PublisherDTO;

import java.util.List;

public interface PublisherService {

    List<PublisherDTO> getAll();

    PublisherDTO getById(Long id);

    PublisherDTO create(PublisherDTO dto);

    PublisherDTO update(Long id, PublisherDTO dto);

    void delete(Long id);
}