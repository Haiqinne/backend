package com.bookstore.backend.service.Impl;

import com.bookstore.backend.dto.AddressDTO;
import com.bookstore.backend.dto.AddressRequest;
import com.bookstore.backend.entity.Address;
import com.bookstore.backend.entity.User;
import com.bookstore.backend.repository.AddressRepository;
import com.bookstore.backend.repository.UserRepository;
import com.bookstore.backend.service.AddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AddressServiceImpl implements AddressService {

    private final AddressRepository addressRepo;
    private final UserRepository userRepo;

    private AddressDTO mapToDTO(Address a) {
        return new AddressDTO(
                a.getAddressId(),
                a.getUser().getId(),
                a.getCity(),
                a.getStreet(),
                a.getDetailAddress(),
                a.getName(),
                a.getPhoneNumber()
        );
    }

    @Override
    public AddressDTO create(AddressRequest request) {

        User user = userRepo.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Address a = new Address();
        a.setUser(user);
        a.setCity(request.getCity());
        a.setStreet(request.getStreet());
        a.setDetailAddress(request.getDetailAddress());
        a.setName(request.getName());
        a.setPhoneNumber(request.getPhoneNumber());

        return mapToDTO(addressRepo.save(a));
    }

    @Override
    public List<AddressDTO> getByUser(Long userId) {
        return addressRepo.findByUser_Id(userId)
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    @Override
    public AddressDTO getById(Long id) {
        Address a = addressRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Address not found"));

        return mapToDTO(a);
    }

    @Override
    public AddressDTO update(Long id, AddressRequest request) {

        Address a = addressRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Address not found"));

        a.setCity(request.getCity());
        a.setStreet(request.getStreet());
        a.setDetailAddress(request.getDetailAddress());
        a.setName(request.getName());
        a.setPhoneNumber(request.getPhoneNumber());

        return mapToDTO(addressRepo.save(a));
    }

    @Override
    public void delete(Long id) {
        addressRepo.deleteById(id);
    }
}