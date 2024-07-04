package com.example.letprakt.service;

import com.example.letprakt.model.PhoneBook;
import com.example.letprakt.repository.PhoneBookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PhoneBookService {

    @Autowired
    private PhoneBookRepository phoneBookRepository;

    public List<PhoneBook> findAll() {
        return phoneBookRepository.findAll();
    }

    public Optional<PhoneBook> findByPhoneNumber(String phoneNumber) {
        return phoneBookRepository.findByPhoneNumber(phoneNumber);
    }

    public PhoneBook save(PhoneBook phoneBook) {
        return phoneBookRepository.save(phoneBook);
    }
}
