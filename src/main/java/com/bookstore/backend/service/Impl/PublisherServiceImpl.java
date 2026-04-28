package com.bookstore.backend.service.Impl;

import com.bookstore.backend.dto.PublisherDTO;
import com.bookstore.backend.entity.Publisher;
import com.bookstore.backend.repository.PublisherRepository;
import com.bookstore.backend.service.PublisherService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PublisherServiceImpl implements PublisherService {

    private final PublisherRepository publisherRepository;

    public PublisherServiceImpl(PublisherRepository publisherRepository) {
        this.publisherRepository = publisherRepository;
    }

    private PublisherDTO mapToDTO(Publisher p) {
        return new PublisherDTO(p.getPublisherId(), p.getPublisherName());
    }

    private Publisher mapToEntity(PublisherDTO dto) {
        Publisher p = new Publisher();
        p.setPublisherId(dto.getPublisherId());
        p.setPublisherName(dto.getPublisherName());
        return p;
    }

    @Override
    public List<PublisherDTO> getAll() {
        return publisherRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public PublisherDTO getById(Long id) {
        return publisherRepository.findById(id)
                .map(this::mapToDTO)
                .orElse(null);
    }

    @Override
    public PublisherDTO create(PublisherDTO dto) {
        Publisher saved = publisherRepository.save(mapToEntity(dto));
        return mapToDTO(saved);
    }

    @Override
    public PublisherDTO update(Long id, PublisherDTO dto) {
        return publisherRepository.findById(id).map(p -> {
            p.setPublisherName(dto.getPublisherName());
            return mapToDTO(publisherRepository.save(p));
        }).orElse(null);
    }

    @Override
    public void delete(Long id) {
        publisherRepository.deleteById(id);
    }
}