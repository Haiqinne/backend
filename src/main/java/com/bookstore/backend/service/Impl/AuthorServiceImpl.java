package com.bookstore.backend.service.Impl;

import com.bookstore.backend.dto.AuthorDTO;
import com.bookstore.backend.entity.Author;
import com.bookstore.backend.repository.AuthorRepository;
import com.bookstore.backend.service.AuthorService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;

    public AuthorServiceImpl(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    private AuthorDTO mapToDTO(Author a) {
        return new AuthorDTO(a.getAuthorId(), a.getAuthorName());
    }

    private Author mapToEntity(AuthorDTO dto) {
        Author a = new Author();
        a.setAuthorId(dto.getAuthorId());
        a.setAuthorName(dto.getAuthorName());
        return a;
    }

    @Override
    public List<AuthorDTO> getAll() {
        return authorRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public AuthorDTO getById(Long id) {
        return authorRepository.findById(id)
                .map(this::mapToDTO)
                .orElse(null);
    }

    @Override
    public AuthorDTO create(AuthorDTO dto) {
        Author saved = authorRepository.save(mapToEntity(dto));
        return mapToDTO(saved);
    }

    @Override
    public AuthorDTO update(Long id, AuthorDTO dto) {
        return authorRepository.findById(id).map(a -> {
            a.setAuthorName(dto.getAuthorName());
            return mapToDTO(authorRepository.save(a));
        }).orElse(null);
    }

    @Override
    public void delete(Long id) {
        authorRepository.deleteById(id);
    }
}