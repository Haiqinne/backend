package com.bookstore.backend.service;

import com.bookstore.backend.dto.UserDTO;
import com.bookstore.backend.dto.UserRequest;

import java.util.List;

public interface UserService {

    UserDTO create(UserRequest request);

    UserDTO update(Long id, UserRequest request);

    List<UserDTO> getAll();

    UserDTO getById(Long id);

    void delete(Long id);
}